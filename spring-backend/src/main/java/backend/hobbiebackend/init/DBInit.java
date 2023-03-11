package backend.hobbiebackend.init;

import backend.hobbiebackend.service.HobbyService;
import backend.hobbiebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
//    private final UserService userService;
//    private final HobbyService hobbyService;

    @Autowired
    public DBInit(UserService userService, HobbyService hobbyService) {
//        this.userService = userService;
//        this.hobbyService = hobbyService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.userService.seedUsersAndUserRoles();
    }

}
