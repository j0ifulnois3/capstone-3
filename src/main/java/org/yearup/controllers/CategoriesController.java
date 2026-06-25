package org.yearup.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController {
    private CategoryService categoryService;
    private ProductService productService;


    @Autowired // Directs Spring Boot to inject your business logic services automatically
    public CategoriesController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    // 1. Get all categories
    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAllCategories();
    }

    // 2. Get category by ID
    @GetMapping("{id}")
    public Category getById(@PathVariable int id) {
        Category category = categoryService.getById(id);
        if (category == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND, "Category not found."
            );
        }
        return category;
    }

    // 3. Get all products under a specific category
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        return productService.listByCategoryId(categoryId);
    }

    // 4. Add Category (Admin Only)
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.create(category);
            return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, "Oops, something went wrong."
            );
        }
    }

    // 5. Update Category (Admin Only)
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category) {
        categoryService.update(id, category);
        return category;
    }

    // 6. Delete Category (Admin Only)
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        try {
            categoryService.delete(id);
            return org.springframework.http.ResponseEntity.noContent().build(); // Sends 204 No Content
        } catch (Exception e) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete category."
            );
        }
    }
}