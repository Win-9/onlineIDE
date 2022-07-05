package com.example.onlineide.service;

import com.example.onlineide.dto.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.StringTokenizer;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class Generate {

    public String separate(String plainCode,String filePath, String plainLowLang) throws IOException {
        String code = plainCode;

        String fileName = null;
        String lowLang = plainLowLang.toLowerCase(); // 언어종류

        log.info("code = {}", plainCode);

        if (!lowLang.equals("java")){
            fileName = String.valueOf(UUID.randomUUID());//파일이름은 고유한 UUID
        }
        else{
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
        log.info("result = {}", result);
        return result.toString();
    }
}
