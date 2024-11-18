package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}