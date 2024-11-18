package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.model.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.util.*;

public class FileUserService implements UserService {
    String filePath = "data/users.dat";
    Map<UUID, User> data;

    public FileUserService() {
        this.data = new HashMap<>();
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
    public User createUser(String username, String email, String password, String avatarUrl) {
        UUID id = UUID.randomUUID();
        User user = new User(id, username, email, password, avatarUrl, LocalDateTime.now());
        data.put(id, user);
        return user;
    }

    @Override
    public Optional<User> findUser(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public User updateUser(User user) {
        if (!data.containsKey(user.getId())) {
            throw new NoSuchElementException("User not found");
        }
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(UUID id) {
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("User not found");
        }
        data.remove(id);
    }
}