package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    public User create(User entity) {
        entity.setId(getNextId());
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public User update(Long userId, UserDto user) {
        if (users.containsKey(userId)) {
            User oldUser = users.get(userId);
            oldUser.setEmail(user.getEmail());
            oldUser.setName(user.getName());
            return oldUser;
        } else {
            throw new NotFoundException("Could not find user with id " + userId);
        }
    }

    @Override
    public User getItem(Long id) {
        User user = null;
        if (users.containsKey(id)) {
            user = users.get(id);
        }
        return user;
    }

    @Override
    public Collection<User> getItems() {
        return users.values();
    }

    @Override
    public void removeUser(Long id) {
        users.remove(id);
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
