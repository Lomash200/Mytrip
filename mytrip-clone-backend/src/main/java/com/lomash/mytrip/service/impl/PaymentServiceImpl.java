package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.payment.PaymentRequest;
import com.lomash.mytrip.dto.payment.PaymentResponse;
import com.lomash.mytrip.dto.payment.PaymentVerifyRequest;
import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.Payment;
import com.lomash.mytrip.entity.enums.BookingStatus;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.BookingRepository;
import com.lomash.mytrip.repository.PaymentRepository;
import com.lomash.mytrip.service.PaymentService;

import com.razorpay.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    @Value("${razorpay.key}")
    private String apiKey;

    @Value("${razorpay.secret}")
    private String apiSecret;

    public PaymentServiceImpl(BookingRepository bookingRepository,
                              PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse createOrder(PaymentRequest request) {

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ApiException("Booking not found"));

        try {
            RazorpayClient client = new RazorpayClient(apiKey, apiSecret);

            JSONObject options = new JSONObject();
            options.put("amount", (int)(request.getAmount() * 100)); // convert to paise
            options.put("currency", "INR");
            options.put("receipt", "order_rcpt_" + booking.getId());

            Order order = client.orders.create(options);

            Payment payment = Payment.builder()
                    .orderId(order.get("id"))
                    .amount(request.getAmount())
                    .status("CREATED")
                    .booking(booking)
                    .build();

            paymentRepository.save(payment);

            return PaymentResponse.builder()
                    .orderId(order.get("id"))
                    .amount(request.getAmount())
                    .currency("INR")
                    .build();

        } catch (Exception e) {
            throw new ApiException("Failed to create order: " + e.getMessage());
        }
    }

    @Override
    public String verifyPayment(PaymentVerifyRequest request) {

        Payment payment = paymentRepository.findAll().stream()
                .filter(p -> p.getOrderId().equals(request.getRazorpayOrderId()))
                .findFirst()
                .orElseThrow(() -> new ApiException("Payment not found"));

        try {
            String data = request.getRazorpayOrderId() + "|" + request.getRazorpayPaymentId();
            String generatedSignature = Utils.getHash(data, apiSecret);

            if (!generatedSignature.equals(request.getRazorpaySignature())) {
                payment.setStatus("FAILED");
                paymentRepository.save(payment);
                throw new ApiException("Signature mismatch! Payment failed");
            }

            Booking booking = payment.getBooking();
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);

            payment.setPaymentId(request.getRazorpayPaymentId());
            payment.setSignature(request.getRazorpaySignature());
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);

            return "Payment Successful";

        } catch (Exception e) {
            throw new ApiException("Payment verification failed: " + e.getMessage());
        }
    }
}
