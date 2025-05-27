package com.invsys.springboot_inventorysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invsys.springboot_inventorysystem.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    

}
