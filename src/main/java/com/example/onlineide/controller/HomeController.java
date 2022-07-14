package com.example.onlineide.controller;


import com.example.onlineide.domain.Member;
import com.example.onlineide.dto.LoginForm;
import com.example.onlineide.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/sign-in")
    public String login(Model model){
        model.addAttribute("loginForm",new LoginForm());
        return "/login/signIn";
    }

    @PostMapping("/sign-in")
    public String loginMember(@ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            return "/login/signIn";
        }

        log.info("loginFormId = {}", loginForm.getId());
        /**
         * 로그인 실패
         */
        Member loginMember = memberService.findMember(loginForm.getId());
        if (loginMember == null){
            bindingResult.reject("login fail","ID doesn't exit");
            return "/login/signIn";
        }

        if (!(loginMember.getPassword()).equals(loginForm.getPassword())){
            bindingResult.reject("login fail","password doesn't match");
            return "/login/signIn";
        }

        /**
         * 로그인 성공
         */
        HttpSession session = request.getSession(true); //세션이 존재하지 않으면 신규세션 생성
        session.setMaxInactiveInterval(600);
        session.setAttribute("member", loginMember);


        return "redirect:/ide"; // 성공시 ide 이용
    }
}
