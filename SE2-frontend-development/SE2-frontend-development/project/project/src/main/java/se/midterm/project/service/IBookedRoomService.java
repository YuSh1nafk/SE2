package se.midterm.project.service;

import se.midterm.project.model.BookedRoom;
import se.midterm.project.model.BookingStatus;
import se.midterm.project.response.BookingResponse;

import java.time.LocalDate;
import java.util.List;

public interface IBookedRoomService {
    BookingResponse bookRoomPending(Long roomId, Long userId, String guestFullName, String guestPhone,
                                    LocalDate checkInDate, LocalDate checkOutDate,
                                    int numOfAdults, int numOfChildren);
    List<BookedRoom> getPendingBookings();
    List<BookingResponse> getConfirmedBookings();

    void acceptBooking(Long bookingId);

    void declineBooking(Long bookingId);

    void cancelBooking(Long bookingId);
    void deleteBookingByUser(Long bookingId, Long userId);

    void deleteBooking(Long bookingId);
    int countPendingBookings();


    void cancelAllBookingsByUser(Long userId);

    List<BookingResponse> getBookingsByUserId(Long userId);

    List<BookingResponse> getAllBookings();
}
