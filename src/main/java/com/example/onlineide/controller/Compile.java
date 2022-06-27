package com.example.onlineide.controller;


import com.example.onlineide.dto.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

@Controller
@Slf4j
public class Compile {

    @PostMapping("/compile.java")
    public void compile(@ModelAttribute Code code) {
        log.info("code = {}", code.getLanguage());
        log.info("code = {}", code.getCode());

        String lowLang = code.getLanguage().toLowerCase();

        BufferedWriter writer = null;
        try {
            File file = new File("src/main/java/com/example/onlineide/file/" + UUID.randomUUID() + "." + lowLang);
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(code.getCode());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
