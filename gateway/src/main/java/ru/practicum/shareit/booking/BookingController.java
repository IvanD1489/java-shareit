package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingClient bookingClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody BookingDto bookingDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approve(@PathVariable("bookingId") Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam(value = "approved") Boolean approved) {
        return bookingClient.approve(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getItem(@PathVariable("bookingId") Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.getItem(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.getItems(userId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsByOwnerItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.getBookingsByOwnerItems(userId);
    }

}
