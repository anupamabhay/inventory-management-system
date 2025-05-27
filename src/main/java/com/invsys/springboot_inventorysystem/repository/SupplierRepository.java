package com.invsys.springboot_inventorysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.invsys.springboot_inventorysystem.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
