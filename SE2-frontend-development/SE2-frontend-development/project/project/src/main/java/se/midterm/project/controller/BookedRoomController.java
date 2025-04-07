package se.midterm.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.midterm.project.response.BookingResponse;
import se.midterm.project.service.IBookedRoomService;

import se.midterm.project.model.MyUserDetail; // thêm cái này

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class BookedRoomController {

    private static final Logger logger = Logger.getLogger(BookedRoomController.class.getName());

    @Autowired
    private IBookedRoomService bookedRoomService;

    @GetMapping("/my-bookings")
    public String viewBookedRooms(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetail userDetail = (MyUserDetail) auth.getPrincipal();
        Long userId = userDetail.getUser().getId();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        logger.info("Fetching bookings for user: " + userId + ", isAdmin: " + isAdmin);

        List<BookingResponse> bookings;
        if (isAdmin) {
            bookings = bookedRoomService.getAllBookings();
            model.addAttribute("bookings", bookings);
            return "admin/mybooking";
        } else {
            bookings = bookedRoomService.getBookingsByUserId(userId);
            model.addAttribute("bookings", bookings);
            return "customer/mybooking";
        }
    }

    @PostMapping("/booking")
    public String bookRoom(
            @RequestParam(value = "roomId", required = false) Long roomId,
            @RequestParam("name") String guestFullName,
            @RequestParam("phone") String guestPhone,
            @RequestParam("checkInDate") String checkInDateStr,
            @RequestParam("checkOutDate") String checkOutDateStr,
            @RequestParam("adults") int numOfAdults,
            @RequestParam("children") int numOfChildren,
            Model model,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetail userDetail = (MyUserDetail) auth.getPrincipal();
            Long userId = userDetail.getUser().getId();
            logger.info("Booking attempt: roomId=" + roomId + ", userId=" + userId + ", name=" + guestFullName +
                    ", phone=" + guestPhone + ", checkIn=" + checkInDateStr + ", checkOut=" + checkOutDateStr);

            if (roomId == null) {
                logger.severe("Room ID is null");
                redirectAttributes.addFlashAttribute("error", "Room ID is missing");
                return "redirect:/browseRoom";
            }

            LocalDate checkInDate = LocalDate.parse(checkInDateStr);
            LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);

            BookingResponse booking = bookedRoomService.bookRoom(roomId, userId, guestFullName, guestPhone,
                    checkInDate, checkOutDate, numOfAdults, numOfChildren);
            logger.info("Booking saved: ID=" + booking.getId());

            redirectAttributes.addFlashAttribute("successMessage", "Booking successful!");
            model.addAttribute("booking", booking);
            return "redirect:/booking-confirmation";
        } catch (IllegalArgumentException e) {
            logger.severe("Invalid argument: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/booking?roomId=" + roomId;
        } catch (IllegalStateException e) {
            logger.severe("Room unavailable: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "The selected room is no longer available.");
            return "redirect:/browseRoom";
        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "redirect:/booking?roomId=" + roomId;
        }
    }

    @GetMapping("/booking-confirmation")
    public String showBookingConfirmation(Model model) {
        return "booking-confirmation";
    }

    @PostMapping("/bookings/{bookingId}/cancel")
    @ResponseBody
    public String cancelBooking(@PathVariable Long bookingId) {
        bookedRoomService.cancelBooking(bookingId);
        return "Booking cancelled successfully";
    }

    @GetMapping("/admin/confirmationPage")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewPendingBookings(Model model) {
        List<BookingResponse> pendingBookings = bookedRoomService.getPendingBookings();
        model.addAttribute("bookings", pendingBookings);
        return "admin/confirmationPage";
    }

    @PostMapping("/bookings/{bookingId}/confirm")
    @ResponseBody
    public String confirmBooking(@PathVariable Long bookingId) {
        bookedRoomService.confirmBooking(bookingId);
        return "Booking confirmed successfully";
    }

    @PostMapping("/bookings/{bookingId}/decline")
    @ResponseBody
    public String declineBooking(@PathVariable Long bookingId) {
        bookedRoomService.cancelBooking(bookingId);
        return "Booking declined successfully";
    }

}