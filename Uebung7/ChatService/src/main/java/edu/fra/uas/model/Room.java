package edu.fra.uas.model;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Room implements java.io.Serializable {

    private static final Logger log = LoggerFactory.getLogger(Room.class);

    private Long id;
    private String name;
    private Map<Long, Message> messages;
    private Map<Long, ChatUser> users;

    public Room() {
        log.debug("Room created without values");
        messages = new HashMap<>();
        users = new HashMap<>();
    }

    public Room(Long id, String name, Map<Long, Message> messages, Map<Long, ChatUser> users) {
        log.debug("Room created with values + name: " + name);
        this.id = id;
        this.name = name;
        this.messages = messages;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<Long, Message> messages) {
        this.messages = messages;
    }

    public Map<Long, ChatUser> getUsers() {
        return users;
    }

    public void setUsers(Map<Long, ChatUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", messages=" + messages +
                ", users=" + users +
                '}';
    }
    
}
