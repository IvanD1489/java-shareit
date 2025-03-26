package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemStorage {

    Item create(ItemDto entity, Long userId);

    Item update(Long itemId, ItemDto item, Long userId);

    Item getItem(Long id);

    Collection<Item> getItems(Long userId);

    void removeItem(Long id);

    Collection<Item> searchItems(String searchText);

}
