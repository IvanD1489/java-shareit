package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserStorage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final BookingStorage bookingStorage;

    public BookingDto create(BookingDto bookingDto, Long userId) {
        validateBooking(bookingDto, userId);
        validateUser(userId);
        return bookingStorage.create(bookingDto, userId);
    }

    public BookingDto approve(Long bookingId, Long userId, Boolean approved) {
        validateApprove(bookingId, userId);
        return bookingStorage.approve(bookingId, userId, approved);
    }

    public BookingDto getItem(Long bookingId, Long userId) {
        validateGetItem(bookingId, userId);
        return bookingStorage.getItem(bookingId, userId);
    }

    public Collection<BookingDto> getItems(Long userId) {
        validateUser(userId);
        return bookingStorage.getItems(userId);
    }

    public Collection<BookingDto> getBookingsByOwnerItems(Long userId) {
        validateUser(userId);
        return bookingStorage.getBookingsByOwnerItems(userId);
    }

    private void validateBooking(BookingDto bookingDto, Long userId) {
        if (bookingDto.getItemId() == null) {
            throw new NotFoundException("Could not find item to book");
        }
        ItemDto itemDto = itemStorage.getItem(bookingDto.getItemId(), userId);
        if (itemDto == null) {
            throw new NotFoundException("Could not find item to book");
        }
        if (!itemDto.getAvailable()) {
            throw new ValidationException("This item is unavailable");
        }
        if (bookingDto.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Booking end date cant be in past");
        }
        if (bookingDto.getStart().equals(bookingDto.getEnd())) {
            throw new ValidationException("Booking end date cant be equal to start date");
        }
        if (bookingDto.getStart() == null || bookingDto.getEnd() == null) {
            throw new ValidationException("Booking end and start date cant be null");
        }
        if (bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Booking start date cant be in past");
        }
    }

    private void validateApprove(Long bookingId, Long userId) {
        if (bookingStorage.getItem(bookingId, userId) == null) {
            throw new NotFoundException("Could not booking item to book");
        }
        if (!Objects.equals(itemStorage.getItem(bookingStorage.getItem(bookingId, userId).getItemId(), userId).getOwner(), userId)) {
            throw new ValidationException("Wrong user");
        }
    }

    private void validateGetItem(Long bookingId, Long userId) {
        if (bookingStorage.getItem(bookingId, userId) == null) {
            throw new NotFoundException("Could not booking item to book");
        }
        if (!(
                Objects.equals(bookingStorage.getItem(bookingId, userId).getBookerId(), userId) ||
                        Objects.equals(itemStorage.getItem(bookingStorage.getItem(bookingId, userId).getItemId(), userId).getOwner(), userId)
        )) {
            throw new ValidationException("Invalid user");
        }
    }

    private void validateUser(Long userId) {
        if (userStorage.getItem(userId) == null) {
            throw new NotFoundException("Could not find user with id " + userId);
        }
    }


}
