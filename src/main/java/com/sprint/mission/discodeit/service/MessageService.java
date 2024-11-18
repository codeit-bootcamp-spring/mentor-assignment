package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.model.Message;

import java.util.Optional;
import java.util.UUID;

public interface MessageService {
    Message createMessage(String content, UUID authorId, UUID channelId);
    Optional<Message> findMessage(UUID id);
    Message updateMessage(Message message);
    void deleteMessage(UUID id);
}
