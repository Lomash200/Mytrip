package com.lomash.mytrip.util;

import com.lomash.mytrip.entity.Booking;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class HotelInvoicePdfGenerator {

    public static byte[] generateInvoice(Booking booking) {
        try {
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD);
            Paragraph title = new Paragraph("Hotel Booking Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);
            document.add(new Paragraph("\n"));

            // Booking Info
            Font normal = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("Invoice Ref: " + booking.getReferenceCode(), normal));
            document.add(new Paragraph("User: " + booking.getUser().getFirstName(), normal));
            document.add(new Paragraph("Email: " + booking.getUser().getEmail(), normal));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Hotel: " + booking.getHotel().getName(), normal));
            document.add(new Paragraph("Address: " + booking.getHotel().getAddress(), normal));
            document.add(new Paragraph("Check-in: " + booking.getCheckInDate(), normal));
            document.add(new Paragraph("Check-out: " + booking.getCheckOutDate(), normal));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Total Amount: ₹" + booking.getTotalAmount(), normal));

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice PDF");
        }
    }
}
