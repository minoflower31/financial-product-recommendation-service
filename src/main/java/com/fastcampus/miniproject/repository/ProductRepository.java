package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
