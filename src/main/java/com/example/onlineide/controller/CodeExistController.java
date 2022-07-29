package com.example.onlineide.controller;


import com.example.onlineide.domain.Member;
import com.example.onlineide.domain.UserFile;
import com.example.onlineide.dto.CreateProjectFormDto;
import com.example.onlineide.service.MemberService;
import com.example.onlineide.service.UserFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CodeExistController {
    private final UserFileService userFileService;
    private final MemberService memberService;


    @GetMapping("/{memberId}/list")
    public String memberOwnList(@PathVariable String memberId, Model model){

        log.info("memberId = {}", memberId);

        List<UserFile> files = userFileService.getAllFiles(memberId);

        System.out.println("=====here=====");

        for (UserFile file : files) {
            log.info("file.getFileName() = {}",file.getFileName());
        }

        model.addAttribute("memberFiles", files);
        model.addAttribute("memberId", memberId);
        return "exist/exist-file";
    }

    @GetMapping("/{memberId}/new-project")
    public String createNewProject(@PathVariable String memberId, Model model){
        CreateProjectFormDto form = new CreateProjectFormDto();
        form.setMemberId(memberId);
        model.addAttribute("createProjectForm", form);
        return "exist/createProject";
    }


    /**
     * post받고 ide서비스 이용
     * */
    @PostMapping("/{memberId}/new-project")
    public String receiveCreateNewProject(@ModelAttribute CreateProjectFormDto form, Model model,
                                          RedirectAttributes redirectAttributes){
        String memberId = form.getMemberId();
        Member findMember = memberService.findMember(memberId);

        String projectName = form.getProjectName();
        redirectAttributes.addAttribute("projectName",projectName);

        log.info("name = {}", projectName);

        UserFile userFile = new UserFile();
        userFile.setFileName(projectName);
        userFile.setMember(findMember);
        userFile.registerProject();

        userFileService.save(userFile);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "redirect:{memberId}/{projectName}/ide";
    }

}
