package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.model.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class BasicUserService implements UserService {
    private final UserRepository userRepository = new FileUserRepository();

    @Override
    public User createUser(String username, String email, String password, String avatarUrl) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        User user = new User(id, username, email, password, avatarUrl, now);
        userRepository.save(user);
        return user;
    }

        @Override
    public Optional<User> findUser(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        UUID id = user.getId();
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User with id " + id + " not found");
        }
        User userToUpdate = userRepository.findById(id).get();
        userToUpdate.updateEmail(user.getEmail());
        userRepository.save(userToUpdate);

        return userToUpdate;
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User with id " + id + " not found");
        }

        userRepository.deleteById(id);
    }
}