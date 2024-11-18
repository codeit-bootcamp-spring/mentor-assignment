package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.model.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    Category createCategory(String name);
    Optional<Category> findCategory(UUID id);
    Category updateCategory(Category category);
    void deleteCategory(UUID id);
}
