package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserStorage userStorage;

    public User create(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("User email cannot be empty");
        }
        if (userStorage.getItems().stream().anyMatch(u -> Objects.equals(u.getEmail(), user.getEmail()))) {
            throw new ValidationException("Email already exists");
        }
        return userStorage.create(user);
    }

    public User update(Long userId, UserDto user) {
        if (userStorage.getItems().stream().anyMatch(u -> Objects.equals(u.getEmail(), user.getEmail()))) {
            throw new ValidationException("Email already exists");
        }
        return userStorage.update(userId, user);
    }

    public Collection<User> getItems() {
        return userStorage.getItems();
    }

    public User getItem(Long userId) {
        return userStorage.getItem(userId);
    }

    public void removeUser(Long userId) {
        userStorage.removeUser(userId);
    }

}
