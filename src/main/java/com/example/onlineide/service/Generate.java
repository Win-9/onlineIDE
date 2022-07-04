package com.example.onlineide.service;

import com.example.onlineide.dto.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class Generate {

    public String separate(String plainCode,String filePath, String plainLowLang) throws IOException {
        String code = plainCode;

        String fileName = String.valueOf(UUID.randomUUID()); //고유한 UUID
        String lowLang = plainLowLang.toLowerCase(); // 언어종류

        registFile(code,lowLang, filePath);

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
        return result.toString();
    }
}
