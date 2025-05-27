package com.invsys.springboot_inventorysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.invsys.springboot_inventorysystem.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
   

}
