package az.etaskify.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email
    private String email;

    public UserDto(@NotBlank String name, @NotBlank String surname, @Email String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
