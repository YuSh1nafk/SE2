package se.midterm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.midterm.project.model.BookedRoom;
import se.midterm.project.model.BookingStatus;
import se.midterm.project.model.Room;
import se.midterm.project.repository.BookedRoomRepository;
import se.midterm.project.repository.RoomRepository;
import se.midterm.project.response.BookingResponse;
import se.midterm.project.response.RoomResponse;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookedRoomServiceImpl implements IBookedRoomService {

    @Autowired
    private BookedRoomRepository bookedRoomRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public BookingResponse bookRoomPending(Long roomId, Long userId, String guestFullName, String guestPhone,
                                           LocalDate checkInDate, LocalDate checkOutDate,
                                           int numOfAdults, int numOfChildren) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID: " + roomId));

        List<BookedRoom> conflictingBookings = bookedRoomRepository
                .findByRoomIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(roomId, checkOutDate, checkInDate);

        boolean isBooked = conflictingBookings.stream()
               .anyMatch(booking ->
                        booking.getStatus() == BookingStatus.Confirmed ||
                                booking.getStatus() == BookingStatus.Pending);

        if (isBooked) {
            throw new IllegalStateException("Room is already booked for these dates");
        }

        BookedRoom booking = new BookedRoom();
        booking.setRoom(room);
        booking.setUserId(userId);
        booking.setGuestFullName(guestFullName);
        booking.setGuestPhone(guestPhone);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setNumOfAdults(numOfAdults);
        booking.setNumOfChildren(numOfChildren);
        booking.setStatus(BookingStatus.Pending);

        BookedRoom savedBooking = bookedRoomRepository.save(booking);

        String confirmationCode = String.format("%05d", savedBooking.getBookingId());
        savedBooking.setBookingConfirmationCode(confirmationCode);
        bookedRoomRepository.save(savedBooking);

        return mapToBookingResponse(savedBooking);
    }

    @Override
    public List<BookedRoom> getPendingBookings() {
        return bookedRoomRepository.findByStatus(BookingStatus.Pending);
    }

    @Override
    public void acceptBooking(Long bookingId) {
        BookedRoom booking = bookedRoomRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));

        if (booking.getStatus() != BookingStatus.Pending) {
            throw new IllegalStateException("Only pending bookings can be accepted");
        }

        booking.setStatus(BookingStatus.Confirmed);
        Room room = booking.getRoom();
        room.setAvailable(false);
        bookedRoomRepository.save(booking);
        roomRepository.save(room);
    }

    @Override
    public void declineBooking(Long bookingId) {
        BookedRoom booking = bookedRoomRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));

        if (booking.getStatus() != BookingStatus.Pending) {
            throw new IllegalStateException("Only pending bookings can be declined");
        }

        booking.setStatus(BookingStatus.Cancelled);
        bookedRoomRepository.save(booking);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        BookedRoom booking = bookedRoomRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));

        if (booking.getStatus() != BookingStatus.Pending) {
            throw new IllegalStateException("Only pending bookings can be cancelled");
        }

        bookedRoomRepository.delete(booking);
    }

    @Override
    public int countPendingBookings() {
        return bookedRoomRepository.countByStatus(BookingStatus.Pending);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        BookedRoom booking = bookedRoomRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));

        if (booking.getStatus() == BookingStatus.Confirmed) {
            bookedRoomRepository.delete(booking);
        } else {
            throw new IllegalStateException("Only confirmed bookings can be deleted by admin.");
        }
    }

    @Override
    public void cancelAllBookingsByUser(Long userId) {
        List<BookedRoom> bookings = bookedRoomRepository.findByUserId(userId);
        bookedRoomRepository.deleteAll(bookings);
    }

    @Override
    public List<BookingResponse> getBookingsByUserId(Long userId) {
        List<BookedRoom> bookings = bookedRoomRepository.findByUserId(userId);
        if (bookings == null || bookings.isEmpty()) {
            return Collections.emptyList();
        }
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getConfirmedBookings() {
        return bookedRoomRepository.findByStatus(BookingStatus.Confirmed)
                .stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        List<BookedRoom> bookings = bookedRoomRepository.findAll();
        if (bookings == null || bookings.isEmpty()) {
            return Collections.emptyList();
        }
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    private BookingResponse mapToBookingResponse(BookedRoom booking) {
        Room room = booking.getRoom();
        RoomResponse roomResponse = new RoomResponse(
                room.getId(),
                room.getRoomType(),
                room.getRoomPrice(),
                !room.isAvailable(),
                room.getPhotoUrl(),
                room.getRoomNumber(),
                room.getRoomArea(),
                room.getRoomCapacity(),
                room.getDescription(),
                room.getAmenities(),
                room.getAllImages()
        );

        boolean cancellable = booking.getStatus() == BookingStatus.Pending;

        return new BookingResponse(
                booking.getBookingId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getGuestFullName(),
                booking.getGuestPhone(),
                booking.getNumOfAdults(),
                booking.getNumOfChildren(),
                booking.getBookingConfirmationCode(),
                roomResponse,
                booking.getStatus().name(),
                cancellable
        );
    }
}
