package com.invsys.springboot_inventorysystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.invsys.springboot_inventorysystem.model.Category;
import com.invsys.springboot_inventorysystem.model.Product;
import com.invsys.springboot_inventorysystem.model.Supplier;
import com.invsys.springboot_inventorysystem.repository.CategoryRepository;
import com.invsys.springboot_inventorysystem.repository.ProductRepository;
import com.invsys.springboot_inventorysystem.repository.SupplierRepository;
import com.invsys.springboot_inventorysystem.service.specifications.ProductSpecification;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Transactional
    public Product createProduct(Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(
                            () -> new RuntimeException("Category not found with id: " + product.getCategory().getId()));

            product.setCategory(category);
        }

        if (product.getSupplier() != null && product.getSupplier().getId() != null) {
            Supplier supplier = supplierRepository.findById(product.getSupplier().getId())
                    .orElseThrow(
                            () -> new RuntimeException("Supplier not found with id: " + product.getSupplier().getId()));

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

    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        product.setName(productDetails.getName());
        product.setQuantity(productDetails.getQuantity());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());

        if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
            Category category = categoryRepository.findById(productDetails.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Category not found with id: " + productDetails.getCategory().getId()));

            product.setCategory(category);
        }

        if (productDetails.getSupplier() != null && productDetails.getSupplier().getId() != null) {
            Supplier supplier = supplierRepository.findById(productDetails.getSupplier().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "Supplier not found with id: " + productDetails.getSupplier().getId()));

            product.setSupplier(supplier);
        }

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Pagination
    public Page<Product> getAllProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    // Filtering (Using Specification with Offset or Keyset)
    public List<Product> getFilteredProducts(
        String name,
        String categoryName,
        String supplierName,
        Double minPrice,
        Double maxPrice,
        Integer minQuantity,
        Long lastId,
        String lastProductName,
        int pageSize) {

            Specification<Product> spec = Specification.not(null);

            // Offset Pagination Logic

            if (name != null && !name.isEmpty())
                spec = spec.and(ProductSpecification.hasProductName(name));

            if (categoryName != null && !categoryName.isEmpty())
                spec = spec.and(ProductSpecification.hasCategoryName(categoryName));

            if (supplierName != null && !supplierName.isEmpty())
                spec = spec.and(ProductSpecification.hasSupplierName(supplierName));

            if (minPrice != null && maxPrice != null) {
                spec = spec.and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));
            } else if (minPrice != null) {
                spec = spec.and((root, query, criteriaBuilder) -> 
                        criteriaBuilder.greaterThanOrEqualTo(root.get("price"),
                        minPrice));
            } else if (maxPrice != null) {
                spec = spec.and((root, query, criteriaBuilder) -> 
                        criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (minQuantity != null)
                spec = spec.and(ProductSpecification.hasQuantityGreaterThanOrEqualTo(minQuantity));

            // Keyset Pagination Logic

            Sort keysetSort = Sort.by(Sort.Order.asc("name"), Sort.Order.asc("id"));

            if (lastId != null && lastProductName != null) {
                spec = spec.and(ProductSpecification.keysetPagination(lastId, lastProductName));

                Pageable keysetPageable = PageRequest.of(0, pageSize, keysetSort);

                return productRepository.findAll(spec, keysetPageable).getContent();
            } else {
                Pageable offsetPageable = PageRequest.of(0, pageSize, keysetSort);

                return productRepository.findAll(spec, offsetPageable).getContent();
            }
    }

    // Filtering (Using custom methods)

    // public Page<Product> getProductsByName(String name, Pageable pageable) {
    // return productRepository.findByNameContainingIgnoreCase(name, pageable);
    // }

    // public Page<Product> getProductsByCategoryName(String categoryName, Pageable
    // pageable) {
    // return productRepository.findByCategory_NameIgnoreCase(categoryName,
    // pageable);
    // }

    // public Page<Product> getProductsBySupplierName(String supplierName, Pageable
    // pageable) {
    // return productRepository.findBySupplier_NameIgnoreCase(supplierName,
    // pageable);
    // }

    // public Page<Product> getProductsByPriceRange(Double minPrice, Double
    // maxPrice, Pageable pageable) {
    // return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
    // }

    // public Page<Product> getProductsByMinQuantity(Integer minQuantity, Pageable
    // pageable) {
    // return productRepository.findByQuantityGreaterThanEqual(minQuantity,
    // pageable);
    // }

}
