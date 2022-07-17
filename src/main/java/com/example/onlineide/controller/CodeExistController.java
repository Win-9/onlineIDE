package com.example.onlineide.controller;


import com.example.onlineide.domain.Member;
import com.example.onlineide.domain.UserFile;
import com.example.onlineide.dto.CreateProjectFormDto;
import com.example.onlineide.service.MemberService;
import com.example.onlineide.service.UserFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CodeExistController {
    private final UserFileService userFileService;
    private final MemberService memberService;


    @GetMapping("/{memberId}/list")
    public String memberOwnList(@PathVariable String memberId, Model model){
        List<UserFile> files = userFileService.getAllFiles(memberId);

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
    public String receiveCreateNewProject(@ModelAttribute CreateProjectFormDto form, Model model){
        String memberId = form.getMemberId();
        Member findMember = memberService.findMember(memberId);

        String projectName = form.getProjectName();

        UserFile userFile = new UserFile();
        userFile.setMember(findMember);
        userFile.setSubPath(projectName);
        userFile.registerProject();

        userFileService.save(userFile);

        return "redirect:{memberId}/ide";
    }

}
