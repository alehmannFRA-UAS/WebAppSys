package edu.fra.uas.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatUser implements java.io.Serializable {

    private static final Logger log = LoggerFactory.getLogger(ChatUser.class);

    private long id;
    private String name;

    public ChatUser() {
        log.debug("User created without values");
    }

    public ChatUser(long id, String name) {
        log.debug("User created with values + name: " + name);
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", name=" + name + "]";
    }
    
}
