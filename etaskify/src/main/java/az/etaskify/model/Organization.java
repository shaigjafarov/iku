package az.etaskify.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Organization extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String address;

    @OneToMany(mappedBy = "organization",cascade = CascadeType.ALL,fetch = FetchType.EAGER)

    private List<User> users;

}
