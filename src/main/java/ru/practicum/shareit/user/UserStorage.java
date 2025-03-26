package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserStorage {
    User create(User entity);

    User update(Long userId, UserDto user);

    User getItem(Long id);

    Collection<User> getItems();

    void removeUser(Long id);
}
