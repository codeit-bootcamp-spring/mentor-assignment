package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.model.DirectMessage;
import com.sprint.mission.discodeit.service.DirectMessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.util.*;

public class FileDirectMessageService implements DirectMessageService {
    String filePath = "data/directMessage.dat";
    Map<UUID, DirectMessage> data;

    private final UserService userService;

    public FileDirectMessageService(UserService userService) {
        this.userService = userService;
        loadData();
    }

    private void loadData() {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                data = (Map<UUID, DirectMessage>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            data = new HashMap<>();
        }
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