package com.example.demo.controller;

import com.example.demo.controller.dto.MemberDto;
import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping(value = {"", "/"})
    MemberDto join(@RequestBody MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());

        Long newId = memberService.join(member);
        memberDto.setId(newId);
        return memberDto;
    }

    @GetMapping(value = {"", "/"})
    List<MemberDto> getAllMembers() {
        return memberService.findAllMembers().stream().map(MemberDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{memberId}")
    MemberDto getMember(@PathVariable Long memberId) {
        return new MemberDto(memberService.findOneMember(memberId).orElseThrow());
    }
}
