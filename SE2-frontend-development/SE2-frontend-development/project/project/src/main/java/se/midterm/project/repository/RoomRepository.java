package se.midterm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.midterm.project.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.id NOT IN (" +
            "SELECT b.room.id FROM BookedRoom b WHERE " +
            "(b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate) " +
            "AND b.status != 'Cancelled')")
    List<Room> findAvailableRoomsByTypeAndDate(
            @Param("roomType") String roomType,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);
}