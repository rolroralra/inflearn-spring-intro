package com.example.demo.repository.impl;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> storage;
    private static AtomicLong sequence;

    public MemoryMemberRepository() {
        storage = new ConcurrentHashMap<>();
        sequence = new AtomicLong(0L);
    }

    @Override
    public Member save(Member member) {
        member.setId(sequence.incrementAndGet());
        storage.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Member> findByName(String name) {
        return storage.values().stream()
                .filter(member -> Objects.equals(name, member.getName()))
                .findAny();
    }

    @Override
    public void delete(Member member) {
        storage.remove(member.getId());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public void deleteAll() {
        storage.clear();
        sequence.set(0L);
    }
}
