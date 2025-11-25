package com.lomash.mytrip.service;

public interface InvoiceService {

    byte[] generateHotelInvoice(Long bookingId);

    byte[] generateFlightTicket(Long flightBookingId);
}
