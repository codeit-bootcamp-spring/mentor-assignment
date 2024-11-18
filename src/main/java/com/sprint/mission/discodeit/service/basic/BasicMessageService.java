package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.model.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository = new FileMessageRepository();
    private final UserRepository userRepository = new FileUserRepository();
    private final ChannelRepository channelRepository = new FileChannelRepository();

    @Override
    public Message createMessage(String content, UUID authorId, UUID channelId) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        if (!userRepository.existsById(authorId)) {
            throw new NoSuchElementException("Author with id " + authorId + " not found");
        }
        if (!channelRepository.existsById(channelId)) {
            throw new NoSuchElementException("Channel with id " + channelId + " not found");
        }

        Message message = new Message(id, content, authorId, channelId, now, now);
        messageRepository.save(message);
        return message;
    }

    @Override
    public Optional<Message> findMessage(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message updateMessage(Message message) {
        UUID id = message.getId();
        if(!messageRepository.existsById(id)) {
            throw new NoSuchElementException("Message with id " + id + " not found");
        }
        Message messageToUpdate = messageRepository.findById(id).get();
        messageToUpdate.updateContent(message.getContent(), LocalDateTime.now());
        messageRepository.save(messageToUpdate);

        return messageToUpdate;
    }

    @Override
    public void deleteMessage(UUID id) {
        if(!messageRepository.existsById(id)) {
            throw new NoSuchElementException("Message with id " + id + " not found");
        }

        messageRepository.deleteById(id);
    }
}