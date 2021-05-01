package az.etaskify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class User extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Column(nullable = false)
    @Email
    private String email;
    @Column(nullable = false)
    @NotBlank
    private String userName;


    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",message = "The password must be at least 6 characters in length." +
            "Only alphanumeric characters.")
    private String password;

    @ManyToOne
    @JoinColumn(name ="role_id")
    private Role role;

    @ManyToMany(mappedBy = "assignees")
    private List<Task> tasks;

    @OneToMany(mappedBy = "createdBy")
    private List<Task> ownerTasks;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public User() {
    }

    public User(Long id) {
        this.id=id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }
}
