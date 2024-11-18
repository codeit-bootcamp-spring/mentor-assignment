package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.model.DirectMessage;

import java.util.Optional;
import java.util.UUID;

public interface DirectMessageService {
    DirectMessage createDirectMessage(String content, UUID senderId, UUID receiverId);
    Optional<DirectMessage> findDirectMessage(UUID id);
    DirectMessage updateDirectMessage(DirectMessage directMessage);
    void deleteDirectMessage(UUID id);
}
