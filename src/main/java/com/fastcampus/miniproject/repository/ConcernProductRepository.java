package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.entity.ConcernProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcernProductRepository extends JpaRepository<ConcernProduct, Long> {

    @Query(" select c from ConcernProduct c where c.member.id = :memberId")
    List<ConcernProduct> findByMemberId(@Param("memberId") Long memberId);

    Optional<ConcernProduct> findByMemberIdAndProductId(Long memberId, Long ProductId);

}

