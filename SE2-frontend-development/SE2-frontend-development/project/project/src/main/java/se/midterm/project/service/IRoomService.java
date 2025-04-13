package se.midterm.project.service;

import se.midterm.project.model.Room;
import se.midterm.project.response.RoomResponse;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    List<RoomResponse> getAllRooms();

    RoomResponse getRoomById(Long id);
    Room save(Room room);
    void deleteRoom(Long id);
    List<RoomResponse> getAvailableRoomsByTypeAndDate(String roomType, LocalDate checkInDate, LocalDate checkOutDate);
List<RoomResponse> searchRoomsByType(String searchQuery);
}
