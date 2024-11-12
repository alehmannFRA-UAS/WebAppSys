package edu.fra.uas.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.fra.uas.model.User;
import edu.fra.uas.service.UserService;

@Controller
public class UserController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // http://127.0.0.1/
    @RequestMapping
	public String get() {
        log.debug("get() is called");
		return "index.html";
	}

    // http://127.0.0.1/list
    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list() is called");
        Iterable<User> userIter = userService.getAllUsers();
        List<User> users = new ArrayList<>();
        for (User user : userIter) {
            users.add(user);
        }
        model.addAttribute("users", users);
        return "list.html";
    }

    // http://127.0.0.1/find?id=1
    @RequestMapping(value = {"/find"}, method = RequestMethod.GET)
    public String find(@RequestParam("id") Long userId, Model model) {
        log.debug("find() is called");
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "find.html";
    }

    // http://127.0.0.1/add?firstName=Celine&lastName=Clever&email=celine.clever%40example.com&password=123456
    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String add(@RequestParam("firstName") String firstName, 
                      @RequestParam("lastName") String lastName, 
                      @RequestParam("email") String email, 
                      @RequestParam("password") String password, 
                      Model model) throws MissingServletRequestParameterException {
        log.debug("add() is called");
        User user = new User();
        user.setRole("USER");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        userService.createUser(user);
        model.addAttribute("user", user);
        return "add.html";
    }

    // http://127.0.0.1/update
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    public String update() {
        log.debug("update() is called");
        return "update.html";
    }

    // http://127.0.0.1/updated?id=2&firstName=Alice&lastName=Abraham&email=alice%40example.com&password=123A456
    @RequestMapping(value = {"/updated"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String update(@RequestParam("id") Long userId, 
                         @RequestParam("firstName") String firstName, 
                         @RequestParam("lastName") String lastName, 
                         @RequestParam("email") String email, 
                         @RequestParam("password") String password, 
                         Model model) {
        log.debug("updated() is called");
        User user = userService.getUserById(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        userService.updateUser(user);
        model.addAttribute("user", user);
        return "updated.html";
    }

    // http://127.0.0.1/delete/3
    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model) {
        log.debug("delete() is called");
        User user = userService.getUserById(id);
        userService.deleteUser(id);
        model.addAttribute("user", user);
        return "deleted.html";
    }

}
