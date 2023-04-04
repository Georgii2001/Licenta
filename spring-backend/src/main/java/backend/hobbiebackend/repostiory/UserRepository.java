package backend.hobbiebackend.repostiory;

import backend.hobbiebackend.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(String role);

    @Query("FROM UserEntity WHERE username !=:username and role = :role")
    Slice<UserEntity> findByRole(String role, String username, Pageable pageable);
}
