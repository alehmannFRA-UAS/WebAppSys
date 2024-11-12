package edu.fra.uas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.fra.uas.model.StockPrice;
import edu.fra.uas.model.User;
import edu.fra.uas.service.UserService;

import reactor.core.publisher.Flux;

@Controller
@SchemaMapping(typeName = "User, StockPrice")
public class GraphqlController {

    private static final Logger log = LoggerFactory.getLogger(GraphqlController.class);

    @Autowired
    private UserService userService;

    @QueryMapping(name="allUsers")
    public List<User> getAllUsers() {
        log.debug("getAllUsers() is called");
        Iterable<User> userIter = userService.getAllUsers();
        List<User> users = new ArrayList<>();
        for (User user : userIter) {
            users.add(user);
        }
        return users;
    }

    @QueryMapping(name="users")
    public List<User> getAllUsers(@Argument int count, @Argument int offset) {
        log.debug("getAllUsers(count=" + count + ", offset = " + offset + ") is called");
        if (count < 0 || offset < 0) {
            log.error("Invalid count or offset values");
            return null;
        }
        Iterable<User> userIter = userService.getAllUsers();
        List<User> users = new ArrayList<>();
        for (User user : userIter) {
            users.add(user);
        }

        if (count > 0) {
            return users.stream().skip(offset).limit(count).collect(Collectors.toList());
        } else {
            return users.stream().skip(offset).collect(Collectors.toList());
        }
    }

    @QueryMapping(name="userById")
    public User getUserById(@Argument Long id) {
        log.debug("getUserById() is called");
        return userService.getUserById(id);
    }

    @MutationMapping
    public User addUser(@Argument String role, @Argument String firstName, @Argument String lastName, @Argument String email, @Argument String password) {
        log.debug("addUser() is called");
        User user = new User();
        user.setRole(role);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return userService.createUser(user);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument String role, @Argument String firstName, @Argument String lastName, @Argument String email, @Argument String password) {
        log.debug("updateUser() is called");
        User user = userService.getUserById(id);
        if (role != null && !role.isEmpty()) {
            user.setRole(role);
        }
        if (firstName != null && !firstName.isEmpty()) {
            user.setFirstName(firstName);            
        }
        if (lastName != null && !lastName.isEmpty()) {
            user.setLastName(lastName);            
        }
        if (email != null && !email.isEmpty()) {
            user.setEmail(email);            
        }
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);            
        }
        return userService.updateUser(user);
    }

    @MutationMapping
    public Integer deleteUser(@Argument Long id) {
        log.debug("deleteUser() is called");
        User user = userService.deleteUser(id);
        if (user == null) {
            log.error("User with id " + id + " not found");
            return null;
        } else {
            log.debug("User with id " + id + " deleted");
            return 1;
        }
    }

    @SubscriptionMapping
    public Flux<StockPrice> stockPrice(@Argument String symbol) {
        log.debug("stockPrice() is called");
        return Flux.interval(Duration.ofSeconds(1))
                .map(l -> new StockPrice(symbol, Math.random(), LocalDateTime.now().toString()));
    }

}
