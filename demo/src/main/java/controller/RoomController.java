package controller;

import lombok.Getter;
import lombok.Setter;

import dao.RoomDAO;
import models.Room;

import java.util.List;

@Getter
@Setter
public class RoomController {
    private RoomDAO roomDAO;

    public RoomController() {
        roomDAO = new RoomDAO();
    }

    public List<Room> getAllRoomWithStateFalse() {
        return roomDAO.getRoomsWithStateFalse();
    }

    public List<Room> getAllRoomWithStateTrue() {
        return roomDAO.getRoomsWithStateTrue();
    }
}
