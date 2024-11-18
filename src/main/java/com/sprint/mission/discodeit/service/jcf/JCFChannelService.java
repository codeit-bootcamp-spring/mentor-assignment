package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.model.Channel;
import com.sprint.mission.discodeit.service.CategoryService;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.UserService;

import java.time.LocalDateTime;
import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;
    private final CategoryService categoryService;
    private final UserService userService;

    public JCFChannelService(CategoryService categoryService, UserService userService) {
        this.data = new HashMap<>();
        this.categoryService = categoryService;
        this.userService = userService;
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
