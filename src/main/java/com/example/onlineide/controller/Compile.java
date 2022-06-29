package com.example.onlineide.controller;


import com.example.onlineide.dto.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.UUID;

@Controller
@Slf4j
public class Compile {

    @PostMapping("/compile.java")
    @ResponseBody
    public String compile(@ModelAttribute Code code) throws IOException {
        log.info("lang = {}", code.getLanguage());
        log.info("code = {}", code.getCode());

        String fileName = String.valueOf(UUID.randomUUID()); //고유한 UUID
        String filePath = "src/main/java/com/example/onlineide/file/" + fileName;// 새로 생성될 파일경로
        String lowLang = code.getLanguage().toLowerCase(); // 언어종류

        registFile(code.getCode(),lowLang, filePath);

        return compileCode(lowLang, filePath, fileName);
    }

    public void registFile(String code, String lowLang, String filePath){
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

    public String compileCode(String language, String filePath, String fileName) throws IOException {
        Process process = null;
        StringBuilder result = new StringBuilder();

        if (language.equals("c")){
            process =  new ProcessBuilder("C:\\MinGW\\bin\\gcc.exe"
                    ,"-o", fileName+" "+filePath+"."+language).start();
        }
        else if(language.equals("cpp")){
            process =  new ProcessBuilder("C:\\MinGW\\bin\\g++.exe"
                    ,filePath+"."+language).start();
        }
        else if(language.equals("java")){
            process =  new ProcessBuilder("C:\\Program Files\\Java\\jdk-11.0.10\\bin\\javaw.exe"
                    ,filePath+"."+language).start();
        }
        else if(language.equals("js")){
            process =  new ProcessBuilder("C:\\Program Files\\nodejs\\node.exe"
                    ,filePath+"."+language).start();
        }
        else if(language.equals("py")){
            process =  new ProcessBuilder("C:\\Python310\\python.exe"
                    ,filePath+"."+language).start();
        }


        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null; (line = br.readLine()) != null;)
            result.append(line);
        return result.toString();
    }
}
