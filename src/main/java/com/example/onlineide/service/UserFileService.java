package com.example.onlineide.service;


import com.example.onlineide.domain.UserFile;
import com.example.onlineide.repository.UserFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFileService {

    private final UserFileRepository userFileRepository;
    public void save(UserFile userFile){
        userFileRepository.save(userFile);
    }

    public UserFile findByFileName(String fileName){
        return userFileRepository.findOne(fileName);
    }

    public List<UserFile> getAllFiles(String memberId){
        return userFileRepository.getAllFiles(memberId);
    }

    public String separate(String plainCode,String filePath, String plainLowLang) throws IOException {
        String code = plainCode;

        String fileName = null;
        String lowLang = plainLowLang.toLowerCase(); // 언어종류

        log.info("code = {}", plainCode);

        if (!lowLang.equals("java")){ // 자바가 아닐때
            fileName = String.valueOf(UUID.randomUUID());//파일이름은 고유한 UUID
        }
        else{ // 자바일때의 처리
            String[]str = code.split(" ");
            StringBuilder sb = new StringBuilder(str[2]);

            fileName = sb.delete(sb.length() - 3, sb.length()).toString();
            log.info("fileName = {}", fileName);
            log.info("fullPath = {}", filePath + fileName+"."+lowLang);
        }
        registerFile(code,lowLang, filePath + fileName);
        return compileCode(lowLang, filePath, fileName);
    }

    private void registerFile(String code, String lowLang, String filePath){
        BufferedWriter writer = null;
        try {
            File file = new File(filePath + "." + lowLang);
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(code);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String compileCode(String language, String filePath, String fileName) throws IOException {
        Process process = null;
        StringBuilder result = new StringBuilder();

        if(language.equals("java")){
            process =  new ProcessBuilder("src/main/resources/compiler/javaw.exe"
                    ,filePath+fileName+"."+language).start();
        }
        else if(language.equals("js")){
            process =  new ProcessBuilder("src/main/resources/compiler/node.exe"
                    ,filePath+fileName+"."+language).start();
        }
        else if(language.equals("py")){
            process =  new ProcessBuilder("src/main/resources/compiler/python.exe"
                    ,filePath+fileName+"."+language).start();
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null; (line = br.readLine()) != null;)
            result.append(line);
        log.info("result = {}", result);
        return result.toString();
    }

}
