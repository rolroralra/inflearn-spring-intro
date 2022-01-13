package com.example.demo.repository.impl;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
    private final EntityManager entityManager;

    @Override
    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Member.class, id));
    }

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("SELECT m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return entityManager.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList().stream().findAny();
    }

    @Override
    public void delete(Member member) {
        deleteById(member.getId());
    }

    @Override
    public void deleteById(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (Objects.nonNull(member)) {
            entityManager.remove(member);
        }
    }

    @Override
    public void deleteAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Member> delete = cb.createCriteriaDelete(Member.class);
        delete.from(Member.class);
        Query deleteQuery = entityManager.createQuery(delete);

        deleteQuery.executeUpdate();
    }
}
