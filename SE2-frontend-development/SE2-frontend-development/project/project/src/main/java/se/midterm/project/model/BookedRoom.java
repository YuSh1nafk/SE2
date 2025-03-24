package se.midterm.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "customer_id")
    private Integer customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    // Optional fields (remove or add to database)
    @Column(name = "guest_fullName")
    private String guestFullName;

    @Column(name = "guest_email")
    private String guestEmail;

    @Column(name = "adults")
    private int numOfAdults;

    @Column(name = "children")
    private int numOfChildren;

    @Column(name = "total_guest")
    private int totalNumOfGuest;

    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;
}

enum BookingStatus {
    Pending, Confirmed, Cancelled
}