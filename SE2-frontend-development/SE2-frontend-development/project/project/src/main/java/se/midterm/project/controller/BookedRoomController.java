package se.midterm.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.midterm.project.model.BookedRoom;
import se.midterm.project.response.BookingResponse;
import se.midterm.project.service.IBookedRoomService;
import se.midterm.project.model.MyUserDetail;

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
        if (auth == null || !auth.isAuthenticated()) {
            logger.severe("User not authenticated for /my-bookings");
            return "redirect:/auth/login";
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof MyUserDetail)) {
            logger.severe("Principal is not MyUserDetail: " + principal.getClass() + ", value: " + principal);
            return "redirect:/auth/login";
        }

        MyUserDetail userDetail = (MyUserDetail) principal;
        Long userId = userDetail.getUser().getId();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        logger.info("Fetching bookings for user: " + userId + ", isAdmin: " + isAdmin);

        List<BookingResponse> bookings;
        if (isAdmin) {
            bookings = bookedRoomService.getConfirmedBookings();
            int pendingCount = bookedRoomService.countPendingBookings();
            model.addAttribute("pendingCount", pendingCount);

            model.addAttribute("bookings", bookings);
            model.addAttribute("activePage", "myBooking");
            return "admin/myBooking";
        } else {
            bookings = bookedRoomService.getBookingsByUserId(userId);
            model.addAttribute("bookings", bookings);
            model.addAttribute("activePage", "myBooking");
            return "customer/myBooking";
        }
    }
    @GetMapping("admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBookingByAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookedRoomService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("successMessage", "Booking deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete booking: " + e.getMessage());
        }
        return "redirect:/my-bookings";
    }


    @PostMapping("/bookings/cancel-all")
    public String cancelAllBookings(RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        Object principal = auth.getPrincipal();
        if (!(principal instanceof MyUserDetail)) {
            return "redirect:/auth/login";
        }

        MyUserDetail userDetail = (MyUserDetail) principal;
        Long userId = userDetail.getUser().getId();

        bookedRoomService.cancelAllBookingsByUser(userId);
        redirectAttributes.addFlashAttribute("successMessage", "All bookings cancelled successfully.");
        return "redirect:/my-bookings";
    }
    @GetMapping("/booking-confirmation")
    public String showPendingBookings(Model model) {
        List<BookedRoom> pendingBookings = bookedRoomService.getPendingBookings();
        model.addAttribute("pendingBookings", pendingBookings);

        return "admin/confirmationPage";
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
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                logger.severe("User not authenticated");
                redirectAttributes.addFlashAttribute("error", "Please log in to book a room.");
                return "redirect:/auth/login";
            }

            Object principal = authentication.getPrincipal();
            if (!(principal instanceof MyUserDetail)) {
                logger.severe("Principal is not MyUserDetail");
                redirectAttributes.addFlashAttribute("error", "Invalid user authentication");
                return "redirect:/auth/login";
            }

            MyUserDetail userDetail = (MyUserDetail) principal;
            Long userId = userDetail.getUser().getId();

            logger.info("Booking attempt: roomId=" + roomId + ", userId=" + userId);

            if (roomId == null) {
                redirectAttributes.addFlashAttribute("error", "Room ID is missing");
                return "redirect:/browseRoom";
            }

            LocalDate checkInDate = LocalDate.parse(checkInDateStr);
            LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);

            
            BookingResponse booking = bookedRoomService.bookRoomPending(
                    roomId, userId, guestFullName, guestPhone,
                    checkInDate, checkOutDate, numOfAdults, numOfChildren);

            redirectAttributes.addFlashAttribute("booking", booking);
            return "redirect:/my-bookings";
            
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", "This room is full during this time period");
            return "redirect:/booking?roomId=" + roomId;
        } catch (Exception e) {
            logger.severe("Booking error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Booking failed: " + e.getMessage());
            return "redirect:/booking?roomId=" + roomId;
        }
    }

    @PostMapping("/bookings/{bookingId}/accept")
    public String acceptBooking(@PathVariable Long bookingId, RedirectAttributes redirectAttributes) {
        bookedRoomService.acceptBooking(bookingId);
        redirectAttributes.addFlashAttribute("successMessage", "Booking confirmed!");
        return "redirect:/my-bookings";
    }

    @PostMapping("/bookings/{bookingId}/decline")
    public String declineBooking(@PathVariable Long bookingId, RedirectAttributes redirectAttributes) {
        bookedRoomService.declineBooking(bookingId);
        redirectAttributes.addFlashAttribute("successMessage", "Booking declined.");
        return "redirect:/my-bookings";
    }

    @PostMapping("/bookings/{bookingId}/cancel")
    public String cancelBooking(@PathVariable Long bookingId, RedirectAttributes redirectAttributes) {
        try {
            bookedRoomService.cancelBooking(bookingId);
            redirectAttributes.addFlashAttribute("successMessage", "Booking cancelled successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cancel failed: " + e.getMessage());
        }
        return "redirect:/my-bookings";
    }

}
