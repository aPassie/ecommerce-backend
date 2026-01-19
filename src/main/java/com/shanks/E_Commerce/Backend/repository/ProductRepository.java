package com.shanks.E_Commerce.Backend.repository;

import com.shanks.E_Commerce.Backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
