package edu.fra.uas.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import edu.fra.uas.model.Room;

@Repository
public class RoomRepository extends HashMap<Long, Room> {

}
