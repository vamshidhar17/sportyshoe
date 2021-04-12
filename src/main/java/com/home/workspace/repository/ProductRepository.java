package com.home.workspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.workspace.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findById(long id);
}
