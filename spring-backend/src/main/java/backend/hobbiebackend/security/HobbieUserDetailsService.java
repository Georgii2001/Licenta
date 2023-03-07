package backend.hobbiebackend.security;

import backend.hobbiebackend.model.entities.UserEntity;
import backend.hobbiebackend.model.repostiory.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HobbieUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public HobbieUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username " + username + " was not found."));

        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity userEntity) {

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole()));

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
    }
}
