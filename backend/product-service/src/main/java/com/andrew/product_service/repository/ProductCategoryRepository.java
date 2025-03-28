package com.andrew.product_service.repository;

import com.andrew.product_service.entity.Category;
import com.andrew.product_service.entity.Product;
import com.andrew.product_service.entity.ProductCategory;
import com.andrew.product_service.entity.ProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId> {

    void deleteByProduct(Product product);
    List<ProductCategory> findByCategory(Category category);
    List<ProductCategory> findByProduct(Product product);

}
