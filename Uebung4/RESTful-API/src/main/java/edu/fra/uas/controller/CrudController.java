package edu.fra.uas.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.User;
import edu.fra.uas.service.UserService;

@RestController
@RequestMapping("/crud")
public class CrudController {
    
    private final Logger log = org.slf4j.LoggerFactory.getLogger(CrudController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list() {
        log.debug("list() is called");
        Iterable<User> userIter = userService.getAllUsers();
        List<User> users = new ArrayList<>();
        for (User user : userIter) {
            users.add(user);
        }
        return users;
    }

    @GetMapping("/users/{id}")
    public User find(@PathVariable("id") Long userId) {
        log.debug("find() is called");
        User user = userService.getUserById(userId);
        return user;
    }

    @PostMapping("/users")
    public User add(User user) {
        log.debug("add() is called");
        return userService.createUser(user);
    }

    @PutMapping("/users/{id}")
    public User update(User newUser, @PathVariable("id") Long userId) {
        log.debug("update() is called");
        User user = userService.getUserById(userId);
        user.setRole(newUser.getRole());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") Long userId) {
        log.debug("delete() is called");
        userService.deleteUser(userId);
    }

}
