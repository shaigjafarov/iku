package az.etaskify.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import az.etaskify.exception.EmailException;
import az.etaskify.service.impl.EmailServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    private EmailService emailService;
    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        emailService = new EmailServiceImpl(javaMailSender, "shaiq@jafr.com");
    }

    @Test
    void givenNullRecipientsWhenSendEmailThenThrowEmailException() {
        Assertions.assertThrows(
                EmailException.class,
                () -> emailService.sendMail("", "", null)
        );
    }

    @Test
    void givenRecipientsWhenSendEmailThenSendEmail() {
        emailService.sendMail("", "", List.of("1"));
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
