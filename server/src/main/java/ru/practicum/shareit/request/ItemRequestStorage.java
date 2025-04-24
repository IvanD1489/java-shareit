package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;

public interface ItemRequestStorage {

    ItemRequestDto addRequest(ItemRequestDto itemRequestDto, Long userId);

    Collection<ItemRequestDto> getUserRequests(Long userId);

    Collection<ItemRequestDto> getAllRequests();

    ItemRequestDto getRequest(Long itemRequestId);

}
