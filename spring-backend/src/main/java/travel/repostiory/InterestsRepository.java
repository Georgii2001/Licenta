package travel.repostiory;

import travel.entities.Interests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestsRepository extends JpaRepository<Interests, Integer> {

    @Query("FROM Interests i WHERE interestId NOT IN (SELECT ui.interests.id FROM UserInterests ui WHERE ui.userEntity.id = :userId)")
    List<Interests> findInterestAvailableForUser(Integer userId);
}
