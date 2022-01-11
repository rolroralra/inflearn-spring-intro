package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
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
        member.setName("rolroralra");

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
        Member member = new Member();
        member.setName("rolroralra");

        Member member2 = new Member();
        member2.setName("rolroralra");

        // When
        memberService.join(member);

        // Then
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> memberService.join(member2));
    }
}
