package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.model.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.time.LocalDateTime;
import java.util.*;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data;
    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageService(UserService userService, ChannelService channelService) {
        this.data = new HashMap<>();
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message createMessage(String content, UUID authorId, UUID channelId) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        if (userService.findUser(authorId).isEmpty()) {
            throw new NoSuchElementException("Author with id " + authorId + " not found");
        }
        if (channelService.findChannel(channelId).isEmpty()) {
            throw new NoSuchElementException("Channel with id " + channelId + " not found");
        }

        Message message = new Message(id, content, authorId, channelId, now, now);
        data.put(id, message);
        return message;
    }

    @Override
    public Optional<Message> findMessage(UUID id) {
        Message messageNullable = data.get(id);
        return Optional.ofNullable(messageNullable);
    }

    @Override
    public Message updateMessage(Message message) {
        UUID id = message.getId();
        if(!data.containsKey(id)) {
            throw new NoSuchElementException("Message with id " + id + " not found");
        }
        Message messageToUpdate = data.get(id);
        messageToUpdate.updateContent(message.getContent(), LocalDateTime.now());
        data.put(id, messageToUpdate);

        return messageToUpdate;
    }

    @Override
    public void deleteMessage(UUID id) {
        if(!data.containsKey(id)) {
            throw new NoSuchElementException("Message with id " + id + " not found");
        }

        data.remove(id);
    }
}
