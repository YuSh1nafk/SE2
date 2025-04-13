package se.midterm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.midterm.project.model.BookedRoom;
import se.midterm.project.model.BookingStatus;

import java.time.LocalDate;
import java.util.List;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
    List<BookedRoom> findByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            Long roomId, LocalDate checkOut, LocalDate checkIn);
    List<BookedRoom> findByGuestFullNameContainingIgnoreCase(String keyword);

    int countByStatus(BookingStatus status);

    List<BookedRoom> findByUserId(Long userId);
    List<BookedRoom> findByStatus(BookingStatus status);

}