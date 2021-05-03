package az.etaskify.service;

public interface EmailService {
    void sendMail(String subject, String text, String... recipients);

}
