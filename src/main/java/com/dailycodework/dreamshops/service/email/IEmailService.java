package com.dailycodework.dreamshops.service.email;

public interface IEmailService {
    void sendEmail(String to, String subject, String content);
}
