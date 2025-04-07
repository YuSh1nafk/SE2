package se.midterm.project.service;

import se.midterm.project.model.Room;
import se.midterm.project.response.RoomResponse;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    List<RoomResponse> getAllRooms();
    List<RoomResponse> getAvailableRoomsByTypeAndDate(String roomType, LocalDate checkInDate, LocalDate checkOutDate);
    RoomResponse getRoomById(Long id);
    Room addRoom(Room room);
}