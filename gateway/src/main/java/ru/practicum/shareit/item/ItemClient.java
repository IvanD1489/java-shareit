package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.comments.dto.CommentDto;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getItem(Long itemId, Long userId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> getItems(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> create(ItemDto itemDto, Long userId) {
        validateItem(itemDto, userId);
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> update(Long itemId, ItemDto itemDto, Long userId) {
        return patch("/" + itemId, userId, itemDto);
    }

    public void removeItem(Long itemId) {
        delete("/" + itemId);
    }

    public ResponseEntity<Object> getItemBySearch(String searchText, Long userId) {
        Map<String, Object> parameters = Map.of(
                "text", searchText
        );
        return get("/search?text={text}", userId, parameters);
    }

    public ResponseEntity<Object> setItemComment(Long itemId, CommentDto commentDto, Long userId) {
        return post("/" + itemId + "/comment", userId, commentDto);
    }

    private void validateItem(ItemDto itemDto, Long userId) {
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new ValidationException("Name must not be empty");
        }
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("Availability must not be empty");
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new ValidationException("Description must not be empty");
        }
    }

}
