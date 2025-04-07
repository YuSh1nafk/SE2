package se.midterm.project.service;

import se.midterm.project.response.BookingResponse;

import java.time.LocalDate;
import java.util.List;

public interface IBookedRoomService {
    BookingResponse bookRoom(Long roomId, Long userId, String guestFullName, String guestPhone,
                             LocalDate checkInDate, LocalDate checkOutDate, int numOfAdults, int numOfChildren);
    List<BookingResponse> getBookingsByUserId(Long userId);
    void cancelBooking(Long bookingId);
    List<BookingResponse> getAllBookings();
    List<BookingResponse> getPendingBookings();
    void confirmBooking(Long bookingId);
}