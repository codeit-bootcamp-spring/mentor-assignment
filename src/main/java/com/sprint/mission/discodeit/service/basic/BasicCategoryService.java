package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.model.Category;
import com.sprint.mission.discodeit.repository.CategoryRepository;
import com.sprint.mission.discodeit.repository.file.FileCategoryRepository;
import com.sprint.mission.discodeit.service.CategoryService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class BasicCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository = new FileCategoryRepository();

    @Override
    public Category createCategory(String name) {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Category category = new Category(id, name, now);
        categoryRepository.save(category);
        return category;
    }

    @Override
    public Optional<Category> findCategory(UUID id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category updateCategory(Category category) {
        UUID id = category.getId();
        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("Category with id " + id + " not found");
        }
        Category categoryToUpdate = categoryRepository.findById(id).get();
        categoryToUpdate.updateName(category.getName());
        categoryRepository.save(categoryToUpdate);
        return categoryToUpdate;
    }

    @Override
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("Category with id " + id + " not found");
        }

        categoryRepository.deleteById(id);
    }
}