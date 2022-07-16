package com.example.onlineide.domain;


import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private String ownPath;

    @OneToMany(mappedBy = "member")
    private List<UserFile> files = new ArrayList<>();

    //사용자별 고유 디렉토리생성
    public void createOwnDirectory(){
        ownPath = "src/main/java/com/example/onlineide/userprojectfile/" + name;
        File file = new File(ownPath);
        file.mkdir();
    }
}
