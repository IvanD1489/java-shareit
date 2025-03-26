package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public Item create(ItemDto item, Long userId) {
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidationException("Name must not be empty");
        }
        if (item.getAvailable() == null) {
            throw new ValidationException("Availability must not be empty");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException("Description must not be empty");
        }
        if (userStorage.getItem(userId) == null) {
            throw new NotFoundException("Could not find user with id " + userId);
        }
        return itemStorage.create(item, userId);
    }

    public Item update(Long itemId, ItemDto item, Long userId) {
        if (userStorage.getItem(userId) == null) {
            throw new NotFoundException("Could not find user with id " + userId);
        }
        return itemStorage.update(itemId, item, userId);
    }

    public Collection<Item> getItems(Long userId) {
        return itemStorage.getItems(userId);
    }

    public Item getItem(Long itemId) {
        return itemStorage.getItem(itemId);
    }

    public void removeItem(Long itemId) {
        itemStorage.removeItem(itemId);
    }

    public Collection<Item> searchItems(String searchText) {
        if (searchText.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.searchItems(searchText);
    }

}
