package se.midterm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.midterm.project.model.BookedRoom;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
}