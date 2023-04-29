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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id")
    Interests interests;

    @Column(name = "SYS_CREATION_DATE", insertable = false)
    private Timestamp creationDate;

    @Column(name = "SYS_UPDATE_DATE", insertable = false, updatable = false)
    private Timestamp updateDate;
}


