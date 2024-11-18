package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.model.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(UUID id);
    boolean existsById(UUID id);
    void deleteById(UUID id);
}