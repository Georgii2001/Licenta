package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_matches")
@Inheritance(strategy = InheritanceType.JOINED)
public class UsersMatches extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

    @OneToOne
    @JoinColumn(name = "user_matched_id", referencedColumnName = "id")
    UserEntity userMatched;

    @OneToOne
    @JoinColumn(name = "matched_status_id", referencedColumnName = "id")
    MatchesStatus matchesStatus;

    @Column(name = "sys_creation_date", insertable = false)
    Timestamp creationDate;

    @Column(name = "sys_update_date", insertable = false, updatable = false)
    Timestamp updateDate;
}
