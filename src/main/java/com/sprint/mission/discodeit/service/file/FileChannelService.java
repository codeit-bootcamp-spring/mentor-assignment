package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.model.Channel;
import com.sprint.mission.discodeit.service.CategoryService;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.util.*;

public class FileChannelService implements ChannelService {
    String filePath = "data/channel.dat";
    Map<UUID, Channel> data;

    private final CategoryService categoryService;
    private final UserService userService;

    public FileChannelService(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;

        loadData();
    }

    private void loadData() {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                data = (Map<UUID, Channel>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            data = new HashMap<>();
        }
    }

    @Override
    public Channel createChannel(String name, String description, UUID categoryId, UUID ownerId) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        if (categoryService.findCategory(categoryId).isEmpty()) {
            throw new NoSuchElementException("Category with id " + categoryId + " not found");
        }
        if (userService.findUser(ownerId).isEmpty()) {
            throw new NoSuchElementException("Owner with id " + ownerId + " not found");
        }

        Channel channel = new Channel(id, name, description, categoryId, ownerId, now);
        data.put(id, channel);
        return channel;
    }

    @Override
    public Optional<Channel> findChannel(UUID id) {
        Channel channelNullable = data.get(id);
        return Optional.ofNullable(channelNullable);
    }

    @Override
    public Channel updateChannel(Channel channel) {
        UUID id = channel.getId();
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("Channel with id " + id + " not found");
        }
        Channel channelToUpdate = data.get(id);
        channelToUpdate.updateDescription(channel.getDescription());
        data.put(id, channelToUpdate);

        return channelToUpdate;
    }

    @Override
    public void deleteChannel(UUID id) {
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("Channel with id " + id + " not found");
        }

        data.remove(id);
    }
}