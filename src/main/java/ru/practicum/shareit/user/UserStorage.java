package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserStorage {
    UserDto create(UserDto userDto);

    UserDto update(Long userId, UserDto user);

    UserDto getItem(Long id);

    Collection<UserDto> getItems();

    void removeUser(Long id);
}
