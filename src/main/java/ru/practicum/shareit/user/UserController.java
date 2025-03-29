package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public User getItem(@PathVariable("userId") Long userId) {
        return userService.getItem(userId);
    }

    @GetMapping
    public Collection<User> getItems() {
        return userService.getItems();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PatchMapping("/{userId}")
    public User update(@PathVariable("userId") Long userId, @Valid @RequestBody UserDto user) {
        return userService.update(userId, user);
    }

    @DeleteMapping("/{userId}")
    public void removeUser(@PathVariable("userId") Long userId) {
        userService.removeUser(userId);
    }

}
