package com.andrew.product_service.service;

import com.andrew.product_service.dto.CategoryRequest;
import com.andrew.product_service.entity.Category;
import com.andrew.product_service.exception.CategoryNotFoundException;
import com.andrew.product_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Category getCategoryById(Long categoryId){
        return repository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryId));
    }

    public Category createCategory(CategoryRequest request) {
        if(repository.existsByName(request.name())){
            throw new IllegalArgumentException("Category name already exists!");
        }
        Category category = new Category();
        category.setName(request.name());
        return repository.save(category);
    }

    public Category updateCategoryById(Long categoryId, CategoryRequest request) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("No category found with id: " + categoryId));

        if(repository.existsByName(request.name())){
            throw new IllegalArgumentException("Category name already exists!");
        }

        category.setName(request.name());
        return repository.save(category);
    }

    public void deleteCategoryById(Long categoryId) {
        if(!repository.existsById(categoryId)){
            throw new CategoryNotFoundException("No category found with id: " + categoryId);
        }
        repository.deleteById(categoryId);
    }
}
