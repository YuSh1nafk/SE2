package se.midterm.project.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomResponse {
    private Long id; // Maps to roomID
    private String roomType;
    private BigDecimal roomPrice; // Maps to price
    private boolean isBooked;
    private String photoUrl; // Maps to roomImageUrl
    private String roomNumber;
    private double roomArea;
    private int roomCapacity;
    private String description;
    private String amenities;
    private List<String> roomImages;
    private List<BookingResponse> bookings;

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean isBooked, String photoUrl,
                        String roomNumber, double roomArea, int roomCapacity, String description, String amenities,
                        List<String> roomImages) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photoUrl = photoUrl;
        this.roomNumber = roomNumber;
        this.roomArea = roomArea;
        this.roomCapacity = roomCapacity;
        this.description = description;
        this.amenities = amenities;
        this.roomImages = roomImages;
    }
}