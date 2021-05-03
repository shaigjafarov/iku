package az.etaskify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.Nullable;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
public class Organization extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String name;
    @Column(nullable = false)
    @NotBlank
    private String phoneNumber;

    @Column(nullable = false)
    @NotBlank
    private String address;

    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Valid
    private List<User> users;

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<Task> tasks;

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
