package com.invsys.springboot_inventorysystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invsys.springboot_inventorysystem.model.Product;
import com.invsys.springboot_inventorysystem.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    // Non-paginated GET all
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Pagination + Filtering using JPA Specifications (Combined)

    @GetMapping
    public ResponseEntity<List<Product>> getFilteredProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minQuantity,
            @RequestParam(required = false) Long lastId,
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "10") int size) {
        
        List<Product> products = productService.getFilteredProducts(name, categoryName, supplierName, minPrice, maxPrice, minQuantity, lastId, lastName, size);
        return ResponseEntity.ok(products);
    }



// Pagination
    // @GetMapping
    // public ResponseEntity<Page<Product>> getAllProductsPaginated(Pageable pageable) {
    //     Page<Product> products = productService.getAllProductsPaginated(pageable);
    //     return ResponseEntity.ok(products);
    // } 

// Filtering (using custom methods)

    // @GetMapping("/filter/by-name")
    // public ResponseEntity<Page<Product>> getProductsByName(
    //         @RequestParam String name, 
    //         Pageable pageable) {
    //     Page<Product> products = productService.getProductsByName(name, pageable);
    //     return ResponseEntity.ok(products);
    // }

    // @GetMapping("/filter/by-category")
    // public ResponseEntity<Page<Product>> getProductsByCategoryName(
    //         @RequestParam String categoryName, 
    //         Pageable pageable) {
    //     Page<Product> products = productService.getProductsByCategoryName(categoryName, pageable);
    //     return ResponseEntity.ok(products);
    // }

    // @GetMapping("/filter/by-price-range")
    // public ResponseEntity<Page<Product>> getProductsByPriceRange(
    //         @RequestParam Double minPrice,
    //         @RequestParam Double maxPrice,
    //         Pageable pageable) {
    //     Page<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice, pageable);
    //     return ResponseEntity.ok(products);
    // }

    // @GetMapping("/filter/by-min-quantity")
    // public ResponseEntity<Page<Product>> getProductsByMinQuantity(
    //         @RequestParam Integer minQuantity,
    //         Pageable pageable) {
    //     Page<Product> products = productService.getProductsByMinQuantity(minQuantity, pageable);
    //     return ResponseEntity.ok(products);
    // }
}