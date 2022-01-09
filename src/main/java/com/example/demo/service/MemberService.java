package com.example.demo.service;

import com.example.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Long join(Member member);

    void validateDuplicateMember(Member member);

    List<Member> findAllMembers();

    Optional<Member> findOneMember(Long memberId);
}
