package com.study.vue_backend.repository;

import com.study.vue_backend.entity.Member;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Optional<Member> findByEmailAndPassword(String email, String password);
}
