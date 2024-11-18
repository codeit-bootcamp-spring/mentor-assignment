package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.model.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {
    String filePath = "data/channel.dat";
    Map<UUID, Channel> data;

    public FileChannelRepository() {
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
    public Channel save(Channel channel) {
        data.put(channel.getId(), channel);
        save(data);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        Channel channelNullable = data.get(id);
        return Optional.ofNullable(channelNullable);
    }

    @Override
    public boolean existsById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        data.remove(id);
        save(data);
    }

    private void save(Map<UUID, Channel> data) {
        try(
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}