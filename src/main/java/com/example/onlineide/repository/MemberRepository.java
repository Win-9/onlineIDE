package com.example.onlineide.repository;

import com.example.onlineide.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRepository {
    private final EntityManager em;

    @Transactional
    public void saveMember(Member member){
        em.persist(member);
    }

    public Member findOne(String id){
        return em.find(Member.class, id);
    }
}
