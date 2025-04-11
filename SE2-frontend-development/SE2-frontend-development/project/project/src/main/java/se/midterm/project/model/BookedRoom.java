// BookedRoom.java
package se.midterm.project.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class BookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private String guestFullName;
    private String guestPhone;

    private Integer numOfAdults;
    private Integer numOfChildren;

    private String bookingConfirmationCode;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    // Getters and Setters
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getGuestFullName() { return guestFullName; }
    public void setGuestFullName(String guestFullName) { this.guestFullName = guestFullName; }

    public String getGuestPhone() { return guestPhone; }
    public void setGuestPhone(String guestPhone) { this.guestPhone = guestPhone; }

    public Integer getNumOfAdults() { return numOfAdults; }
    public void setNumOfAdults(Integer numOfAdults) { this.numOfAdults = numOfAdults; }

    public Integer getNumOfChildren() { return numOfChildren; }
    public void setNumOfChildren(Integer numOfChildren) { this.numOfChildren = numOfChildren; }

    public String getBookingConfirmationCode() { return bookingConfirmationCode; }
    public void setBookingConfirmationCode(String bookingConfirmationCode) { this.bookingConfirmationCode = bookingConfirmationCode; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
}
