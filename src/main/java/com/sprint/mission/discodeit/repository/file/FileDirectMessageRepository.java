package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.model.DirectMessage;
import com.sprint.mission.discodeit.repository.DirectMessageRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileDirectMessageRepository implements DirectMessageRepository {
    String filePath = "data/directMessage.dat";
    Map<UUID, DirectMessage> data;

    public FileDirectMessageRepository() {
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
    public DirectMessage save(DirectMessage directMessage) {
        data.put(directMessage.getId(), directMessage);
        save(data);
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
        save(data);
    }

    private void save(Map<UUID, DirectMessage> data) {
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