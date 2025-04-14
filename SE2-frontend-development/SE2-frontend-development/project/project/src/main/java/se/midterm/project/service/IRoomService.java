package se.midterm.project.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.midterm.project.model.Room;
import se.midterm.project.response.RoomResponse;

import java.time.LocalDate;
import java.util.List;

public interface IRoomService {
    List<RoomResponse> getAllRooms();
    List<RoomResponse> searchRooms(String keyword);

    RoomResponse getRoomById(Long id);
    Room save(Room room);
    void deleteRoom(Long id);
    List<RoomResponse> getAvailableRoomsByTypeAndDate(String roomType, LocalDate checkInDate, LocalDate checkOutDate);
    List<RoomResponse> searchRoomsByType(String searchQuery);
    Room getRoomEntityById(Long id);
    Page<RoomResponse> getAllRoomsPaginated(Pageable pageable);

}