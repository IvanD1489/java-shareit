package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public Item getItem(@PathVariable("itemId") Long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public Collection<Item> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItems(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@Valid @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public Item update(@PathVariable("itemId") Long itemId, @Valid @RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.update(itemId, item, userId);
    }

    @DeleteMapping("/{itemId}")
    public void removeItem(@PathVariable("itemId") Long itemId) {
        itemService.removeItem(itemId);
    }

    @GetMapping("/search")
    public Collection<Item> getItemBySearch(@RequestParam(value = "text") String searchText) {
        return itemService.searchItems(searchText);
    }
}
