package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b join Item i on i.id = b.itemId where i.owner = ?1")
    List<Booking> getBookingsByOwnerItems(Long ownerId);

    @Query("select b from Booking b where itemId = ?1 and booker = ?2 and end < CURRENT_TIMESTAMP()")
    List<Booking> isAvailableForComment(Long itemId, Long bookerId);

    @Query("select b from Booking b where itemId = ?1 and status = 'APPROVED' and end < CURRENT_TIMESTAMP() order by end desc")
    List<Booking> getLastBooking(Long itemId);

    @Query("select b from Booking b where itemId = ?1 and status = 'APPROVED' and start > CURRENT_TIMESTAMP() order by start asc")
    List<Booking> getNextBooking(Long itemId);
}
