package com.lomash.mytrip.controller;

import com.lomash.mytrip.service.InvoiceService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/hotel/{bookingId}")
    public ResponseEntity<byte[]> getHotelInvoice(@PathVariable Long bookingId) {
        byte[] pdf = invoiceService.generateHotelInvoice(bookingId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hotel-invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/flight/{flightBookingId}")
    public ResponseEntity<byte[]> getFlightTicket(@PathVariable Long flightBookingId) {
        byte[] pdf = invoiceService.generateFlightTicket(flightBookingId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=flight-ticket.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
