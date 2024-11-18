package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.model.User;
import com.sprint.mission.discodeit.service.UserService;

import java.time.LocalDateTime;
import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;

    public JCFUserService() {
        this.data = new HashMap<>();
    }

    @Override
    public User createUser(String username, String email, String password, String avatarUrl) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        User user = new User(id, username, email, password, avatarUrl, now);
        data.put(id, user);
        return user;
    }

    @Override
    public Optional<User> findUser(UUID id) {
        User userNullable = data.get(id);
        return Optional.ofNullable(userNullable);
    }

    @Override
    public User updateUser(User user) {
        UUID id = user.getId();
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("User with id " + id + " not found");
        }
        User userToUpdate = data.get(id);
        userToUpdate.updateEmail(user.getEmail());
        data.put(id, userToUpdate);

        return userToUpdate;
    }

    @Override
    public void deleteUser(UUID id) {
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("User with id " + id + " not found");
        }

        data.remove(id);
    }
}
