package se.midterm.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.midterm.project.model.Room;
import se.midterm.project.repository.RoomRepository;
import se.midterm.project.response.RoomResponse;
import se.midterm.project.service.IRoomService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public String manageRoom(Model model) {
        List<RoomResponse> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
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
    @PreAuthorize("hasRole('ADMIN')")
    public String addRoom(@ModelAttribute Room room, RedirectAttributes redirectAttributes) {
        roomService.save(room);
        redirectAttributes.addFlashAttribute("success", "Room added successfully");
        return "redirect:/manageRoom";
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
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room room,
                             RedirectAttributes redirectAttributes) {
        room.setId(id);
        roomService.save(room);
        redirectAttributes.addFlashAttribute("success", "Room updated successfully");
        return "redirect:/manageRoom";
    }

    // Delete Room
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        roomService.deleteRoom(id);
        redirectAttributes.addFlashAttribute("success", "Room deleted successfully");
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