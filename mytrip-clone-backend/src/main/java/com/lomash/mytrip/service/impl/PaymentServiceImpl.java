package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.common.ApiResponse;
import com.lomash.mytrip.dto.payment.PaymentRequest;
import com.lomash.mytrip.dto.payment.PaymentResponse;
import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.PaymentRecord;
import com.lomash.mytrip.repository.BookingRepository;
import com.lomash.mytrip.repository.PaymentRecordRepository;
import com.lomash.mytrip.service.EmailService;
import com.lomash.mytrip.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RazorpayClient client;
    private final String razorpaySecret;
    private final BookingRepository bookingRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final EmailService emailService;

    public PaymentServiceImpl(
            @Value("${razorpay.key}") String key,
            @Value("${razorpay.secret}") String secret,
            @Value("${razorpay.webhook.secret}") String webhookSecret,
            BookingRepository bookingRepository,
            PaymentRecordRepository paymentRecordRepository,
            EmailService emailService) throws Exception {

        this.client = new RazorpayClient(key, secret);
        this.razorpaySecret = secret;
        this.bookingRepository = bookingRepository;
        this.paymentRecordRepository = paymentRecordRepository;
        this.emailService = emailService;
    }

    @Override
    public PaymentResponse createOrder(PaymentRequest request) {
        try {
            Booking booking = bookingRepository.findById(request.getBookingId())
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            long amountInPaise = (long) (request.getAmount() * 100);

            JSONObject options = new JSONObject();
            options.put("amount", amountInPaise);
            options.put("currency", request.getCurrency());
            options.put("receipt", "bkg_" + booking.getId());
            options.put("notes", new JSONObject()
                    .put("bookingId", booking.getId().toString())
                    .put("bookingRef", booking.getReferenceCode()));

            Order order = client.orders.create(options);
            String orderId = order.get("id");

            booking.setOrderId(orderId);
            bookingRepository.save(booking);

            return new PaymentResponse(orderId, request.getAmount(), "Order created");

        } catch (Exception e) {
            return new PaymentResponse(null, request.getAmount(), "Failed: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> verifyPayment(String orderId, String paymentId, String signature) {
        try {
            // Verify using Razorpay Utils
            JSONObject attributes = new JSONObject();
            attributes.put("razorpay_order_id", orderId);
            attributes.put("razorpay_payment_id", paymentId);
            attributes.put("razorpay_signature", signature);

            boolean verified = Utils.verifyPaymentSignature(attributes, razorpaySecret);

            if (!verified) {
                return ApiResponse.error("Signature mismatch");
            }

            Booking booking = bookingRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            PaymentRecord record = PaymentRecord.builder()
                    .orderId(orderId)
                    .paymentId(paymentId)
                    .signature(signature)
                    .amount(booking.getTotalAmount())
                    .status("PAID")
                    .booking(booking)
                    .build();

            paymentRecordRepository.save(record);

            booking.setPaymentStatus("PAID");
            bookingRepository.save(booking);

            // Send email
            emailService.sendBookingConfirmation(
                    booking.getUser().getEmail(),
                    booking.getReferenceCode(),
                    booking.getHotel().getName(),
                    booking.getRoom().getRoomType(),
                    booking.getCheckInDate().toString(),
                    booking.getCheckOutDate().toString(),
                    booking.getTotalAmount()
            );

            return ApiResponse.ok("Payment successful", null);

        } catch (Exception e) {
            return ApiResponse.error("Verification failed: " + e.getMessage());
        }
    }
}