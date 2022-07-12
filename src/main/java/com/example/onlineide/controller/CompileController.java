package com.example.onlineide.controller;


import com.example.onlineide.dto.Code;
import com.example.onlineide.service.GenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CompileController {
    private final GenerateService generateService;


    @GetMapping("/ide")
    public String ide(){
        return "ide/ide";
    }

    @PostMapping("/compile.java")
    @ResponseBody
    public String compile(@ModelAttribute Code code) throws IOException {
        log.info("compile controller");

        String filePath = "src/main/java/com/example/onlineide/file/";// 새로 생성될 파일경로

        return generateService.separate(code.getCode(), filePath, code.getLanguage());
    }
}
