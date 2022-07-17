package com.example.onlineide;


import com.example.onlineide.domain.Address;
import com.example.onlineide.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Controller
@RequiredArgsConstructor
public class TestDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = new Member();
            Address address = new Address();
            address.setCity("서울");
            address.setZipcode("강동구");
            address.setEtc("256동71호");

            member.setAddress(address);
            member.setName("testUserName");
            member.setId("test1234");
            member.setPassword("1234");

            member.createOwnDirectory();
            em.persist(member);
        }

    }
}
