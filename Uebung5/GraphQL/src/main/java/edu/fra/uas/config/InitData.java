package edu.fra.uas.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.model.User;
import edu.fra.uas.service.UserService;
import jakarta.annotation.PostConstruct;

@Component
public class InitData {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(InitData.class);
    
    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        log.debug("### Initialize Data ###");

        log.debug("create user admin");
        User user = new User();
        user.setRole("ADMIN");
        user.setFirstName("Administrator");
        user.setLastName("Administrator");
        user.setEmail("admin@example.com");
        user.setPassword("extremeSecurePassword1234");
        userService.createUser(user);

        log.debug("create user alice");
        user = new User();
        user.setRole("USER");
        user.setFirstName("Alice");
        user.setLastName("Cooper");
        user.setEmail("alice@example.com");
        user.setPassword("alice1234");
        userService.createUser(user);

        log.debug("create user bob");
        user = new User();
        user.setRole("USER");
        user.setFirstName("Bob");
        user.setLastName("Builder");
        user.setEmail("bob@example.com");
        user.setPassword("bob1234");
        userService.createUser(user);

        log.debug("create user carol");
        user = new User();
        user.setRole("USER");
        user.setFirstName("Carol");
        user.setLastName("Cohn");
        user.setEmail("cc@umb.edu");
        user.setPassword("carcoumb");
        userService.createUser(user);

        log.debug("create user dave");
        user = new User();
        user.setRole("USER");
        user.setFirstName("Dave");
        user.setLastName("Gahan");
        user.setEmail("gahan@dm.uk");
        user.setPassword("dmdg12");
        userService.createUser(user);

        log.debug("create user eve");
        user = new User();
        user.setRole("USER");
        user.setFirstName("Eve");
        user.setLastName("Curie");
        user.setEmail("ec@curie.com");
        user.setPassword("1evec4");
        userService.createUser(user);

        log.debug("### Data initialized ###");
    }

}
