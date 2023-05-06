package backend.hobbiebackend.repostiory;

import backend.hobbiebackend.entities.Avatars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AvatarsRepository  extends JpaRepository<Avatars, Integer> {

    List<Avatars> findByUserEntityId(Integer userId);

    @Query("SELECT MAX(avatarPriority) + 1 FROM Avatars WHERE userEntity.id = :userId")
    Integer findNextAvatarPriority(Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Avatars a WHERE a.userEntity.id = :userId AND a.id = :avatarId")
    void deleteUserAvatar(Integer userId, Integer avatarId);
}
