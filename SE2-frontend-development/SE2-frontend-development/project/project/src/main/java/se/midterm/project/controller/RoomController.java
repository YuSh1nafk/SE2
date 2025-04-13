package se.midterm.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.midterm.project.model.Room;
import se.midterm.project.repository.RoomRepository;
import se.midterm.project.response.RoomResponse;
import se.midterm.project.service.IRoomService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class RoomController {

    @Autowired
    private IRoomService roomService;
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<RoomResponse> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        model.addAttribute("activePage", "home");
        return "home";
    }

    @GetMapping("/admin/rooms")
    @PreAuthorize("hasRole('ADMIN')")
    public String manageRooms(Model model) {
        List<RoomResponse> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        model.addAttribute("activePage", "manageRooms");
        return "admin/rooms";
    }

    @GetMapping("/browseRoom")
    public String browseAllRooms(Model model) {
        List<RoomResponse> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        model.addAttribute("activePage", "browseRoom");
        return "browseRoom";
    }
        @GetMapping("/manageRoom")
    @PreAuthorize("hasRole('ADMIN')")
    public String manageRoom(@RequestParam(value = "search", required = false) String searchQuery, Model model) {
        List<RoomResponse> rooms;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            if (searchQuery.matches("\\d+")) {
                try {
                    Long roomId = Long.parseLong(searchQuery);
                    RoomResponse room = roomService.getRoomById(roomId);
                    if (room != null) {
                        rooms = Collections.singletonList(room); 
                    } else {
                        rooms = Collections.emptyList();
                        model.addAttribute("errorMessage", "Room not found for ID: " + roomId);
                    }
                } catch (NumberFormatException e) {
                    rooms = Collections.emptyList(); 
                    model.addAttribute("errorMessage", "Invalid room ID format");
                }
            } else {
                rooms = roomService.searchRoomsByType(searchQuery); 
            }
        } else {
            rooms = roomService.getAllRooms();
        }
        model.addAttribute("rooms", rooms);
        model.addAttribute("searchQuery", searchQuery); 
        model.addAttribute("activePage", "manageRoom");
        return "admin/manageRoom";
    }

    // Add Room - Form
    @GetMapping("/addRoom")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "admin/addNewRoom";
    }

    // Add Room - Submit
    @PostMapping("/addRoom")
    public String addRoom(@ModelAttribute Room room,
                          @RequestParam("mainImage") MultipartFile mainImage,
                          @RequestParam(value = "roomImages", required = false) MultipartFile[] roomImages,
                          RedirectAttributes redirectAttributes) {

        try {
            String uploadDir = System.getProperty("java.io.tmpdir") + "/hotel-images/";
            java.io.File dir = new java.io.File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!mainImage.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(mainImage.getOriginalFilename());
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(mainImage.getInputStream(), filePath);
                room.setPhotoUrl("/images/" + fileName);
            }
            if (roomImages != null && roomImages.length > 0) {
                int index = 2;
                for (MultipartFile image : roomImages) {
                    if (!image.isEmpty() && index <= 7) {
                        String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(image.getOriginalFilename());
                        Path filePath = Paths.get(uploadDir, fileName);
                        Files.copy(image.getInputStream(), filePath);
                        String imageUrl = "/images/" + fileName;

                        switch (index) {
                            case 2 -> room.setImageUrl2(imageUrl);
                            case 3 -> room.setImageUrl3(imageUrl);
                            case 4 -> room.setImageUrl4(imageUrl);
                            case 5 -> room.setImageUrl5(imageUrl);
                            case 6 -> room.setImageUrl6(imageUrl);
                            case 7 -> room.setImageUrl7(imageUrl);
                        }

                        index++;
                    }
                }
            }
            roomService.save(room);
            redirectAttributes.addFlashAttribute("successMessage", "Room added successfully");
            return "redirect:/manageRoom";

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to upload image(s): " + e.getMessage());
            return "redirect:/addRoom";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add room: " + e.getMessage());
            return "redirect:/addRoom";
        }
    }


    // Edit Room - Form
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editRoomForm(@PathVariable Long id, Model model) {
        RoomResponse room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        return "admin/editRoom";
    }

    // Edit Room - Submit
    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateRoom(@PathVariable Long id,
                             @ModelAttribute Room room,
                             @RequestParam("mainImage") MultipartFile mainImage,
                             @RequestParam(value = "roomImages", required = false) MultipartFile[] roomImages,
                             RedirectAttributes redirectAttributes) {

        room.setId(id);
        Room existingRoom = roomService.getRoomEntityById(id);

        try {
            if (!mainImage.isEmpty()) {
                String uploadDir = System.getProperty("java.io.tmpdir") + "/hotel-images/";
                new java.io.File(uploadDir).mkdirs();

                String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(mainImage.getOriginalFilename());
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(mainImage.getInputStream(), filePath);
                room.setPhotoUrl("/images/" + fileName);
            } else {
                room.setPhotoUrl(existingRoom.getPhotoUrl());
            }
            room.setImageUrl2(existingRoom.getImageUrl2());
            room.setImageUrl3(existingRoom.getImageUrl3());
            room.setImageUrl4(existingRoom.getImageUrl4());
            room.setImageUrl5(existingRoom.getImageUrl5());
            room.setImageUrl6(existingRoom.getImageUrl6());
            room.setImageUrl7(existingRoom.getImageUrl7());

            if (roomImages != null && roomImages.length > 0) {
                int index = 2;
                for (MultipartFile image : roomImages) {
                    if (!image.isEmpty() && index <= 7) {
                        String uploadDir = System.getProperty("java.io.tmpdir") + "/hotel-images/";
                        new java.io.File(uploadDir).mkdirs();

                        String fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(image.getOriginalFilename());
                        Path filePath = Paths.get(uploadDir, fileName);
                        Files.copy(image.getInputStream(), filePath);
                        String imageUrl = "/images/" + fileName;

                        switch (index) {
                            case 2 -> room.setImageUrl2(imageUrl);
                            case 3 -> room.setImageUrl3(imageUrl);
                            case 4 -> room.setImageUrl4(imageUrl);
                            case 5 -> room.setImageUrl5(imageUrl);
                            case 6 -> room.setImageUrl6(imageUrl);
                            case 7 -> room.setImageUrl7(imageUrl);
                        }
                        index++;
                    }
                }
            }

            roomService.save(room);
            redirectAttributes.addFlashAttribute("success", "Room updated successfully");
            return "redirect:/manageRoom";

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Image upload failed: " + e.getMessage());
            return "redirect:/edit/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Room update failed: " + e.getMessage());
            return "redirect:/edit/" + id;
        }
    }


    // Delete Room
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            roomService.deleteRoom(id);
            redirectAttributes.addFlashAttribute("success", "Room deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete room: It is currently booked.");
        }
        return "redirect:/manageRoom";
    }


    @PostMapping("/search")
    public String searchRooms(
            @RequestParam("checkInDate") String checkInDateStr,
            @RequestParam("checkOutDate") String checkOutDateStr,
            @RequestParam("roomType") String roomType,
            Model model) {
        LocalDate checkInDate = LocalDate.parse(checkInDateStr);
        LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);

        List<RoomResponse> availableRooms = roomService.getAvailableRoomsByTypeAndDate(roomType, checkInDate, checkOutDate);
        model.addAttribute("rooms", availableRooms);
        model.addAttribute("activePage", "browseRoom");
        return "browseRoom";
    }

    @GetMapping("/rooms/{id}")
    public String viewRoomDetails(@PathVariable Long id, Model model) {
        System.out.println("Fetching room with ID: " + id);
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id: " + id));
        formatPhotoUrls(Collections.singletonList(room));
        List<String> amenitiesList = Arrays.asList(room.getAmenities().split(","));
        model.addAttribute("room", room);
        model.addAttribute("roomImages", room.getAllImages());
        model.addAttribute("amenitiesList", amenitiesList);
        return "detailPage";
    }

    @GetMapping("/booking")
    public String showBookingForm(@RequestParam("roomId") Long roomId, Model model) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID: " + roomId));
        model.addAttribute("room", room);
        return "bookingForm";
    }


    private void formatPhotoUrls(List<Room> rooms) {
        if (rooms == null) return;
        rooms.forEach(room -> {
            room.setPhotoUrl(room.getPhotoUrl() != null && !room.getPhotoUrl().startsWith("/")
                    ? "/" + room.getPhotoUrl()
                    : room.getPhotoUrl());
            room.setImageUrl2(formatSimple(room.getImageUrl2()));
            room.setImageUrl3(formatSimple(room.getImageUrl3()));
            room.setImageUrl4(formatSimple(room.getImageUrl4()));
            room.setImageUrl5(formatSimple(room.getImageUrl5()));
            room.setImageUrl6(formatSimple(room.getImageUrl6()));
            room.setImageUrl7(formatSimple(room.getImageUrl7()));
        });
    }

    private String formatSimple(String url) {
        return url != null && !url.startsWith("/") ? "/" + url : url;
    }

}
