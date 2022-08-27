package com.fastcampus.miniproject.repository;

import com.fastcampus.miniproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
