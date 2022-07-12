package com.example.onlineide.controller;


import com.example.onlineide.domain.Member;
import com.example.onlineide.dto.LoginForm;
import com.example.onlineide.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        log.info("home:{}", "home-controller");

        return "home";
    }

    @GetMapping("/signIn")
    public String login(){
        return "/login/signIn";
    }

    @PostMapping("/signIn")
    public String loginMember(@ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request){

        HttpSession session = request.getSession(false);


        if (bindingResult.hasErrors()) {
            return "/login/signIn";
        }

        Member findMember = memberService.findMember(loginForm.getId());
        if (findMember == null){
            throw new IllegalStateException("not exit ID");
        }

        if (!(findMember.getPassword()).equals(loginForm.getPassWord())){
            throw new IllegalStateException("password is not match");
        }

        session.setAttribute("member", loginForm);

        return "redirect:/ide";
    }
}
