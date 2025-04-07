package se.midterm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.midterm.project.model.Room;
import se.midterm.project.repository.RoomRepository;
import se.midterm.project.response.RoomResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::mapToRoomResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> getAvailableRoomsByTypeAndDate(String roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        return roomRepository.findAvailableRoomsByTypeAndDate(roomType, checkInDate, checkOutDate).stream()
                .map(this::mapToRoomResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID: " + id));
        return mapToRoomResponse(room);
    }

    @Override
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    private RoomResponse mapToRoomResponse(Room room) {
        return new RoomResponse(
                room.getId(), room.getRoomType(), room.getRoomPrice(), !room.isAvailable(), room.getPhotoUrl(),
                room.getRoomNumber(), room.getRoomArea(), room.getRoomCapacity(), room.getDescription(),
                room.getAmenities(), room.getAllImages()
        );
    }
}