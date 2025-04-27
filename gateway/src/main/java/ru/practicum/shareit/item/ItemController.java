package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comments.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemClient itemClient;

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable("itemId") Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getItems(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@PathVariable("itemId") Long itemId, @Valid @RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.update(itemId, itemDto, userId);
    }

    @DeleteMapping("/{itemId}")
    public void removeItem(@PathVariable("itemId") Long itemId) {
        itemClient.removeItem(itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemBySearch(@RequestParam(value = "text") String searchText, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getItemBySearch(searchText, userId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> setItemComment(@PathVariable("itemId") Long itemId, @Valid @RequestBody CommentDto commentDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.setItemComment(itemId, commentDto, userId);
    }
}
