package se.midterm.project.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id; // Maps to bookingID
    private LocalDate checkInDate; // Maps to checkIn
    private LocalDate checkOutDate; // Maps to checkOut
    private String guestFullName;
    private String guestPhone; // Maps to guestPhoneNumber
    private int numOfAdults; // Maps to adults
    private int numOfChildren; // Maps to children
    private String bookingConfirmationCode; // Maps to confirmationCode
    private RoomResponse room; // Contains roomType, roomID (id), price, imageUrl
    private String status; // Add status as String
    private boolean cancellable; // Add cancellable flag

    public BookingResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate, String bookingConfirmationCode) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}