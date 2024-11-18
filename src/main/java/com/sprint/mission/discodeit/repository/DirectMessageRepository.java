package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.model.DirectMessage;

import java.util.Optional;
import java.util.UUID;

public interface DirectMessageRepository {
    DirectMessage save(DirectMessage directMessage);
    Optional<DirectMessage> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}