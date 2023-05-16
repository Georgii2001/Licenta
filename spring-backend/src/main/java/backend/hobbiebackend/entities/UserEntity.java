package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity extends BaseEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 2798509641422598279L;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private String gender;
    String description;

    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String password;
    private String avatar;

    @Column(name = "SYS_CREATION_DATE", insertable = false, updatable = false)
    private Timestamp creationDate;

    @Column(name = "SYS_UPDATE_DATE", insertable = false, updatable = false)
    private Timestamp updateDate;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
