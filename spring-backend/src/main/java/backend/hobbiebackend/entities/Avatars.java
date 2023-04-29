package backend.hobbiebackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "avatars")
@NoArgsConstructor
@AllArgsConstructor
public class Avatars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatars_id", nullable = false, updatable = false)
    Integer avatarsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserEntity userEntity;

    @Column(name = "avatar_name", nullable = false)
    String avatarName;

    @Column(name = "avatar_priority", nullable = false)
    String avatarPriority;
    @Column(name = "SYS_CREATION_DATE", insertable = false)
    private Timestamp creationDate;

    @Column(name = "SYS_UPDATE_DATE", insertable = false, updatable = false)
    private Timestamp updateDate;
}


