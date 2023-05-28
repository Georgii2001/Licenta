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
    Integer avatarPriority;

    @Column(name = "sys_creation_date", insertable = false, updatable = false)
    Timestamp creationDate;

    @Column(name = "sys_update_date", insertable = false, updatable = false)
    Timestamp updateDate;
}


