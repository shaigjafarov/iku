package az.etaskify.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@NoArgsConstructor
public class UserOwnerDto {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "The password must be at least 6 characters in length." +
            "Only alphanumeric characters.")
    private String password;

    public UserOwnerDto(@Email String email) {
        this.email = email;
    }
}
