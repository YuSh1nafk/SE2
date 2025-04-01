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


    // Optional: Remove if not needed in the database
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
    }
    public List<String> getAllImages() {
        List<String> images = new ArrayList<>();
        if (imageUrl2 != null && !imageUrl2.isEmpty()) images.add(imageUrl2);
        if (imageUrl3 != null && !imageUrl3.isEmpty()) images.add(imageUrl3);
        if (imageUrl4 != null && !imageUrl4.isEmpty()) images.add(imageUrl4);
        if (imageUrl5 != null && !imageUrl5.isEmpty()) images.add(imageUrl5);
        if (imageUrl6 != null && !imageUrl6.isEmpty()) images.add(imageUrl6);
        if (imageUrl7 != null && !imageUrl7.isEmpty()) images.add(imageUrl7);
        return images;
    }


}

enum RoomStatus {
    Available, Booked
}
