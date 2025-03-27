package se.midterm.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
        rooms.forEach(room -> {
            if (!room.getPhotoUrl().startsWith("/")) {
                room.setPhotoUrl("/" + room.getPhotoUrl());
            }
        });
        model.addAttribute("rooms", rooms);
        model.addAttribute("activePage", "home");
        return "home";
    }

    @GetMapping("/browseRoom")
    public String browseAllRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        rooms.forEach(room -> {
            if (!room.getPhotoUrl().startsWith("/")) {
                room.setPhotoUrl("/" + room.getPhotoUrl());
            }
        });
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
        availableRooms.forEach(room -> {
            if (!room.getPhotoUrl().startsWith("/")) {
                room.setPhotoUrl("/" + room.getPhotoUrl());
            }
        });
        model.addAttribute("rooms", availableRooms);
        model.addAttribute("activePage", "browseRoom");
        return "browseRoom";
    }
}