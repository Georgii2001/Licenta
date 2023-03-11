package backend.hobbiebackend.repostiory;

import backend.hobbiebackend.entities.AppClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppClientRepository extends JpaRepository<AppClient, Integer> {
    Optional<AppClient> findByUsername(String username);
}
