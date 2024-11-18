package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.model.Category;
import com.sprint.mission.discodeit.repository.CategoryRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class JCFCategoryRepository implements CategoryRepository {
    private final Map<UUID, Category> data;

    public JCFCategoryRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public Category save(Category category) {
        data.put(category.getId(), category);
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
    }
}