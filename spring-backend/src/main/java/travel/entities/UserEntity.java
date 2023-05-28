package travel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity extends BaseEntity  {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private String gender;
    private String description;

    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private String password;
    private String avatar;

    @Column(name = "sys_creation_date", insertable = false, updatable = false)
    private Timestamp creationDate;

    @Column(name = "sys_update_date", insertable = false, updatable = false)
    private Timestamp updateDate;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
