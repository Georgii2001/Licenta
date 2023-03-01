package backend.hobbiebackend.init;

import backend.hobbiebackend.service.CategoryService;
import backend.hobbiebackend.service.HobbyService;
import backend.hobbiebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final HobbyService hobbyService;

    @Autowired
    public DBInit(UserService userService, CategoryService categoryService, HobbyService hobbyService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.hobbyService = hobbyService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.seedUsersAndUserRoles();
        this.categoryService.initCategories();
    }

}
