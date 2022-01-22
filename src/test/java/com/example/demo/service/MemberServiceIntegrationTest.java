package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles(value = {"dev"})
@Transactional
public class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void test_회원가입() {
        // Given
        Member member = new Member();
        member.setName("rolroralra" + UUID.randomUUID());

        // When
        Long memberId = memberService.join(member);

        // Then
        assertThat(memberRepository.findById(memberId))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("name", member.getName());
    }

    @Test
    public void test_중복회원가입_예외() {
        // Given
        String name = "rolroralra" + UUID.randomUUID();
        Member member = new Member();
        member.setName(name);

        Member member2 = new Member();
        member2.setName(name);

        // When
        memberService.join(member);

        // Then
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> memberService.join(member2));
    }
}
