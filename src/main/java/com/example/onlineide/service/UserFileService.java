package com.example.onlineide.service;


import com.example.onlineide.domain.UserFile;
import com.example.onlineide.repository.UserFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFileService {

    private final UserFileRepository userFileRepository;
    public void save(UserFile userFile){
        userFileRepository.save(userFile);
    }

    public List<UserFile> getAllFiles(String memberId){
        return userFileRepository.getAllFiles(memberId);
    }
}
