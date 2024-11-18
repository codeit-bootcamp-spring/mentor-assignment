package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.model.Channel;
import com.sprint.mission.discodeit.repository.CategoryRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileCategoryRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository = new FileChannelRepository();
    private final CategoryRepository categoryRepository = new FileCategoryRepository();
    private final UserRepository userRepository = new FileUserRepository();

    @Override
    public Channel createChannel(String name, String description, UUID categoryId, UUID ownerId) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        if (!categoryRepository.existsById(categoryId)) {
            throw new NoSuchElementException("Category with id " + categoryId + " not found");
        }
        if (!userRepository.existsById(ownerId)) {
            throw new NoSuchElementException("Owner with id " + ownerId + " not found");
        }

        Channel channel = new Channel(id, name, description, categoryId, ownerId, now);
        channelRepository.save(channel);
        return channel;
    }

    @Override
    public Optional<Channel> findChannel(UUID id) {
        return channelRepository.findById(id);
    }

    @Override
    public Channel updateChannel(Channel channel) {
        UUID id = channel.getId();
        if (!channelRepository.existsById(id)) {
            throw new NoSuchElementException("Channel with id " + id + " not found");
        }
        Channel channelToUpdate = channelRepository.findById(id).get();
        channelToUpdate.updateDescription(channel.getDescription());
        channelRepository.save(channelToUpdate);

        return channelToUpdate;
    }

    @Override
    public void deleteChannel(UUID id) {
        if (!channelRepository.existsById(id)) {
            throw new NoSuchElementException("Channel with id " + id + " not found");
        }

        channelRepository.deleteById(id);
    }
}