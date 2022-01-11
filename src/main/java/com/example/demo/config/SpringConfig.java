package com.example.demo.config;

import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.impl.JdbcMemberRepository;
import com.example.demo.repository.impl.MemoryMemberRepository;
import com.example.demo.service.MemberService;
import com.example.demo.service.impl.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SpringConfig {
    private final DataSource dataSource;

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
}
