package com.invsys.springboot_inventorysystem.repository;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.invsys.springboot_inventorysystem.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

// Filtering: Using JpaSpecificationExecutor (Specification)

// Filtering: Using custom methods
    
    // Find products by name (case-sensitive, contains) with pagination & sorting
    // Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Find products by category name (case-insensitive, exact match) with pagination & sorting
    // Page<Product> findByCategory_NameIgnoreCase(String categoryName, Pageable pageable);

    // Find products by supplier name (case-insensitive, exact match) with pagination & sorting
    // Page<Product> findBySupplier_NameIgnoreCase(String supplierName, Pageable pageable);

    // Find products by price range with pagination & sorting
    // Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    // Find products by quantity greater than or equal to a value with pagination & sorting
    // Page<Product> findByQuantityGreaterThanEqual(Integer minQuantity, Pageable pageable);

}
