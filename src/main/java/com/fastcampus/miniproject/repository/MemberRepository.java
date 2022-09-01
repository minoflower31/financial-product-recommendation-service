package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findMemberByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
