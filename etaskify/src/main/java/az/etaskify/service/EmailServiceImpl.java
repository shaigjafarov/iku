package az.etaskify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender emailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) {

    }

    @Override
    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shaig.jafarov.aze@gmail.com");
        message.setTo("semedovilkin96@gmail.com");
        message.setSubject("subject");
        message.setText("text");
        emailSender.send(message);
    }
}