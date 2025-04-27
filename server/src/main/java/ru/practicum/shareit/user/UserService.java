package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserStorage userStorage;

    public UserDto create(UserDto userDto) {
        if (userStorage.getItems().stream().anyMatch(u -> Objects.equals(u.getEmail(), userDto.getEmail()))) {
            throw new ValidationException("Email already exists");
        }
        return userStorage.create(userDto);
    }

    public UserDto update(Long userId, UserDto user) {
        if (userStorage.getItem(userId) == null) {
            throw new NotFoundException("Could not find user with id " + userId);
        }
        if (userStorage.getItems().stream().anyMatch(u -> Objects.equals(u.getEmail(), user.getEmail()))) {
            throw new ValidationException("Email already exists");
        }
        return userStorage.update(userId, user);
    }

    public Collection<UserDto> getItems() {
        return userStorage.getItems();
    }

    public UserDto getItem(Long userId) {
        return userStorage.getItem(userId);
    }

    public void removeUser(Long userId) {
        if (userStorage.getItem(userId) == null) {
            throw new NotFoundException("Could not find user with id " + userId);
        }
        userStorage.removeUser(userId);
    }

}
