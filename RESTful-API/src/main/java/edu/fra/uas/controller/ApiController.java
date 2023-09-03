package edu.fra.uas.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.User;
import edu.fra.uas.model.UserDTO;
import edu.fra.uas.service.UserService;
import edu.fra.uas.util.Partition;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    private final Logger log = org.slf4j.LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private UserService userService;

    private static final int MAX_USERS = 2;

    @GetMapping(value = "/users", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CollectionModel<UserDTO>> list(@RequestParam(required = false) Integer page) {
        log.debug("list() is called");
        List<UserDTO> users = userService.getAllUsersDTO();       
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else if (users.size() > MAX_USERS && page == null) {
            int lastPage = (users.size() / MAX_USERS) + 1;
            Link first = linkTo(methodOn(ApiController.class).list(1)).withRel(IanaLinkRelations.FIRST);
            Link next = linkTo(methodOn(ApiController.class).list(2)).withRel(IanaLinkRelations.NEXT);
            Link last = linkTo(methodOn(ApiController.class).list(lastPage)).withRel(IanaLinkRelations.LAST);
            CollectionModel<UserDTO> result = CollectionModel.of(users.subList(0, MAX_USERS)).add(first, next, last);
            for (UserDTO user : result) {
                Link selfLink = linkTo(ApiController.class).slash("/users/" + user.getId()).withSelfRel();
                user.add(selfLink);
            }
            return new ResponseEntity<>(result, HttpStatus.PARTIAL_CONTENT);
        } else if (page != null) {
            Partition<UserDTO> partition = Partition.ofSize(users, MAX_USERS);
            CollectionModel<UserDTO> result = null;
            Link link = linkTo(methodOn(ApiController.class).list(page)).withSelfRel();
            try{
                result = CollectionModel.of(partition.get(page - 1)).add(link);
                for (UserDTO user : result) {
                    Link selfLink = linkTo(ApiController.class).slash("/users/" + user.getId()).withSelfRel();
                    user.add(selfLink);
                }
            } catch (IndexOutOfBoundsException e) {
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            Link link = linkTo(methodOn(ApiController.class).list(null)).withSelfRel();
            CollectionModel<UserDTO> result = CollectionModel.of(users).add(link);
            for (UserDTO user : result) {
                Link selfLink = linkTo(ApiController.class).slash("/users/" + user.getId()).withSelfRel();
                user.add(selfLink);
            }
            return new ResponseEntity<>(CollectionModel.of(users), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/users/{id}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> find(@PathVariable("id") Long userId) {
        log.debug("find() is called");
        User user = userService.getUserById(userId);
        if (user == null) {            
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/users", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> add(@RequestBody User user) {
        log.debug("add() is called");
        String detail = null;
        if (user == null) {
            detail = "User must not be null";            
        } else if (user.getRole() == null) {
            detail = "Role must not be null";
        } else if (user.getRole().isEmpty()) {
            detail = "Role must not be empty";
        } else if (user.getFirstName() == null) {
            detail = "FirstName must not be null";
        } else if (user.getFirstName().isEmpty()) {
            detail = "FirstName must not be empty";
        } else if (user.getLastName() == null) {
            detail = "LastName must not be null";
        } else if (user.getLastName().isEmpty()) {
            detail = "LastName must not be empty";
        } else if (user.getEmail() == null) {
            detail = "Email must not be null";
        } else if (user.getEmail().isEmpty()) {
            detail = "Email must not be empty";
        } else if (user.getPassword() == null) {
            detail = "Password must not be null";
        } else if (user.getPassword().isEmpty()) {
            detail = "Password must not be empty";
        }
        if (detail != null) {
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail); 
            pd.setInstance(URI.create("/users"));
            pd.setTitle("JSON Object Error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }
        user = userService.createUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/restful/users/" + user.getId()));
        return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}"
                , consumes = MediaType.APPLICATION_JSON_VALUE
                , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody User newUser, @PathVariable("id") Long userId) {
        log.debug("update() is called");
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        String detail = null;
        if (newUser == null) {
            detail = "User must not be null";            
        } else if (newUser.getRole() == null) {
            detail = "Role must not be null";
        } else if (newUser.getRole().isEmpty()) {
            detail = "Role must not be empty";
        } else if (newUser.getFirstName() == null) {
            detail = "FirstName must not be null";
        } else if (newUser.getFirstName().isEmpty()) {
            detail = "FirstName must not be empty";
        } else if (newUser.getLastName() == null) {
            detail = "LastName must not be null";
        } else if (newUser.getLastName().isEmpty()) {
            detail = "LastName must not be empty";
        } else if (newUser.getEmail() == null) {
            detail = "Email must not be null";
        } else if (newUser.getEmail().isEmpty()) {
            detail = "Email must not be empty";
        } else if (newUser.getPassword() == null) {
            detail = "Password must not be null";
        } else if (newUser.getPassword().isEmpty()) {
            detail = "Password must not be empty";
        }
        if (detail != null) {
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail); 
            pd.setInstance(URI.create("/users/" + userId));
            pd.setTitle("JSON Object Error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }
        user.setRole(newUser.getRole());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user = userService.updateUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/restful/users/" + user.getId()));
        return new ResponseEntity<User>(user, headers,  HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long userId) {
        log.debug("delete() is called");
        User user = userService.deleteUser(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}
