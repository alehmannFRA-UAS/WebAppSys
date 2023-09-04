package edu.fra.uas.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.model.Message;
import edu.fra.uas.model.Room;
import edu.fra.uas.model.RoomDTO;
import edu.fra.uas.model.ChatUser;
import edu.fra.uas.repository.RoomRepository;

@Service
public class RoomService {

    private static final Logger log = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository roomRepository;

    private long nextRoomId = 1;

    private long nextMessageId = 1;

    public Room create(String name){
        log.info("createRoom: ", name);
        Room room = new Room();
        room.setId(nextRoomId++);
        room.setName(name);
        roomRepository.put(room.getId(), room);
        return room;
    }

    public Iterable<Room> getAll(){
        log.info("getAllRooms");
        return roomRepository.values();
    }

    public Room getById(Long id) {
        log.info("get room by id: {}", id);
        return roomRepository.get(id);
    }

    public Room update(Room room){
        log.info("update room: {}", room);
        roomRepository.put(room.getId(), room);
        return room;
    }

    public Room delete(Long id){
        log.info("delete room: {}", id);
        return roomRepository.remove(id);
    }

    public List<RoomDTO> getAllDTO() {
        log.info("getAllRoomsDTO");
        Iterable<Room> roomIter = getAll();
        List<Room> roomsOrigList = new ArrayList<>();
        for (Room room : roomIter) {
            roomsOrigList.add(room);
        }
        List<RoomDTO> roomsTargetList = new ArrayList<>();
        for (Room source : roomsOrigList) {
            RoomDTO target = new RoomDTO();
            BeanUtils.copyProperties(source, target);
            roomsTargetList.add(target);
        }
        return roomsTargetList;
    }

    public Room addUser(Long roomId, ChatUser user) {
        log.info("addUser: roomId={}, user={}", roomId, user);
        Room room = roomRepository.get(roomId);
        if(room.getUsers().get(user.getId()) != null) {
            return room;
        }
        room.getUsers().put(user.getId(), user);
        return room;
    }

    public ChatUser getUserById(Long roomId, Long userId) {
        log.info("getUserById: roomId={}, userId={}", roomId, userId);
        Room room = roomRepository.get(roomId);
        return room.getUsers().get(userId);
    }

    public Room removeUser(Long roomId, Long userId) {
        log.info("removeUser: roomId={}, userId={}", roomId, userId);
        Room room = roomRepository.get(roomId);
        if(room.getUsers().get(userId) == null) {
            return room;
        }
        room.getUsers().remove(userId);
        return room;
    }

    public Message sendMessage(Room room, Long userId, String text) {
        log.info("sendMessage: roomId={}, userId={}, text={}", room.getId(), userId, text);
        Message message = new Message();
        message.setId(nextMessageId++);
        message.setContent(text);
        message.setUserId(userId);
        message.setTimeStamp(java.time.LocalDateTime.now());
        room.getMessages().put(message.getId(), message);
        return message;
    }

    public List<Message> getMessages(Room room) {
        log.info("getMessages: roomId={}", room.getId());
        List<Message> messages = new ArrayList<>();
        for (Message message : room.getMessages().values()) {
            messages.add(message);
        }
        return messages;
    }

}
