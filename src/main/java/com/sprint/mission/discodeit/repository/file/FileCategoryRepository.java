package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.model.Category;
import com.sprint.mission.discodeit.repository.CategoryRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FileCategoryRepository implements CategoryRepository {
    String filePath = "data/category.dat";
    Map<UUID, Category> data;

    public FileCategoryRepository() {
        loadData();
    }

    private void loadData() {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                data = (Map<UUID, Category>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            data = new HashMap<>();
        }
    }

    @Override
    public Category save(Category category) {
        data.put(category.getId(), category);
        save(data);
        return category;
    }

    @Override
    public Optional<Category> findById(UUID id) {
        Category categoryNullable = data.get(id);
        return Optional.ofNullable(categoryNullable);
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

    private void save(Map<UUID, Category> data) {
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