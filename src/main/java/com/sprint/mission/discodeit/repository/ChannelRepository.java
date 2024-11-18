package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.model.Channel;

import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {
    Channel save(Channel channel);
    Optional<Channel> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}