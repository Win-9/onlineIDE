package com.example.onlineide.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class GenerateService {

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
        registFile(code,lowLang, filePath + fileName);
        return compileCode(lowLang, filePath, fileName);
    }

    private void registFile(String code, String lowLang, String filePath){
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
            process =  new ProcessBuilder("src/main/java/com/example/onlineide/compiler/javaw.exe"
                    ,filePath+fileName+"."+language).start();
        }
        else if(language.equals("js")){
            process =  new ProcessBuilder("src/main/java/com/example/onlineide/compiler/node.exe"
                    ,filePath+fileName+"."+language).start();
        }
        else if(language.equals("py")){
            process =  new ProcessBuilder("src/main/java/com/example/onlineide/compiler/python.exe"
                    ,filePath+fileName+"."+language).start();
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null; (line = br.readLine()) != null;)
            result.append(line);
        log.info("result = {}", result);
        return result.toString();
    }
}
