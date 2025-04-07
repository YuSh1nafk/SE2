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
    public BookingResponse bookRoom(Long roomId, Long userId, String guestFullName, String guestPhone,
                                    LocalDate checkInDate, LocalDate checkOutDate, int numOfAdults, int numOfChildren) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID: " + roomId));

        if (!room.isAvailable()) {
            throw new IllegalStateException("Room is already booked");
        }

        BookedRoom booking = new BookedRoom();
        booking.setRoom(room);
        booking.setUserId(userId); // Thay vì String thì Long đẻ kh loi
        booking.setGuestFullName(guestFullName);
        booking.setGuestPhone(guestPhone);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setNumOfAdults(numOfAdults);
        booking.setNumOfChildren(numOfChildren);
        booking.setBookingConfirmationCode(UUID.randomUUID().toString());
        booking.setStatus(BookingStatus.Pending);

        bookedRoomRepository.save(booking);

        room.setAvailable(false);
        roomRepository.save(room);

        RoomResponse roomResponse = new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice(),
                !room.isAvailable(), room.getPhotoUrl(), room.getRoomNumber(), room.getRoomArea(),
                room.getRoomCapacity(), room.getDescription(), room.getAmenities(), room.getAllImages());
        return new BookingResponse(booking.getBookingId(), checkInDate, checkOutDate, guestFullName, guestPhone,
                numOfAdults, numOfChildren, booking.getBookingConfirmationCode(), roomResponse,
                booking.getStatus().name(), booking.getStatus() == BookingStatus.Pending);
    }

    @Override
    public List<BookingResponse> getPendingBookings() {
        List<BookedRoom> pendingBookings = bookedRoomRepository.findByStatus(BookingStatus.Pending);
        if (pendingBookings == null || pendingBookings.isEmpty()) {
            return Collections.emptyList();
        }
        return pendingBookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void confirmBooking(Long bookingId) {
        BookedRoom booking = bookedRoomRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));
        if (booking.getStatus() != BookingStatus.Pending) {
            throw new IllegalStateException("Only pending bookings can be confirmed");
        }
        booking.setStatus(BookingStatus.Confirmed);
        bookedRoomRepository.save(booking);
    }

    @Override
    public List<BookingResponse> getBookingsByUserId(Long userId) {
        List<BookedRoom> bookings = bookedRoomRepository.findByUserId(userId); // Now expects Long
        if (bookings == null || bookings.isEmpty()) {
            return Collections.emptyList();
        }
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long bookingId) {
        BookedRoom booking = bookedRoomRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));
        if (booking.getStatus() != BookingStatus.Pending) {
            throw new IllegalStateException("Only pending bookings can be cancelled");
        }
        booking.setStatus(BookingStatus.Cancelled);
        Room room = booking.getRoom();
        room.setAvailable(true);
        bookedRoomRepository.save(booking);
        roomRepository.save(room);
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
        RoomResponse roomResponse = new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice(),
                !room.isAvailable(), room.getPhotoUrl(), room.getRoomNumber(), room.getRoomArea(),
                room.getRoomCapacity(), room.getDescription(), room.getAmenities(), room.getAllImages());
        return new BookingResponse(booking.getBookingId(), booking.getCheckInDate(), booking.getCheckOutDate(),
                booking.getGuestFullName(), booking.getGuestPhone(), booking.getNumOfAdults(),
                booking.getNumOfChildren(), booking.getBookingConfirmationCode(), roomResponse,
                booking.getStatus().name(), booking.getStatus() == BookingStatus.Pending);
    }
}