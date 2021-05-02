package az.etaskify.service;


public interface PasswordService {

    public String bcryptEncryptor(String plainText) ;

    public Boolean doPasswordsMatch(String rawPassword,String encodedPassword);
    }

