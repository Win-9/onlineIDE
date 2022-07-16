package com.example.onlineide.domain;



import javax.persistence.*;

@Entity
public class UserFile {

    @Id @GeneratedValue
    @Column(name = "FILE_ID")
    private Long id;
    private String subPath;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Embedded
    private Code code;

}
