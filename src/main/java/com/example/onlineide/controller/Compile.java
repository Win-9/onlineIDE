package com.example.onlineide.controller;


import com.example.onlineide.dto.Code;
import com.example.onlineide.service.Generate;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class Compile {
    private final Generate generate;

    @PostMapping("/compile.java")
    @ResponseBody
    public String compile(@ModelAttribute Code code) throws IOException {
        log.info("compile controller");

        String fileName = String.valueOf(UUID.randomUUID()); //고유한 UUID
        String filePath = "src/main/java/com/example/onlineide/file/" + fileName;// 새로 생성될 파일경로

        return generate.separate(String.valueOf(UUID.randomUUID()), filePath+fileName, code.getLanguage());
    }
}
