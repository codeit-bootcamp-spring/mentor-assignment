package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.model.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.util.*;

public class FileMessageService implements MessageService {
    String filePath = "data/message.dat";
    Map<UUID, Message> data;

    private final UserService userService;
    private final ChannelService channelService;

    public FileMessageService(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
        loadData();
    }

    private void loadData() {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                data = (Map<UUID, Message>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            data = new HashMap<>();
        }
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
        messageToUpdate.updateContent(messageToUpdate.getContent(), LocalDateTime.now());
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