package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    // ============================
    // NORMAL EMAIL SENDER
    // ============================
    @Override
    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }

    // ============================
    // BOOKING CONFIRMATION EMAIL
    // Correct 7-parameter method
    // ============================
    @Override
    public void sendBookingConfirmation(
            String email,
            String bookingRef,
            String hotelName,
            String roomType,
            String checkIn,
            String checkOut,
            double amount
    ) {
        try {

            String subject = "Booking Confirmed - " + bookingRef;

            String html = """
                    <html>
                    <body style="font-family: Arial, sans-serif; padding: 20px;">
                        <h2 style="color: #0078ff;">Your Booking is Confirmed!</h2>

                        <p>Thank you for booking with <b>MyTrip</b>.</p>

                        <h3>Booking Details</h3>
                        <table style="border-collapse: collapse; width: 100%;">
                            <tr>
                                <td style="padding: 8px;"><b>Booking Reference:</b></td>
                                <td style="padding: 8px;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px;"><b>Hotel:</b></td>
                                <td style="padding: 8px;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px;"><b>Room Type:</b></td>
                                <td style="padding: 8px;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px;"><b>Check-in:</b></td>
                                <td style="padding: 8px;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px;"><b>Check-out:</b></td>
                                <td style="padding: 8px;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px;"><b>Amount Paid:</b></td>
                                <td style="padding: 8px; color: green; font-weight: bold;">₹%s</td>
                            </tr>
                        </table>

                        <br/>
                        <p style="font-size: 14px;">
                            For any help, reply to this email or contact MyTrip support.
                        </p>

                        <br/>
                        <b>Enjoy Your Stay!</b><br/>
                        — MyTrip Team
                    </body>
                    </html>
                    """.formatted(
                    bookingRef, hotelName, roomType, checkIn, checkOut, amount
            );

            sendEmail(email, subject, html);

        } catch (Exception e) {
            throw new RuntimeException("Booking confirmation email failed: " + e.getMessage());
        }
    }
}
