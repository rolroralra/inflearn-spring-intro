package com.example.demo.config;

import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.impl.MemoryMemberRepository;
import com.example.demo.service.MemberService;
import com.example.demo.service.impl.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
