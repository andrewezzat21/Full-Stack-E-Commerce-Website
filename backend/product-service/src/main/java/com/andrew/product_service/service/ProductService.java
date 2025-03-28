package com.andrew.product_service.service;

import com.andrew.product_service.dto.ProductRequest;
import com.andrew.product_service.entity.Category;
import com.andrew.product_service.entity.Product;
import com.andrew.product_service.entity.ProductCategory;
import com.andrew.product_service.entity.ProductCategoryId;
import com.andrew.product_service.exception.CategoryNotFoundException;
import com.andrew.product_service.exception.ProductNotFoundException;
import com.andrew.product_service.repository.CategoryRepository;
import com.andrew.product_service.repository.ProductCategoryRepository;
import com.andrew.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final ProductRepository repository;
    public final CategoryRepository categoryRepository;
    public final ProductCategoryRepository productCategoryRepository;


    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    }

    @Transactional
    public Product createProduct(ProductRequest request) {
        if(repository.existsByName(request.name())){
            throw new IllegalArgumentException("Product already exists!");
        }
        Set<Category> categories = categoryRepository.findByCategoryIdIn(request.categoryIds());

        if (categories.size() != request.categoryIds().size()) {
            throw new IllegalArgumentException("Some category IDs are invalid!");
        }

        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setImage(request.image());
        product.setAvailableQuantity(request.availableQuantity());
        product.setCreatedAt(LocalDateTime.now());

        repository.save(product);

        for(Category category : categories){
            ProductCategoryId productCategoryId = new ProductCategoryId(product.getProductId(), category.getCategoryId());
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCategory.setProduct(product);
            productCategory.setCategory(category);
            productCategoryRepository.save(productCategory);
        }

        return product;
    }

    @Transactional
    public Product updateProductById(Long productId, ProductRequest request) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        Set<Category> categories = categoryRepository.findByCategoryIdIn(request.categoryIds());

        if (categories.size() != request.categoryIds().size()) {
            throw new IllegalArgumentException("Some category IDs are invalid!");
        }
        productCategoryRepository.deleteByProduct(product);

        product.setName(request.name());
        product.setPrice(request.price());
        product.setImage(request.image());
        product.setAvailableQuantity(request.availableQuantity());

        repository.save(product);

        for(Category category : categories){
            ProductCategoryId productCategoryId = new ProductCategoryId(product.getProductId(), category.getCategoryId());
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCategory.setProduct(product);
            productCategory.setCategory(category);
            productCategoryRepository.save(productCategory);
        }

        return product;
    }

    @Transactional
    public void deleteProductById(Long productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        productCategoryRepository.deleteByProduct(product);
        repository.delete(product);
    }

    public List<Category> getAllCategoriesByProduct(Long productId) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        return productCategoryRepository.findByProduct(product)
                .stream()
                .map(ProductCategory::getCategory)
                .toList();
    }
}
