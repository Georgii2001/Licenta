package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class UserEntity extends BaseEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 2798509641422598279L;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private String gender;
    String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_results_id", referencedColumnName = "id")
    private Test testResults;

    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String password;
    private String avatar;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
