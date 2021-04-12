package com.home.workspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.workspace.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

	  Category findById(long id);
}
