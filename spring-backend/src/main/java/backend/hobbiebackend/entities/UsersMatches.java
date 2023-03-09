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
@Table(name = "users_matches")
public class UsersMatches extends BaseEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 2798509641422598279L;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_matched_id", referencedColumnName = "id")
    private UserEntity userMatched;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
