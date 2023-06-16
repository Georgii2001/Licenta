package travel.repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import travel.entities.Notifications;
import travel.entities.UserEntity;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {

    @Query("FROM Notifications WHERE userSender=:sender AND userReceiver=:receiver " +
            "AND notificationType=:notificationType AND CAST(creationDate AS date)=CURDATE()")
    Notifications findUserNotificationByType(UserEntity sender, UserEntity receiver, String notificationType);
}
