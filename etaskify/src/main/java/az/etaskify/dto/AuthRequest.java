package az.etaskify.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class AuthRequest {
    @Email(message = "Email is not correct")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",message = "The password must be at least 6 characters in length." +
            "Only alphanumeric characters.")
    private String password;

}
