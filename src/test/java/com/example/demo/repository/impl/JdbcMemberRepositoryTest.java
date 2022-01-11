package com.example.demo.repository.impl;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"classpath:schema.sql"})
class JdbcMemberRepositoryTest {
    @Autowired
    private DataSource dataSource;
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new JdbcMemberRepository(dataSource);
        Member member = new Member();
        member.setName("admin");
        memberRepository.save(member);
    }

    @Test
    void save() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");

        // When
        memberRepository.save(member);

        // Then
        Optional<Member> findMember = memberRepository.findById(member.getId());
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
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("guest");
        memberRepository.save(member2);

        // When
        Optional<Member> findMember = memberRepository.findById(member.getId());

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
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("guest");
        memberRepository.save(member2);

        // When
        List<Member> findAllMemberList = memberRepository.findAll();

        // Then
        assertThat(findAllMemberList).hasSize(3);
    }

    @Test
    void findByName() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("guest");
        memberRepository.save(member2);

        // When
        Optional<Member> findMember = memberRepository.findByName(member.getName());

        // Then
        assertThat(findMember)
                .isPresent()
                .get()
                .isEqualTo(member);
    }

    @Test
    void delete() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("guest");
        memberRepository.save(member2);

        // When
        memberRepository.delete(member);

        // Then
        assertThat(memberRepository.findByName("rolroralra"))
                .isNotPresent();
        assertThat(memberRepository.findByName("guest"))
                .isPresent()
                .get()
                .isEqualTo(member2);
        assertThat(memberRepository.findAll())
                .hasSize(2);
    }

    @Test
    void deleteById() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("guest");
        memberRepository.save(member2);

        // When
        memberRepository.deleteById(member.getId());

        // Then
        assertThat(memberRepository.findByName("rolroralra"))
                .isNotPresent();
        assertThat(memberRepository.findByName("guest"))
                .isPresent()
                .get()
                .isEqualTo(member2);
        assertThat(memberRepository.findAll())
                .hasSize(2);
    }

    @Test
    void deleteAll() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("guest");
        memberRepository.save(member2);

        // When
        memberRepository.deleteAll();

        // Then
        assertThat(memberRepository.findAll())
                .isEmpty();
    }
}