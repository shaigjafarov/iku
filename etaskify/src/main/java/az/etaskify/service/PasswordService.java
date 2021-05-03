package az.etaskify.service;


public interface PasswordService {

    String bcryptEncryptor(String plainText) ;

    Boolean doPasswordsMatch(String rawPassword,String encodedPassword);
    }

