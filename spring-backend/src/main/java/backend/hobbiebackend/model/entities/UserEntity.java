package backend.hobbiebackend.model.entities;

import backend.hobbiebackend.model.entities.enums.GenderEnum;
import backend.hobbiebackend.model.entities.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class UserEntity extends BaseEntity implements Serializable,Cloneable {

    private static final long serialVersionUID = 2798509641422598279L;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private GenderEnum gender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_results_id", referencedColumnName = "id")
    private Test testResults;

    @Column(nullable = false)
    private UserRoleEnum role;
    @Column(nullable = false)
    private String password;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
