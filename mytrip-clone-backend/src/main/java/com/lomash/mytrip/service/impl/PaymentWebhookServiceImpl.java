package com.lomash.mytrip.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.PaymentRecord;
import com.lomash.mytrip.entity.enums.BookingStatus;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.BookingRepository;
import com.lomash.mytrip.repository.PaymentRepository;
import com.lomash.mytrip.service.EmailEventsService;
import com.lomash.mytrip.service.PaymentWebhookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Service
public class PaymentWebhookServiceImpl implements PaymentWebhookService {

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${razorpay.webhook.secret}")
    private String razorpaySecret;

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final EmailEventsService emailEventsService;

    public PaymentWebhookServiceImpl(
            BookingRepository bookingRepository,
            PaymentRepository paymentRepository,
            EmailEventsService emailEventsService
    ) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.emailEventsService = emailEventsService;
    }

    @Override
    @Transactional
    public void handleRazorpayWebhook(String payload, String signature) {

        try {
            // -----------------------------
            // 1) Signature Verify
            // -----------------------------
            if (!verifyRazorpaySignature(payload, signature)) {
                throw new ApiException("Invalid Razorpay signature");
            }

            JsonNode root = mapper.readTree(payload);

            // Payment entity from webhook
            JsonNode paymentEntity = root
                    .path("payload")
                    .path("payment")
                    .path("entity");

            String paymentId = paymentEntity.path("id").asText();
            String orderId = paymentEntity.path("order_id").asText();
            long amountPaise = paymentEntity.path("amount").asLong();
            double amount = amountPaise / 100.0;
            String status = paymentEntity.path("status").asText("FAILED");

            // -----------------------------
            // Extract bookingReference
            // -----------------------------
            final String[] bookingRefHolder = { null };

            if (paymentEntity.has("notes")) {
                JsonNode notes = paymentEntity.path("notes");
                if (notes.has("bookingReference")) {
                    bookingRefHolder[0] = notes.get("bookingReference").asText();
                }
            }

            // Validate booking reference
            if (bookingRefHolder[0] == null || bookingRefHolder[0].isBlank()) {
                throw new ApiException("bookingReference missing in payment notes");
            }

            String bookingRef = bookingRefHolder[0];

            // -----------------------------
            // 2) Idempotency check
            // -----------------------------
            if (paymentRepository.findByPaymentId(paymentId).isPresent()) {
                return; // Already processed webhook
            }

            // -----------------------------
            // 3) Find booking by reference
            // -----------------------------
            Optional<Booking> optBooking = bookingRepository.findAll()
                    .stream()
                    .filter(b -> bookingRef.equals(b.getReferenceCode()))
                    .findFirst();

            if (optBooking.isEmpty()) {
                throw new ApiException("Booking not found for reference: " + bookingRef);
            }

            Booking booking = optBooking.get();

            // -----------------------------
            // 4) Save Payment Record
            // -----------------------------
            PaymentRecord payment = PaymentRecord.builder()
                    .orderId(orderId)
                    .paymentId(paymentId)
                    .signature(signature)
                    .amount(amount)
                    .status(status.equals("captured") ? "SUCCESS" : "FAILED")
                    .booking(booking)
                    .build();

            paymentRepository.save(payment);

            // -----------------------------
            // 5) Confirm booking if payment success
            // -----------------------------
            if (status.equals("captured")) {
                booking.setStatus(BookingStatus.CONFIRMED);
                bookingRepository.save(booking);

                // Email user
                emailEventsService.sendHotelBookingConfirmation(booking);
            }

        } catch (Exception e) {
            throw new ApiException("Webhook error: " + e.getMessage());
        }
    }


    // -----------------------------
    // Signature verification
    // -----------------------------
    private boolean verifyRazorpaySignature(String payload, String signature) {
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(
                    razorpaySecret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );
            sha256Hmac.init(secretKey);

            byte[] hash = sha256Hmac.doFinal(payload.getBytes(StandardCharsets.UTF_8));

            // Razorpay signatures are Base64 encoded
            String generated = Base64.getEncoder().encodeToString(hash);

            return generated.equals(signature);

        } catch (Exception e) {
            return false;
        }
    }
}
