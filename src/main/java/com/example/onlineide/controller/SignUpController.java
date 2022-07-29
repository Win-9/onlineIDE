package com.example.onlineide.controller;


import com.example.onlineide.domain.Address;
import com.example.onlineide.domain.Member;
import com.example.onlineide.dto.SignUpForm;
import com.example.onlineide.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignUpController {
    private final MemberService memberService;

    @GetMapping("/sign-up")
    public String signUp(Model model){

        model.addAttribute("signUpForm", new SignUpForm());
        return "login/signUp";
    }

    @PostMapping("/sign-up")
    public String createMember(@ModelAttribute SignUpForm signUpForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            return "error";
        }

        Member findMember = memberService.findMember(signUpForm.getId());
        if (findMember != null) {
            bindingResult.reject("signUp fail","duplicated ID");
            return "login/signUp";
        }
        Member newMember = new Member();

        Address address = new Address();
        address.setCity(signUpForm.getCity());
        address.setZipcode(signUpForm.getZipcode());
        address.setEtc((signUpForm.getEtc()));

        newMember.setAddress(address);
        newMember.setId(signUpForm.getId());
        newMember.setPassword(signUpForm.getPassword());
        newMember.setName(signUpForm.getName());
        newMember.setEmail(signUpForm.getEmail());

        memberService.join(newMember);

        return "redirect:/";
    }
}
