package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable("itemId") Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItem(itemId, userId);
    }

    @GetMapping
    public Collection<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItems(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@PathVariable("itemId") Long itemId, @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.update(itemId, itemDto, userId);
    }

    @DeleteMapping("/{itemId}")
    public void removeItem(@PathVariable("itemId") Long itemId) {
        itemService.removeItem(itemId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> getItemBySearch(@RequestParam(value = "text") String searchText, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.searchItems(searchText, userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto setItemComment(@PathVariable("itemId") Long itemId, @RequestBody CommentDto commentDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.setItemComment(itemId, commentDto, userId);
    }
}
