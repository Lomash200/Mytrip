package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.FlightBooking;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.BookingRepository;
import com.lomash.mytrip.repository.FlightBookingRepository;
import com.lomash.mytrip.service.InvoiceService;
import com.lomash.mytrip.util.FlightTicketPdfGenerator;
import com.lomash.mytrip.util.HotelInvoicePdfGenerator;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final BookingRepository bookingRepository;
    private final FlightBookingRepository flightBookingRepository;

    public InvoiceServiceImpl(BookingRepository bookingRepository,
                              FlightBookingRepository flightBookingRepository) {
        this.bookingRepository = bookingRepository;
        this.flightBookingRepository = flightBookingRepository;
    }

    @Override
    public byte[] generateHotelInvoice(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ApiException("Booking not found"));

        return HotelInvoicePdfGenerator.generateInvoice(booking);
    }

    @Override
    public byte[] generateFlightTicket(Long id) {
        FlightBooking fb = flightBookingRepository.findById(id)
                .orElseThrow(() -> new ApiException("Flight booking not found"));

        return FlightTicketPdfGenerator.generateTicket(fb);
    }
}
