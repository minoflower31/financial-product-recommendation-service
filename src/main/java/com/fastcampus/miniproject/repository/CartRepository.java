package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
