package edu.fra.uas.controller;

import java.net.URI;
import java.util.List;

import edu.fra.uas.model.Message;
import edu.fra.uas.model.ChatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.Room;
import edu.fra.uas.model.RoomDTO;
import edu.fra.uas.service.RoomService;

@RestController
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "/rooms",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<RoomDTO>> getAll() {
        log.info("Get all rooms");
        List<RoomDTO> rooms = roomService.getAllDTO();
        if (rooms.isEmpty() || rooms.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping(value = "/rooms/{id}", 
                produces = MediaType.APPLICATION_JSON_VALUE)               
    @ResponseBody
    public ResponseEntity<?> getById(@PathVariable("id") Long roomId) {
        log.info("Get room by id: ", roomId);
        Room room = roomService.getById(roomId);
        if (room == null) {
            return new ResponseEntity<> ("Room was not found for id {" + roomId + "}", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Room>(room, HttpStatus.OK);
    }

    @PostMapping(value = "/rooms", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody String name) {
        log.info("Create new room: ", name);
        if (name == null || name.isEmpty()) {
            String detail = "Room name must not be null or empty";
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail); 
            pd.setInstance(URI.create("/rooms"));
            pd.setTitle("Room creation error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }
        Room room = roomService.create(name);
        return new ResponseEntity<Room>(room, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/rooms/{id}",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("Delete rooms: ", id);
        Room room = roomService.delete(id);
        if (room == null) {
            return new ResponseEntity<> ("Room was not found for id {" + id + "}", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Room>(room, HttpStatus.OK);
    }

    @PutMapping(value = "/rooms/{id}", 
                consumes = MediaType.APPLICATION_JSON_VALUE, 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> join(@PathVariable("id") Long roomId, @RequestBody ChatUser user) {
        log.info("Join room: ", roomId);
        Room room = roomService.getById(roomId);
        if (room == null) {
            return new ResponseEntity<> ("Room was not found for id {" + roomId + "}", HttpStatus.NOT_FOUND);
        }
        roomService.addUser(roomId, user);
        return new ResponseEntity<Room>(room, HttpStatus.OK);
    }

    @PatchMapping(value = "/rooms/{id}/users/{userId}", 
                  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> leaveRoom(@PathVariable("id") Long roomId, @PathVariable("userId") Long userId) {
        log.info("Leave room: ", roomId);
        Room room = roomService.getById(roomId);
        if (room == null) {
            return new ResponseEntity<> ("Room was not found for id {" + roomId + "}", HttpStatus.NOT_FOUND);
        }
        room = roomService.removeUser(roomId, userId);
        return new ResponseEntity<Room>(room, HttpStatus.OK);
    }

    @PostMapping(value = "/rooms/{id}/users/{userId}/messages", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> sendMessage(@PathVariable("id") Long roomId, @PathVariable("userId") Long userId, @RequestBody String text) {
        log.info("Send message to room: ", roomId);
        Room room = roomService.getById(roomId);
        if (room == null) {
            return new ResponseEntity<> ("Room was not found for id {" + roomId + "}", HttpStatus.NOT_FOUND);
        }
        ChatUser user = roomService.getUserById(roomId, userId);
        if (user == null) {
            return new ResponseEntity<> ("User was not found for id {" + userId + "}", HttpStatus.NOT_FOUND);
        }
        Message message = roomService.sendMessage(room, userId, text);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @GetMapping(value = "/rooms/{id}/users/{userId}/messages", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getMessages(@PathVariable("id") Long roomId, @PathVariable("userId") Long userId) {
        log.info("Get messages from room: ", roomId);
        Room room = roomService.getById(roomId);
        if (room == null) {
            return new ResponseEntity<> ("Room was not found for id {" + roomId + "}", HttpStatus.NOT_FOUND);
        }
        ChatUser user = roomService.getUserById(roomId, userId);
        if (user == null) {
            return new ResponseEntity<> ("User was not found for id {" + userId + "}", HttpStatus.NOT_FOUND);
        }
        List<Message> messages = roomService.getMessages(room);
        return new ResponseEntity<List<Message>>(messages, HttpStatus.OK);
    }

}
