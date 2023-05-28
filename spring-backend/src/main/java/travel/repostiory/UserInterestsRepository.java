package travel.repostiory;

import travel.entities.UserInterests;
import travel.models.UserMatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserInterestsRepository extends JpaRepository<UserInterests, Integer> {

    List<UserInterests> findByUserEntityId(Integer id);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserInterests ui " +
            "WHERE ui.userEntity.id = :userId " +
            "AND ui.interests.interestId = (SELECT interestId FROM Interests WHERE interestName = :interest)")
    void deleteUserInterest(Integer userId, String interest);

    @Transactional
    void deleteByUserEntityId(Integer userId);

    @Query("SELECT new travel.models.UserMatches(userEntity.id, COUNT(*)) " +
            "FROM UserInterests WHERE userEntity.id <> :userId " +
            "AND interests.interestId IN (SELECT interests.interestId FROM UserInterests WHERE userEntity.id = :userId) " +
            "GROUP BY userEntity.id ORDER BY COUNT(*)")
    List<UserMatches> countCommonInterestsByUserId(Integer userId);
}
