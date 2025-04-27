package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    @Query("select r from ItemRequest r where r.requestor = ?1")
    List<ItemRequest> getRequestsByUser(Long userId);

    @Query("select i from Item i where requestId = ?1")
    List<Item> getItemsByRequestId(Long itemRequestId);
}
