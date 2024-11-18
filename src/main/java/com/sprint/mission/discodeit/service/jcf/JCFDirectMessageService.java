package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.model.DirectMessage;
import com.sprint.mission.discodeit.service.DirectMessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class JCFDirectMessageService implements DirectMessageService {
    private final Map<UUID, DirectMessage> data;
    private final UserService userService;

    public JCFDirectMessageService(UserService userService) {
        this.data = new HashMap<>();
        this.userService = userService;
    }

    @Override
    public DirectMessage createDirectMessage(String content, UUID senderId, UUID receiverId) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        if (userService.findUser(senderId).isEmpty()) {
            throw new NoSuchElementException("Sender with id " + senderId + " not found");
        }
        if (userService.findUser(receiverId).isEmpty()) {
            throw new NoSuchElementException("Receiver with id " + receiverId + " not found");
        }

        DirectMessage directMessage = new DirectMessage(id, content, senderId, receiverId, now, null);
        data.put(id, directMessage);
        return directMessage;
    }

    @Override
    public Optional<DirectMessage> findDirectMessage(UUID id) {
        DirectMessage directMessageNullable = data.get(id);
        return Optional.ofNullable(directMessageNullable);
    }

    @Override
    public DirectMessage updateDirectMessage(DirectMessage directMessage) {
        UUID id = directMessage.getId();
        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("DirectMessage with id " + id + " does not exist");
        }
        DirectMessage directMessageToUpdate = data.get(id);
        directMessageToUpdate.updateContent(directMessage.getContent(), LocalDateTime.now());
        data.put(id, directMessageToUpdate);

        return directMessageToUpdate;
    }

    @Override
    public void deleteDirectMessage(UUID id) {
        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("DirectMessage with id " + id + " does not exist");
        }

        data.remove(id);
    }
}
