package com.example.demo.repository;

import com.example.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    List<Member> findAll();

    Optional<Member> findByName(String name);

    void delete(Member member);
    void deleteById(Long id);
    void deleteAll();
}
