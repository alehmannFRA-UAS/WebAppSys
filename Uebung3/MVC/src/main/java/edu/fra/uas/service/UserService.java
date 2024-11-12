package edu.fra.uas.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.model.User;
import edu.fra.uas.repository.UserRepository;

/**
 * This class represents the service for the user.
 */
@Service
public class UserService {
    
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    
    private long nextUserId = 1;

    public User createUser(User user) {
        log.debug("createUser: " + user);
        user.setId(nextUserId++);
        userRepository.put(user.getId(), user);
        return user;
    }

    public Iterable<User> getAllUsers() {
        log.debug("getAllUsers");
        return userRepository.values();
    }

    public User getUserById(long id) {
        log.debug("getUser: " + id);
        return userRepository.get(id);
    }

    public User updateUser(User user) {
        log.debug("updateUser: " + user);
        userRepository.put(user.getId(), user);
        return user;
    }

    public void deleteUser(long id) {
        log.debug("deleteUser: " + id);
        userRepository.remove(id);
    }

}
