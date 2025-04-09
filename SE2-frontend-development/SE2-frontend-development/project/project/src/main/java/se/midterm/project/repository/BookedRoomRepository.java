package se.midterm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.midterm.project.model.BookedRoom;

import java.time.LocalDate;
import java.util.List;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
    List<BookedRoom> findByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            Long roomId, LocalDate checkOut, LocalDate checkIn);

    List<BookedRoom> findByUserId(Long userId);
}