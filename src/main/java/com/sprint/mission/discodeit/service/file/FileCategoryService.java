package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.model.Category;
import com.sprint.mission.discodeit.service.CategoryService;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.util.*;

public class FileCategoryService implements CategoryService {
    String filePath = "data/category.dat";
    Map<UUID, Category> data;

    public FileCategoryService() {
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
    public Category createCategory(String name) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Category category = new Category(id, name, now);
        data.put(id, category);
        return category;
    }

    @Override
    public Optional<Category> findCategory(UUID id) {
        Category categoryNullable = data.get(id);
        return Optional.ofNullable(categoryNullable);
    }

    @Override
    public Category updateCategory(Category category) {
        UUID id = UUID.randomUUID();
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("Category with id " + id + " not found");
        }
        Category categoryToUpdate = data.get(id);
        categoryToUpdate.updateName(category.getName());
        data.put(id, categoryToUpdate);

        return categoryToUpdate;
    }

    @Override
    public void deleteCategory(UUID id) {
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("Category with id " + id + " not found");
        }

        data.remove(id);
    }
}