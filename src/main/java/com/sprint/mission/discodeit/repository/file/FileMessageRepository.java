package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.model.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileMessageRepository implements MessageRepository {
    String filePath = "data/message.dat";
    Map<UUID, Message> data;

    public FileMessageRepository() {
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
    public Message save(Message message) {
        data.put(message.getId(), message);
        save(data);
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        Message messageNullable = data.get(id);
        return Optional.ofNullable(messageNullable);
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

    private void save(Map<UUID, Message> data) {
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