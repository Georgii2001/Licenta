package backend.hobbiebackend.repostiory;

import backend.hobbiebackend.entities.UserInterests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserInterestsRepository  extends JpaRepository<UserInterests, Integer> {

    List<UserInterests> findByUserEntityId(Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserInterests ui " +
            "WHERE ui.userEntity.id = :userId " +
            "AND ui.interests.interestId = (SELECT interestId FROM Interests i WHERE interestName = :interest)")
    void deleteUserInterest(Integer userId, String interest);
}
