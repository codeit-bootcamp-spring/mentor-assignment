package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.model.Category;
import com.sprint.mission.discodeit.service.CategoryService;

import java.time.LocalDateTime;
import java.util.*;

public class JCFCategoryService implements CategoryService {
    private final Map<UUID, Category> data;

    public JCFCategoryService() {
        this.data = new HashMap<>();
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
        UUID id = category.getId();
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
