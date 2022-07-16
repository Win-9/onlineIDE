package com.example.onlineide.controller;


import com.example.onlineide.domain.Member;
import com.example.onlineide.dto.CodeDto;
import com.example.onlineide.service.GenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CompileController {
    private final GenerateService generateService;


    @GetMapping("/ide")
    public String ide(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) { // 빈세션
            return "error";
        }

        Member loginMember = (Member) session.getAttribute("member");

        if (loginMember == null) { // 빈객체
            return "error";
        }

        model.addAttribute("member", loginMember);

        return "ide/ide";
    }

    @PostMapping("/compile.java")
    @ResponseBody
    public String compile(@ModelAttribute CodeDto code) throws IOException {
        log.info("compile controller");

        String filePath = "src/main/java/com/example/onlineide/file/";// 새로 생성될 파일경로

        return generateService.separate(code.getCode(), filePath, code.getLanguage());
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false); // 새 세션생성 x

        log.info("logout-controller");

        session.invalidate();


        return "redirect:/";
    }
}
