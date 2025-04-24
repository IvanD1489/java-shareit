package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto create(@Valid @RequestBody BookingDto bookingDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.create(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approve(@PathVariable("bookingId") Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam(value = "approved") Boolean approved) {
        return bookingService.approve(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getItem(@PathVariable("bookingId") Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getItem(bookingId, userId);
    }

    @GetMapping
    public Collection<BookingDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getItems(userId);
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getBookingsByOwnerItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBookingsByOwnerItems(userId);
    }

}
