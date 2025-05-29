package com.invsys.springboot_inventorysystem.service.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.invsys.springboot_inventorysystem.model.Product;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {
    // Static methods for creating specifications

    public static Specification<Product> hasProductName(String name) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"); 
    }

    public static Specification<Product> hasCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(criteriaBuilder.lower(root.join("category", JoinType.INNER).get("name")), categoryName.toLowerCase());  
    }

    public static Specification<Product> hasSupplierName(String supplierName) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(criteriaBuilder.lower(root.join("supplier", JoinType.INNER)), supplierName.toLowerCase());
    }

    public static Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }    

    public static Specification<Product> hasQuantityGreaterThanOrEqualTo(Integer minQuantity) {
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), minQuantity);
    }

    // Specification to filter results based on a curson (Keyset Pagination)

    public static Specification<Product> keysetPagination(Long lastId, String lastProductName) {
        return (root, query, criteriaBuilder) -> {
            // If no cursor is provided (first page)
            if(lastId == null || lastProductName == null) {
                return criteriaBuilder.conjunction(); // returns true thus, no filter
            }

            Predicate sameNameGreaterId = criteriaBuilder.and(
                criteriaBuilder.equal(root.get("name"), lastProductName), 
                criteriaBuilder.greaterThan(root.get("id"), lastId)  
            );

            Predicate sameName = criteriaBuilder.greaterThan(root.get("name"), lastProductName);

            return criteriaBuilder.or(sameNameGreaterId, sameName);
        };
    }
}
