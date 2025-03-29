package se.midterm.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.midterm.project.model.Room;
import se.midterm.project.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;
    @GetMapping("/")
    public String home(Model model) {
        List<Room> rooms = roomRepository.findAll();
        formatPhotoUrls(rooms);
        model.addAttribute("rooms", rooms);
        model.addAttribute("activePage", "home");
        return "home";
    }
    @GetMapping("/admin/rooms")
    @PreAuthorize("hasRole('ADMIN')")
    public String manageRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        formatPhotoUrls(rooms);
        model.addAttribute("rooms", rooms);
        model.addAttribute("activePage", "manageRooms");
        return "admin/rooms";
    }
    @GetMapping("/browseRoom")
    public String browseAllRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        formatPhotoUrls(rooms);
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
        List<Room> availableRooms = roomRepository.findAvailableRoomsByTypeAndDate(roomType, checkInDate, checkOutDate);
        formatPhotoUrls(availableRooms);
        model.addAttribute("rooms", availableRooms);
        model.addAttribute("activePage", "browseRoom");
        return "browseRoom";
    }
    private void formatPhotoUrls(List<Room> rooms) {
        rooms.forEach(room -> {
            if (!room.getPhotoUrl().startsWith("/")) {
                room.setPhotoUrl("/" + room.getPhotoUrl());
            }
        });
    }
}