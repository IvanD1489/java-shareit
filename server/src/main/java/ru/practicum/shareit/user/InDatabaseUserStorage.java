package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InDatabaseUserStorage implements UserStorage {

    private final UserRepository repository;

    public InDatabaseUserStorage(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        return UserMapper.toUserDto(repository.save(user));
    }

    @Override
    public UserDto update(Long userId, UserDto userDto) {
        User retVal = UserMapper.toUser(userDto);
        User user = repository.getById(userId);
        retVal.setId(user.getId());
        if (userDto.getEmail() == null) {
            retVal.setEmail(user.getEmail());
        }
        if (userDto.getName() == null) {
            retVal.setName(user.getName());
        }
        return UserMapper.toUserDto(repository.save(retVal));
    }

    @Override
    public UserDto getItem(Long id) {
        Optional<User> optUser = repository.findById(id);
        return optUser.map(UserMapper::toUserDto).orElse(null);
    }

    @Override
    public Collection<UserDto> getItems() {
        return repository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public void removeUser(Long id) {
        repository.delete(repository.getById(id));
    }

}
