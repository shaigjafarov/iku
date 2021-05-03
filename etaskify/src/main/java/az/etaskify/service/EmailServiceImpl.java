package az.etaskify.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender emailSender;
    private final Environment environment;

    @Override
    public void sendMail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            log.debug("Success sending mail to {} .", to);
        } catch (Exception e) {
            log.error("Fail sending mail to {} .", to, e);
        }

    }
}