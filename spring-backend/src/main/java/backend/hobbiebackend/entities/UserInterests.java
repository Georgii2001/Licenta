package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@Table(name = "user_interests")
@NoArgsConstructor
@AllArgsConstructor
public class UserInterests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_interest_id", nullable = false, updatable = false)
    Integer userInterestId;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "interest_id")
    Interests interests;

    @Column(name = "sys_creation_date", insertable = false)
    Timestamp creationDate;

    @Column(name = "sys_update_date", insertable = false, updatable = false)
    Timestamp updateDate;
}


