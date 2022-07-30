package com.example.onlineide.domain;

import javax.persistence.*;
import java.io.File;

@Entity
public class UserFile {

    @Id
    @Column(name = "FILE_ID")
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Embedded
    private Code code;

    @Enumerated(EnumType.STRING)
    private LangStatus langStatus;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Member getMember() {
        return member;
    }

    public Code getCode() {
        return code;
    }

    public LangStatus getLangStatus() {
        return langStatus;
    }

    public void setLangStatus(LangStatus langStatus) {
        this.langStatus = langStatus;
    }

    //편의메소드
    public void setMember(Member member){
        this.member = member;
        this.member.getFiles().add(this);
    }

    public void registerProject(){
        String path = member.getOwnPath() + "/" + fileName;
        File file = new File(path);
        file.mkdir();
    }
}
