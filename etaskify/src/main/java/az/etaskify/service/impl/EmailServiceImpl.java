package az.etaskify.service.impl;

import az.etaskify.exception.EmailException;
import az.etaskify.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private final String senderEmail;

    @Override
    public void sendMail(String subject, String text, Collection<String> recipients) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(Objects.requireNonNull(senderEmail));
            message.setTo(recipients.toArray(String[]::new));
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            log.debug("Email with {} title was sent {} recipients.", subject, recipients.size());
        } catch (Exception e) {
            log.debug("Sending an email with {} title was failed.", subject);
            throw new EmailException("Failed to send an email");
        }
    }
}