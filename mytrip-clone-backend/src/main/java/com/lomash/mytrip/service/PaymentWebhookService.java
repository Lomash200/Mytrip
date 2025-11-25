package com.lomash.mytrip.service;

public interface PaymentWebhookService {
    void handleRazorpayWebhook(String payload, String signature);


}
