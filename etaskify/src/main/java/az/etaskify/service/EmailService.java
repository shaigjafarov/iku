package az.etaskify.service;

import java.util.Collection;

public interface EmailService {
    void sendMail(String subject, String text, Collection<String> recipients);
}
