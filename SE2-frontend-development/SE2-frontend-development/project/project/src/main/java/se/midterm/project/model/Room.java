package se.midterm.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "number")
    private String roomNumber;

    @Column(name = "type")
    private String roomType;

    @Column(name = "price")
    private BigDecimal roomPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column(name = "image_url")
    private String photoUrl;

    // Optional: Remove if not needed in the database
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedRoom> bookings;

    public Room() {
        this.bookings = new ArrayList<>();
    }

}

enum RoomStatus {
    Available, Booked
}