package com.carpool.CarPoolingSystem.service;

import org.springframework.stereotype.Service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

@Service
public class OtpService {

        public void sendOtpEmail(String toEmail, String otp) {
            final String fromEmail = "krish01code@gmail.com";
            final String password = "kykvhheloaapaybj"; // App password from Gmail

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject("OTP Verification");
                message.setText("Your OTP is: " + otp);
                Transport.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send email: " + e.getMessage());
            }
        }
    }
