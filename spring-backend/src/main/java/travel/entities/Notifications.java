package travel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false, updatable = false)
    protected Integer id;

    @OneToOne
    @JoinColumn(name = "user_sender_id", referencedColumnName = "id")
    UserEntity userSender;

    @OneToOne
    @JoinColumn(name = "user_receiver_id", referencedColumnName = "id")
    UserEntity userReceiver;

    @Column(name = "notification_type")
    String notificationType;

    @Column(name = "sys_creation_date", insertable = false, updatable = false)
    Timestamp creationDate;

    @Column(name = "sys_update_date", insertable = false, updatable = false)
    Timestamp updateDate;
}
