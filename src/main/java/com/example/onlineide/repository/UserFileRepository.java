package com.example.onlineide.repository;


import com.example.onlineide.domain.UserFile;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFileRepository {

    private final EntityManager em;

    @Transactional
    public void save(UserFile userFile){
        em.persist(userFile);
    }

    public List<UserFile> getAllFiles(String memberId){
        return em.createQuery("select u from UserFile u where u.member.id =: memberId", UserFile.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
