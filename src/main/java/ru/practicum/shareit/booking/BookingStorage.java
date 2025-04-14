package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

public interface BookingStorage {

    BookingDto create(BookingDto entity, Long userId);

    BookingDto approve(Long bookingId, Long userId, Boolean approved);

    BookingDto getItem(Long bookingId, Long userId);

    Collection<BookingDto> getItems(Long userId);

    Collection<BookingDto> getBookingsByOwnerItems(Long userId);

    Boolean isAvailableForComment(Long itemId, Long bookerId);

}
