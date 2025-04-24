package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingStorage;
import ru.practicum.shareit.comments.dto.CommentDto;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final BookingStorage bookingStorage;

    public ItemDto create(ItemDto itemDto, Long userId) {
        validateItem(itemDto, userId);
        return itemStorage.create(itemDto, userId);
    }

    public ItemDto update(Long itemId, ItemDto item, Long userId) {
        if (userStorage.getItem(userId) == null) {
            throw new NotFoundException("Could not find user with id " + userId);
        }
        return itemStorage.update(itemId, item, userId);
    }

    public Collection<ItemDto> getItems(Long userId) {
        return itemStorage.getItems(userId);
    }

    public ItemDto getItem(Long itemId, Long userId) {
        return itemStorage.getItem(itemId, userId);
    }

    public void removeItem(Long itemId) {
        itemStorage.removeItem(itemId);
    }

    public Collection<ItemDto> searchItems(String searchText, Long userId) {
        if (searchText.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.searchItems(searchText, userId);
    }

    public CommentDto setItemComment(Long itemId, CommentDto commentDto, Long userId) {
        if (userStorage.getItem(userId) == null) {
            throw new NotFoundException("Could not find user with id " + userId);
        }
        if (itemStorage.getItem(itemId, userId) == null) {
            throw new NotFoundException("Could not find item with id " + userId);
        }
        if (!bookingStorage.isAvailableForComment(itemId, userId)) {
            throw new ValidationException("Item is not available");
        }
        return itemStorage.setItemComment(itemId, commentDto, userId);
    }

    public void validateItem(ItemDto itemDto, Long userId) {
        if (userStorage.getItem(userId) == null) {
            throw new NotFoundException("Could not find user with id " + userId);
        }
    }

}
