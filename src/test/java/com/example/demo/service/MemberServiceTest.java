package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.impl.MemoryMemberRepository;
import com.example.demo.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberServiceImpl(memberRepository);
    }

    @ParameterizedTest
    @ValueSource(strings = {"rolroralra", "test"})
    void test_join_member(String memberName) {
        // Given
        Member member = new Member();
        member.setName(memberName);

        // When
        memberService.join(member);

        // Then
        Optional<Member> findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isPresent().get().hasFieldOrPropertyWithValue("name", memberName);
    }

    @Test
    void validateDuplicateMember() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");
        memberService.join(member);

        Member member2 = new Member();
        member2.setName("rolroralra");

        // When, Then
        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                memberService.join(member2)
        ).withMessage("Already Exists Member.");
    }

    @Test
    void findAllMembers() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");

        Member member2 = new Member();
        member2.setName("guest");

        memberService.join(member);
        memberService.join(member2);

        // When
        List<Member> findAllMemberList = memberService.findAllMembers();

        // Then
        assertThat(findAllMemberList).hasSize(2);
    }

    @Test
    void findOneMember() {
        // Given
        Member member = new Member();
        member.setName("rolroralra");

        memberService.join(member);

        // When
        Optional<Member> findOneMember = memberService.findOneMember(member.getId());

        // Then
        assertThat(findOneMember).isPresent().get().isEqualTo(member);
    }
}