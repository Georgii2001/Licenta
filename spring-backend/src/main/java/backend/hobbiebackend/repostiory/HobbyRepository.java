package backend.hobbiebackend.repostiory;

import backend.hobbiebackend.entities.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
    Set<Hobby> findAllByCreator(String creator);
}
