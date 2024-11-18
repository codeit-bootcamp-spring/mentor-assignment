package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.model.DirectMessage;
import com.sprint.mission.discodeit.repository.DirectMessageRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class JCFDirectMessageRepository implements DirectMessageRepository {
    private final Map<UUID, DirectMessage> data;

    public JCFDirectMessageRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public DirectMessage save(DirectMessage directMessage) {
        data.put(directMessage.getId(), directMessage);
        return directMessage;
    }

    @Override
    public Optional<DirectMessage> findById(UUID id) {
        DirectMessage directMessageNullable = data.get(id);
        return Optional.ofNullable(directMessageNullable);
    }

    @Override
    public boolean existsById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        data.remove(id);
    }
}