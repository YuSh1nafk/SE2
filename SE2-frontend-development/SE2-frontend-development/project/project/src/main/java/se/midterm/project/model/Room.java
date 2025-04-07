package se.midterm.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
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

    @Column(name = "image_url2")
    private String imageUrl2;

    @Column(name = "image_url3")
    private String imageUrl3;

    @Column(name = "image_url4")
    private String imageUrl4;

    @Column(name = "image_url5")
    private String imageUrl5;

    @Column(name = "image_url6")
    private String imageUrl6;

    @Column(name = "image_url7")
    private String imageUrl7;

    @Column(name = "description")
    private String description;

    @Column(name = "area")
    private double roomArea;

    @Column(name = "capacity")
    private int roomCapacity;

    @Column(name = "amenities")
    private String amenities;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedRoom> bookings;

    public Room() {
        this.bookings = new ArrayList<>();
        this.status = RoomStatus.Available;
    }

    public List<String> getAllImages() {
        return Arrays.asList(photoUrl, imageUrl2, imageUrl3, imageUrl4, imageUrl5, imageUrl6, imageUrl7)
                .stream()
                .filter(url -> url != null && !url.isEmpty())
                .collect(Collectors.toList());
    }

    public boolean isAvailable() {
        return status == RoomStatus.Available;
    }

    public void setAvailable(boolean available) {
        this.status = available ? RoomStatus.Available : RoomStatus.Booked;
    }
}