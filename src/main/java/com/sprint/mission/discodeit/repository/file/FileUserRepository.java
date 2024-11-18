package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.model.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileUserRepository implements UserRepository {
    String filePath = "data/users.dat";
    Map<UUID, User> data;

    public FileUserRepository() {
        loadData();
    }


    private void loadData() {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                data = (Map<UUID, User>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            data = new HashMap<>();
        }
    }

    @Override
    public User save(User user) {
        data.put(user.getId(), user);
        save(data);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        User userNullable = data.get(id);
        return Optional.ofNullable(userNullable);
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

    private void save(Map<UUID, User> data) {
        try(
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}