package edu.fra.uas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.model.Room;
import edu.fra.uas.model.ChatUser;
import edu.fra.uas.service.RoomService;
import jakarta.annotation.PostConstruct;

@Component
public class InitData {
    
    private final Logger log = LoggerFactory.getLogger(InitData.class);

    @Autowired
    RoomService roomService;

    @PostConstruct
    public void init() {
        log.debug("### Initialize Data ###");

        log.debug("create room 1");
        Room room1 = roomService.create("room1");
        log.debug("create room 2");
        Room room2 = roomService.create("room2");

        log.debug("create user 1");
        ChatUser user1 = new ChatUser();
        user1.setId(1L);
        user1.setName("user1");

        log.debug("create user 2");
        ChatUser user2 = new ChatUser();
        user2.setId(2L);
        user2.setName("user2");

        log.debug("create user 3");
        ChatUser user3 = new ChatUser();
        user3.setId(3L);
        user3.setName("user3");

        log.debug("add user 1 to room 1");
        roomService.addUser(room1.getId(), user1);
        log.debug("add user 2 to room 1");
        roomService.addUser(room1.getId(), user2);

        log.debug("add user 3 to room 2");
        roomService.addUser(room2.getId(), user3);
        log.debug("add user 1 to room 2");
        roomService.addUser(room2.getId(), user1);

        log.debug("send message from user 1 in room 1");
        roomService.sendMessage(room1, user1.getId(), "Hello from user 1 in room 1");
        log.debug("send message from user 2 in room 1");
        roomService.sendMessage(room1, user2.getId(), "Hello from user 2 in room 1");

        log.debug("send message from user 3 in room 2");
        roomService.sendMessage(room2, user3.getId(), "Hello from user 3 in room 2");
        log.debug("send message from user 1 in room 2");
        roomService.sendMessage(room2, user1.getId(), "Hello from user 1 in room 2");

        log.debug("### Data initialized ###");
    }

}
