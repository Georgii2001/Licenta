package backend.hobbiebackend.repostiory;

import backend.hobbiebackend.entities.MatchesStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchesStatusRepository extends JpaRepository<MatchesStatus, Integer> {

    MatchesStatus findByMatchesStatusNameEquals(String matchesStatusName);
}
