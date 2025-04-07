package se.midterm.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.midterm.project.response.RoomResponse;
import se.midterm.project.service.IRoomService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private IRoomService roomService;

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
        RoomResponse room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        model.addAttribute("roomImages", room.getRoomImages());
        model.addAttribute("amenitiesList", room.getAmenities() != null ? List.of(room.getAmenities().split(",")) : List.of());
        return "detailPage";
    }

    @GetMapping("/booking")
    public String showBookingForm(@RequestParam("roomId") Long roomId, Model model) {
        RoomResponse room = roomService.getRoomById(roomId);
        model.addAttribute("room", room);
        return "bookingForm";
    }
}