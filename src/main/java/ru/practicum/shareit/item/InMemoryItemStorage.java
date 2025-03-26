package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class InMemoryItemStorage implements ItemStorage {

    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item create(ItemDto entity, Long userId) {
        Item item = ItemMapper.toItem(entity);
        item.setId(getNextId());
        item.setOwner(userId);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Long itemId, ItemDto entity, Long userId) {
        Item oldItem = items.get(itemId);
        if (items.containsKey(itemId) && Objects.equals(oldItem.getOwner(), userId)) {
            if (entity.getName() != null && !entity.getName().isBlank()) {
                oldItem.setName(entity.getName());
            }
            if (entity.getDescription() != null && !entity.getDescription().isBlank()) {
                oldItem.setDescription(entity.getDescription());
            }
            if (entity.getAvailable() != null) {
                oldItem.setAvailable(entity.getAvailable());
            }
            return oldItem;
        } else {
            throw new NotFoundException("Could not find item with id " + itemId);
        }
    }

    @Override
    public Item getItem(Long id) {
        if (items.containsKey(id)) {
            return items.get(id);
        } else {
            throw new NotFoundException("Could not find item with id " + id);
        }
    }

    @Override
    public Collection<Item> getItems(Long userId) {
        return items.values().stream().filter(item -> Objects.equals(item.getOwner(), userId)).collect(Collectors.toList());
    }

    @Override
    public void removeItem(Long id) {
        items.remove(id);
    }

    @Override
    public Collection<Item> searchItems(String searchText) {
        return items.values().stream().filter(item -> (item.getName().toLowerCase() + item.getDescription().toLowerCase()).contains(searchText.toLowerCase()) && item.isAvailable()).collect(Collectors.toList());
    }

    private long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
