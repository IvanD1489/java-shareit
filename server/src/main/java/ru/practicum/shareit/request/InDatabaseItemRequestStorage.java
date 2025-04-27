package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@AllArgsConstructor
public class InDatabaseItemRequestStorage implements ItemRequestStorage {

    private final ItemRequestRepository repository;
    private final ItemStorage itemStorage;

    public ItemRequestDto addRequest(ItemRequestDto itemRequestDto, Long userId) {
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setRequestor(userId);
        ItemRequest retVal = repository.save(itemRequest);
        return ItemRequestMapper.toItemRequestDto(retVal, getRequestItems(retVal.getId()));
    }

    public Collection<ItemRequestDto> getUserRequests(Long userId) {
        return repository.getRequestsByUser(userId)
                .stream()
                .map(item -> ItemRequestMapper.toItemRequestDto(item, getRequestItems(item.getId())))
                .collect(Collectors.toList());
    }

    public Collection<ItemRequestDto> getAllRequests() {
        return repository.findAll()
                .stream()
                .map(item -> ItemRequestMapper.toItemRequestDto(item, getRequestItems(item.getId())))
                .collect(Collectors.toList());
    }

    public ItemRequestDto getRequest(Long itemRequestId) {
        Optional<ItemRequest> optItem = repository.findById(itemRequestId);
        return optItem
                .map(item -> ItemRequestMapper.toItemRequestDto(item, getRequestItems(item.getId())))
                .orElse(null);
    }

    public Collection<ItemDto> getRequestItems(Long requestId) {
        return repository.getItemsByRequestId(requestId)
                .stream()
                .map(item -> ItemMapper.toItemDto(item, itemStorage.getLastBooking(item.getId(), item.getOwner(), item.getOwner()),
                        itemStorage.getNextBooking(item.getId(), item.getOwner(), item.getOwner()),
                        itemStorage.getItemComments(item.getId()))).collect(Collectors.toList());
    }
}
