package se.midterm.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "booking")
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long bookingId;

    @Column(name = "checkin")
    private LocalDate checkInDate;

    @Column(name = "checkout")
    private LocalDate checkOutDate;

    @Setter
    @Getter
    @Column(name = "customer_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "guest_fullName")
    private String guestFullName;

    @Column(name = "guest_phone") // Added for phone
    private String guestPhone;

    @Column(name = "adults")
    private int numOfAdults;

    @Column(name = "children")
    private int numOfChildren;

    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;

    public BookedRoom() {
        this.status = BookingStatus.Pending;
    }
}