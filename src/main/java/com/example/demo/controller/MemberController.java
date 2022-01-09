package com.example.demo.controller;

import com.example.demo.controller.dto.MemberDto;
import com.example.demo.domain.Member;
import com.example.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/list")
    String listAllMembers(Model model) {
        model.addAttribute("members", this.getAllMembers());
        return "members/memberList";
    }

    @GetMapping("/new")
    String createNewMemberForm() {
        return "members/createNewMember";
    }

    @PostMapping("/new")
    String createNewMember(MemberDto memberDto) {
        this.join(memberDto);

        return "redirect:/";
    }

    @PostMapping(value = {"", "/"})
    @ResponseBody
    MemberDto join(@RequestBody MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());

        Long newId = memberService.join(member);
        memberDto.setId(newId);
        return memberDto;
    }

    @GetMapping(value = {"", "/"})
    @ResponseBody
    List<MemberDto> getAllMembers() {
        return memberService.findAllMembers().stream().map(MemberDto::new).collect(Collectors.toList());
    }

    @GetMapping("/{memberId}")
    @ResponseBody
    MemberDto getMember(@PathVariable Long memberId) {
        return new MemberDto(memberService.findOneMember(memberId).orElseThrow());
    }
}
