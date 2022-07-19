package com.example.onlineide.controller;


import com.example.onlineide.domain.Member;
import com.example.onlineide.domain.UserFile;
import com.example.onlineide.dto.CodeDto;
import com.example.onlineide.service.UserFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CompileController {
    private final UserFileService userFileService;

    @GetMapping("{memberId}/{projectName}/ide")
    public String ide(@PathVariable String memberId, String projectName,
                      HttpServletRequest request, Model model) {
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
    public String compile(@ModelAttribute CodeDto code, Model model) throws IOException {
        log.info("compile controller");

        Member member = (Member) model.getAttribute("member");
        log.info("memberName = {}", member.getName());

        UserFile userFile = (UserFile) model.getAttribute("userFile");
        log.info("userFileName = {}", userFile.getFileName());
        String filePath = "src/main/java/com/example/onlineide/userprojectfile/" + userFile.getFileName() + "/";// 새로 생성될 파일경로


        return userFileService.separate(code.getCode(), filePath, code.getLanguage());
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false); // 새 세션생성 x

        log.info("logout-controller");

        session.invalidate();


        return "redirect:/";
    }
}
