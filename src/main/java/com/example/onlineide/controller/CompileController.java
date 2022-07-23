package com.example.onlineide.controller;


import com.example.onlineide.domain.Code;
import com.example.onlineide.domain.LangStatus;
import com.example.onlineide.domain.Member;
import com.example.onlineide.domain.UserFile;
import com.example.onlineide.dto.IdeCodeDto;
import com.example.onlineide.service.UserFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Scanner;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CompileController {
    private final UserFileService userFileService;

    @GetMapping("{memberId}/{projectName}/ide")
    public String ide(@PathVariable String memberId, @PathVariable String projectName,
                      HttpServletRequest request, Model model) throws FileNotFoundException {
        HttpSession session = request.getSession(false);
        if (session == null) { // 빈세션
            return "error";
        }

        Member loginMember = (Member) session.getAttribute("member");

        if (loginMember == null) { // 빈객체
            return "error";
        }

        File findFile = getFileList(memberId, projectName);

        if (findFile != null) {
            StringBuilder sb = new StringBuilder();
            Scanner scan = new Scanner(findFile);
            while(scan.hasNextLine()){
                sb.append(scan.nextLine());
            }

            model.addAttribute("code", sb.toString());
        }

        log.info("fileName = {}", projectName);

        model.addAttribute("member", loginMember);
        model.addAttribute("projectName", projectName);

        return "ide/ide";
    }

    private File getFileList(String memberId, String projectName) {
        String filePath = "src/main/java/com/example/onlineide/userprojectfile/"
                + memberId + "/" + projectName + "/";
        File file = new File(filePath);
        File[] files = file.listFiles();
        if (files == null){
            return null;
        }
        log.info("fileName = {}", files[0].getName());

        return files[0];
    }

    @PostMapping("/compile.java")
    @ResponseBody
    public String compile(@ModelAttribute IdeCodeDto code, Model model) throws IOException {
        log.info("compile controller");

        String projectName = code.getProjectName();
        log.info("fileName = {}", projectName);

        UserFile findFile = userFileService.findByFileName(projectName);
        String memberName = findFile.getMember().getName();

//        log.info("fileName = {}", findFile.getFileName());

        String filePath = "src/main/java/com/example/onlineide/userprojectfile/"
                + memberName + "/" + findFile.getFileName() + "/";// 새로 생성될 파일경로


        findFile.setCode(new Code(code.getCode(), code.getLanguage()));

        if (code.getCode().equals("js")){
            findFile.setLangStatus(LangStatus.JavaScript);
        }
        else{
            findFile.setLangStatus(LangStatus.Python);
        }

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
