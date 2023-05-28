package travel.repostiory;

import travel.entities.MatchesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchesStatusRepository extends JpaRepository<MatchesStatus, Integer> {

    MatchesStatus findByMatchesStatusNameEquals(String matchesStatusName);
}
