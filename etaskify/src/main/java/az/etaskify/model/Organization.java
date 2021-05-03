package az.etaskify.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, exclude = {"users", "tasks"})
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

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(mappedBy = "organization")
    private List<Task> tasks;

}
