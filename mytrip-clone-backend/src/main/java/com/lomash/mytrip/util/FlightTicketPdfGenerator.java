package com.lomash.mytrip.util;

import com.lomash.mytrip.entity.FlightBooking;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

public class FlightTicketPdfGenerator {

    public static byte[] generateTicket(FlightBooking booking) {
        try {
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD);
            Paragraph title = new Paragraph("Flight Ticket - MyTrip", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);
            document.add(new Paragraph("\n"));

            Font normal = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("PNR: " + booking.getPnr(), normal));
            document.add(new Paragraph("Passenger: " + booking.getUser().getFirstName(), normal));
            document.add(new Paragraph("Email: " + booking.getUser().getEmail(), normal));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Flight: " + booking.getFlight().getAirline()
                    + " " + booking.getFlight().getFlightNumber(), normal));

            document.add(new Paragraph("From: " + booking.getFlight().getOrigin().getName(), normal));
            document.add(new Paragraph("To: " + booking.getFlight().getDestination().getName(), normal));
            document.add(new Paragraph("Departure: " + booking.getFlight().getDepartureTime(), normal));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Total Amount: ₹" + booking.getTotalAmount(), normal));

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate flight ticket PDF");
        }
    }
}
