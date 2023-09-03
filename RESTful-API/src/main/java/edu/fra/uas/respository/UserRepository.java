package edu.fra.uas.respository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import edu.fra.uas.model.User;

@Repository
public class UserRepository extends HashMap<Long, User> {
    
}
