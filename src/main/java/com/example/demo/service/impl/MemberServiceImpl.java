package com.example.demo.service.impl;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findOneMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public void validateDuplicateMember(Member member) {
        if (isDuplicatedMember(member)) {
            throw new IllegalStateException("Already Exists Member.");
        }
    }

    private boolean isDuplicatedMember(Member member) {
        return memberRepository.findByName(member.getName()).isPresent();
    }
}
