package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c join fetch c.product where c.member.id = :memberId")
    List<Cart> findByMemberId(@Param("memberId") Long memberId);

    Optional<Cart> findByMemberIdAndProductId(Long memberId, Long ProductId);

}

