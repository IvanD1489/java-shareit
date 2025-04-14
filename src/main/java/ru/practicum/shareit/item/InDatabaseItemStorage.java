package ru.practicum.shareit.item;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.comments.Comment;
import ru.practicum.shareit.comments.CommentMapper;
import ru.practicum.shareit.comments.CommentRepository;
import ru.practicum.shareit.comments.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserStorage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class InDatabaseItemStorage implements ItemStorage {

    private final ItemRepository repository;
    private final CommentRepository commentRepository;
    private final UserStorage userStorage;
    private final BookingRepository bookingRepository;

    @Override
    public ItemDto create(ItemDto entity, Long userId) {
        Item item = ItemMapper.toItem(entity);
        item.setOwner(userId);
        return ItemMapper.toItemDto(repository.save(item), this, userId);
    }

    @Override
    public ItemDto update(Long itemId, ItemDto entity, Long userId) {
        Item item = repository.getById(itemId);
        entity.setId(item.getId());
        if (entity.getAvailable() == null) {
            entity.setAvailable(item.isAvailable());
        }
        if (entity.getDescription() == null) {
            entity.setDescription(item.getDescription());
        }
        if (entity.getName() == null) {
            entity.setName(item.getName());
        }
        return ItemMapper.toItemDto(repository.save(ItemMapper.toItem(entity)), this, userId);
    }

    @Override
    public ItemDto getItem(Long id, Long userId) {
        Optional<Item> optItem = repository.findById(id);
        return optItem.map(item -> ItemMapper.toItemDto(item, this, userId)).orElse(null);
    }

    @Override
    public Collection<ItemDto> getItems(Long userId) {
        return repository.findAll()
                .stream()
                .filter(item -> Objects.equals(item.getOwner(), userId))
                .map(item -> ItemMapper.toItemDto(item, this, userId))
                .collect(Collectors.toList());
    }

    @Override
    public void removeItem(Long id) {
        repository.delete(repository.getById(id));
    }

    @Override
    public Collection<ItemDto> searchItems(String searchText, Long userId) {
        return repository.findAll()
                .stream()
                .filter(item -> (item.getName().toLowerCase() + item.getDescription().toLowerCase()).contains(searchText.toLowerCase()) && item.isAvailable())
                .map(item -> ItemMapper.toItemDto(item, this, userId))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto setItemComment(Long itemId, CommentDto commentDto, Long userId) {
        Comment comment = CommentMapper.toComment(commentDto);
        comment.setItemId(itemId);
        comment.setAuthorId(userId);
        return CommentMapper.toCommentDto(commentRepository.save(comment), userStorage);
    }

    @Override
    public Collection<CommentDto> getItemComments(Long itemId) {
        return commentRepository.findByItemId(itemId).stream().map(comment -> CommentMapper.toCommentDto(comment, userStorage)).collect(Collectors.toList());
    }

    @Override
    public LocalDateTime getLastBooking(Long itemId, Long ownerId, Long userId) {
        if (!Objects.equals(ownerId, userId)) {
            return null;
        }
        List<Booking> bookings = bookingRepository.getLastBooking(itemId);
        if (bookings.isEmpty()) {
            return null;
        }
        return bookings.getFirst().getStart();
    }

    @Override
    public LocalDateTime getNextBooking(Long itemId, Long ownerId, Long userId) {
        if (!Objects.equals(ownerId, userId)) {
            return null;
        }
        List<Booking> bookings = bookingRepository.getNextBooking(itemId);
        if (bookings.isEmpty()) {
            return null;
        }
        return bookings.getFirst().getStart();
    }

}
