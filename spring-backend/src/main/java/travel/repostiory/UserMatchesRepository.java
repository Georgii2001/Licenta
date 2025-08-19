package travel.repostiory;

import travel.entities.UserEntity;
import travel.entities.UsersMatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMatchesRepository extends JpaRepository<UsersMatches, Integer> {

    List<UsersMatches> findByUserIdEqualsAndUserMatchedIdEquals(Integer userId, Integer userMatchedId);

    @Query("FROM UsersMatches WHERE (user = :userEntity OR userMatched = :userEntity) " +
            "AND matchesStatus.matchesStatusName = 'MATCHED'")
    List<UsersMatches> getAllMatchedUserMatches(UserEntity userEntity);

    List<UsersMatches> findByUserEquals(UserEntity userEntity);
}
