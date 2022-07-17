package com.example.onlineide.domain;

import javax.persistence.*;
import java.io.File;

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


    public String getSubPath() {
        return subPath;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    //편의메소드
    public void setMember(Member member){
        this.member = member;
        this.member.getFiles().add(this);
    }

    public void registerProject(){
        String path = member.getOwnPath() + "/" + subPath;
        File file = new File(path);
        file.mkdir();
    }
}
