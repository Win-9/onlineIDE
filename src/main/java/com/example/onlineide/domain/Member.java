package com.example.onlineide.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    private String id;
    private String password;
    private String name;

    @Embedded
    private Address address;
    private String email;
}
