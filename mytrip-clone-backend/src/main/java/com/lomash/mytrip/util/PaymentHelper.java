package com.lomash.mytrip.util;

import java.util.UUID;

public class PaymentHelper {

    public static String generateOrderId() {
        return "ORD_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateTransactionId() {
        return "TXN_" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }

    public static String generateRefundId() {
        return "REF_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}