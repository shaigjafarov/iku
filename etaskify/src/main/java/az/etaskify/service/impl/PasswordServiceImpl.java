package az.etaskify.service.impl;


import az.etaskify.service.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PasswordServiceImpl implements PasswordService {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public String bcryptEncryptor(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    public Boolean doPasswordsMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}