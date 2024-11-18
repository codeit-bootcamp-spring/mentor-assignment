package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.model.Message;

import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {
    Message save(Message message);
    Optional<Message> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}