package com.lomash.mytrip.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lomash.mytrip.dto.refund.RefundRequest;
import com.lomash.mytrip.dto.refund.RefundResponse;
import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.Payment;
import com.lomash.mytrip.entity.enums.BookingStatus;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.BookingRepository;
import com.lomash.mytrip.repository.PaymentRepository;
import com.lomash.mytrip.service.EmailEventsService;
import com.lomash.mytrip.service.RefundService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class RefundServiceImpl implements RefundService {

    @Value("${razorpay.key}")
    private String razorKey;

    @Value("${razorpay.secret}")
    private String razorSecret;

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final EmailEventsService emailEventsService;
    private final ObjectMapper mapper = new ObjectMapper();

    public RefundServiceImpl(
            BookingRepository bookingRepository,
            PaymentRepository paymentRepository,
            EmailEventsService emailEventsService) {

        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.emailEventsService = emailEventsService;
    }

    @Override
    @Transactional
    public RefundResponse initiateRefund(RefundRequest req) {

        // 1️⃣ Booking fetch
        Booking booking = bookingRepository.findById(req.getBookingId())
                .orElseThrow(() -> new ApiException("Booking not found"));

        // 2️⃣ Payment fetch
        Optional<Payment> optPay = paymentRepository.findByBooking(booking);

        if (optPay.isEmpty()) {
            throw new ApiException("Payment not found for booking.");
        }

        Payment payment = optPay.get();

        // 3️⃣ Validate status
        if (!payment.getStatus().equalsIgnoreCase("SUCCESS")) {
            throw new ApiException("Refund allowed only after successful payment.");
        }

        // 4️⃣ Razorpay refund API call
        try {
            String url = "https://api.razorpay.com/v1/payments/" + payment.getPaymentId() + "/refund";

            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
            okhttp3.RequestBody body = okhttp3.RequestBody
                    .create(("{}").getBytes());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", okhttp3.Credentials.basic(razorKey, razorSecret))
                    .build();

            okhttp3.Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new ApiException("Refund API failed: " + response.message());
            }

            JsonNode json = mapper.readTree(response.body().string());

            String refundId = json.path("id").asText();
            String status = json.path("status").asText();

            // 5️⃣ Update booking + payment status
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);

            payment.setStatus("REFUNDED");
            paymentRepository.save(payment);

            // 6️⃣ Email notification
            emailEventsService.sendBookingCancelled(
                    booking.getUser().getEmail(),
                    booking.getReferenceCode()
            );

            return RefundResponse.builder()
                    .refundId(refundId)
                    .status(status)
                    .amount(payment.getAmount())
                    .message("Refund successful")
                    .build();

        } catch (IOException e) {
            throw new ApiException("Refund failed: " + e.getMessage());
        }
    }
}
