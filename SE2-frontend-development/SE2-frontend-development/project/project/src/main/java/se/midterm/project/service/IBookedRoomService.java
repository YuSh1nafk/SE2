package se.midterm.project.service;

import se.midterm.project.response.BookingResponse;

import java.time.LocalDate;
import java.util.List;

public interface IBookedRoomService {
    BookingResponse bookRoomPending(Long roomId, Long userId, String guestFullName, String guestPhone,
                                    LocalDate checkInDate, LocalDate checkOutDate,
                                    int numOfAdults, int numOfChildren);

    void acceptBooking(Long bookingId);

    void declineBooking(Long bookingId);

    void cancelBooking(Long bookingId); // giờ là xóa luôn

    void deleteBooking(Long bookingId); // nếu cần riêng tên deleteBooking cũng được

    List<BookingResponse> getBookingsByUserId(Long userId);

    List<BookingResponse> getAllBookings();
}