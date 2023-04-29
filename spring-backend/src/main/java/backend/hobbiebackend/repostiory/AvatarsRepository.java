package backend.hobbiebackend.repostiory;

import backend.hobbiebackend.entities.Avatars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvatarsRepository  extends JpaRepository<Avatars, Integer> {

    List<Avatars> findByUserEntityId(Integer userId);
}
