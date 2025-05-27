package com.invsys.springboot_inventorysystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invsys.springboot_inventorysystem.model.Supplier;
import com.invsys.springboot_inventorysystem.repository.SupplierRepository;

@Service
public class SupplierService {
    
    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id " + id));
        
        supplier.setName(supplierDetails.getName());
        supplier.setContactPerson(supplierDetails.getContactPerson());
        supplier.setPhone(supplierDetails.getPhone());

        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
