package com.dailycodework.dreamshops.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;
    private final Producer<String, String> producer;


    @Override
    public void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // `true` indicates HTML content

            mailSender.send(message);
        } catch (MessagingException e) {
            // Log or handle exception
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
