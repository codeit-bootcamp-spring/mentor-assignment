package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(String username, String email, String password, String avatarUrl);
    Optional<User> findUser(UUID id);
    User updateUser(User user);
    void deleteUser(UUID id);
}
