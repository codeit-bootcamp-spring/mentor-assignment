package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.model.Channel;

import java.util.Optional;
import java.util.UUID;

public interface ChannelService {
    Channel createChannel(String name, String description, UUID categoryId, UUID ownerId);
    Optional<Channel> findChannel(UUID id);
    Channel updateChannel(Channel channel);
    void deleteChannel(UUID id);
}
