package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.request.Status;
import ru.practicum.shareit.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Service
public class InDatabaseBookingStorage implements BookingStorage {

    private final BookingRepository repository;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public BookingDto create(BookingDto bookingDto, Long userId) {
        Booking booking = BookingMapper.toBooking(bookingDto);
        booking.setBooker(userId);
        booking.setStatus(Status.WAITING);
        return BookingMapper.toBookingDto(repository.save(booking), itemStorage.getItem(booking.getItemId(), booking.getBooker()), userStorage.getItem(booking.getBooker()));
    }

    @Override
    public BookingDto approve(Long bookingId, Long userId, Boolean approved) {
        Booking booking = BookingMapper.toBooking(getItem(bookingId, userId));
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return BookingMapper.toBookingDto(repository.save(booking), itemStorage.getItem(booking.getItemId(), booking.getBooker()), userStorage.getItem(booking.getBooker()));
    }

    @Override
    public BookingDto getItem(Long bookingId, Long userId) {
        Optional<Booking> optBooking = repository.findById(bookingId);
        return optBooking.map(booking -> BookingMapper.toBookingDto(booking, itemStorage.getItem(booking.getItemId(), booking.getBooker()), userStorage.getItem(booking.getBooker()))).orElse(null);
    }

    @Override
    public Collection<BookingDto> getItems(Long userId) {
        List<Booking> bookings = repository.findAll();
        return bookings.stream()
                .map(booking -> BookingMapper.toBookingDto(booking, itemStorage.getItem(booking.getItemId(), booking.getBooker()), userStorage.getItem(booking.getBooker())))
                .filter(booking -> Objects.equals(booking.getBookerId(), userId))
                .sorted(Comparator.comparing(BookingDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<BookingDto> getBookingsByOwnerItems(Long ownerId) {
        return repository.getBookingsByOwnerItems(ownerId)
                .stream()
                .map(booking -> BookingMapper.toBookingDto(booking, itemStorage.getItem(booking.getItemId(), booking.getBooker()), userStorage.getItem(booking.getBooker())))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isAvailableForComment(Long itemId, Long bookerId) {
        return !repository.isAvailableForComment(itemId, bookerId).isEmpty();
    }

}
