package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    // 1. Updated method name signature to match what you had
    public List<Category> getAllCategories()
    {
        // delegate work to the repository layer
        return categoryRepository.findAll();
    }

    public Category getById(int categoryId)
    {
        // find category or return null if not present
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public Category create(Category category)
    {
        return categoryRepository.save(category);
    }

    public Category update(int categoryId, Category category)
    {
        // Ensure the ID matches before saving changes
        category.setCategoryId(categoryId);
        return categoryRepository.save(category);
    }

    public void delete(int categoryId)
    {
        categoryRepository.deleteById(categoryId);
    }
}