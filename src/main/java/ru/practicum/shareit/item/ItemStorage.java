package ru.practicum.shareit.item;

import ru.practicum.shareit.comments.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ItemStorage {

    ItemDto create(ItemDto entity, Long userId);

    ItemDto update(Long itemId, ItemDto item, Long userId);

    ItemDto getItem(Long id, Long userId);

    Collection<ItemDto> getItems(Long userId);

    void removeItem(Long id);

    Collection<ItemDto> searchItems(String searchText, Long userId);

    CommentDto setItemComment(Long itemId, CommentDto commentDto, Long userId);

    Collection<CommentDto> getItemComments(Long itemId);

    LocalDateTime getLastBooking(Long itemId, Long ownerId, Long userId);

    LocalDateTime getNextBooking(Long itemId, Long ownerId, Long userId);

}
