package com.invsys.springboot_inventorysystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invsys.springboot_inventorysystem.model.Category;
import com.invsys.springboot_inventorysystem.model.Product;
import com.invsys.springboot_inventorysystem.model.Supplier;
import com.invsys.springboot_inventorysystem.repository.CategoryRepository;
import com.invsys.springboot_inventorysystem.repository.ProductRepository;
import com.invsys.springboot_inventorysystem.repository.SupplierRepository;

@Service
public class ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private SupplierRepository supplierRepository;
    
    public Product createProduct(Product product) {
        if(product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + product.getCategory().getId()));

            product.setCategory(category);
        }

        if(product.getSupplier() != null && product.getSupplier().getId() != null) {
            Supplier supplier = supplierRepository.findById(product.getSupplier().getId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + product.getSupplier().getId()));

            product.setSupplier(supplier);
        }

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        
        product.setName(productDetails.getName());
        product.setQuantity(productDetails.getQuantity());

        if(productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
            Category category = categoryRepository.findById(productDetails.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDetails.getCategory().getId()));

            product.setCategory(category);
        }

        if(productDetails.getSupplier() != null && productDetails.getSupplier().getId() != null) {
            Supplier supplier = supplierRepository.findById(productDetails.getSupplier().getId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + productDetails.getSupplier().getId()));

            product.setSupplier(supplier);
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
}
