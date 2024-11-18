package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.model.DirectMessage;
import com.sprint.mission.discodeit.repository.DirectMessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileDirectMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.DirectMessageService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class BasicDirectMessageService implements DirectMessageService {
    private final DirectMessageRepository directMessageRepository = new FileDirectMessageRepository();
    private final UserRepository userRepository = new FileUserRepository();

    @Override
    public DirectMessage createDirectMessage(String content, UUID senderId, UUID receiverId) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        if (!userRepository.existsById(senderId)) {
            throw new NoSuchElementException("Sender with id " + senderId + " not found");
        }
        if (!userRepository.existsById(receiverId)) {
            throw new NoSuchElementException("Receiver with id " + receiverId + " not found");
        }

        DirectMessage directMessage = new DirectMessage(id, content, senderId, receiverId, now, null);
        directMessageRepository.save(directMessage);
        return directMessage;
    }

    @Override
    public Optional<DirectMessage> findDirectMessage(UUID id) {
        return directMessageRepository.findById(id);
    }

    @Override
    public DirectMessage updateDirectMessage(DirectMessage directMessage) {
        UUID id = directMessage.getId();
        if (!directMessageRepository.existsById(id)) {
            throw new IllegalArgumentException("DirectMessage with id " + id + " does not exist");
        }
        DirectMessage directMessageToUpdate = directMessageRepository.findById(id).get();
        directMessageToUpdate.updateContent(directMessage.getContent(), LocalDateTime.now());
        directMessageRepository.save(directMessageToUpdate);

        return directMessageToUpdate;
    }

    @Override
    public void deleteDirectMessage(UUID id) {
        if (!directMessageRepository.existsById(id)) {
            throw new IllegalArgumentException("DirectMessage with id " + id + " does not exist");
        }

        directMessageRepository.deleteById(id);
    }
}