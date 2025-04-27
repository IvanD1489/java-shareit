package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class ItemRequestService {

    private final ItemRequestStorage itemRequestStorage;

    public ItemRequestDto addRequest(ItemRequestDto itemRequestDto, Long userId) {
        return itemRequestStorage.addRequest(itemRequestDto, userId);
    }

    public Collection<ItemRequestDto> getUserRequests(Long userId) {
        return itemRequestStorage.getUserRequests(userId);
    }

    public Collection<ItemRequestDto> getAllRequests() {
        return itemRequestStorage.getAllRequests();
    }

    public ItemRequestDto getRequest(Long itemRequestId) {
        return itemRequestStorage.getRequest(itemRequestId);
    }

}
