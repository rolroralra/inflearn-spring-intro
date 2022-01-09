package com.example.demo.repository.impl;

import com.example.demo.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    @BeforeEach
    void beforeEach() {
        memoryMemberRepository.clear();

        Member member = new Member();
        member.setName("admin");
        memoryMemberRepository.save(member);
    }

    @AfterEach
    void afterEach() {
        memoryMemberRepository.clear();
    }

    @Test
    void save() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");

        // When
        memoryMemberRepository.save(member);

        // Then
        Optional<Member> findMember = memoryMemberRepository.findById(member.getId());
        assertThat(findMember)
                .isPresent()
                .get()
                .isEqualTo(member);
    }

    @Test
    void findById() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");
        memoryMemberRepository.save(member);

        Member member2 = new Member();
        member2.setName("guest");
        memoryMemberRepository.save(member2);

        // When
        Optional<Member> findMember = memoryMemberRepository.findById(member.getId());

        // Then
        assertThat(findMember)
                .isPresent()
                .get()
                .isEqualTo(member);

    }

    @Test
    void findAll() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");
        memoryMemberRepository.save(member);

        Member member2 = new Member();
        member2.setName("guest");
        memoryMemberRepository.save(member2);

        // When
        List<Member> findAllMemberList = memoryMemberRepository.findAll();

        // Then
        assertThat(findAllMemberList).hasSize(3);
    }

    @Test
    void findByName() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");
        memoryMemberRepository.save(member);

        Member member2 = new Member();
        member2.setName("guest");
        memoryMemberRepository.save(member2);

        // When
        Optional<Member> findMember = memoryMemberRepository.findByName(member.getName());

        // Then
        assertThat(findMember)
                .isPresent()
                .get()
                .isEqualTo(member);
    }
}