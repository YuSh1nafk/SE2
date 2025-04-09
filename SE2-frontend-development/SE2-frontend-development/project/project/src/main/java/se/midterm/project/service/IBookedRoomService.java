package se.midterm.project.service;

import se.midterm.project.response.BookingResponse;

import java.time.LocalDate;
import java.util.List;

public interface IBookedRoomService {
    BookingResponse bookRoomPending(Long roomId, Long userId, String guestFullName, String guestPhone,
                                    LocalDate checkInDate, LocalDate checkOutDate, int numOfAdults, int numOfChildren);
    void acceptBooking(Long bookingId);
    void declineBooking(Long bookingId);
    List<BookingResponse> getBookingsByUserId(Long userId);
    void cancelBooking(Long bookingId);
    List<BookingResponse> getAllBookings();
}