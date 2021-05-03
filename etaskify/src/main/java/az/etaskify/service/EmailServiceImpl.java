package az.etaskify.service;

import az.etaskify.exception.EmailException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final Environment environment;

    @Override
    public void sendMail(String subject, String text, String... recipients) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));
            message.setTo(recipients);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            log.debug("Email with {} title was sent {} recipients.", subject, recipients.length);
        } catch (Exception e) {
            log.debug("Sending an email with {} title was failed.", subject);
            throw new EmailException("Failed to send an email");
        }

    }
}