package com.app.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ecom.enums.PRODUCT_STATUS;
import com.app.ecom.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(PRODUCT_STATUS status);
}
