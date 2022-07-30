package com.example.onlineide.api.member;


import com.example.onlineide.domain.Member;
import com.example.onlineide.dto.MemberDto;
import com.example.onlineide.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    @GetMapping("/api/members/{id}")
    public MemberDto memberApi(@PathVariable String id){
        log.info("memberApiController");
        Member member = memberService.findMember(id);


        if (member == null) {
            throw new RuntimeException("Not Exist Member");
        }

        return new MemberDto(member.getId(), member.getPassword(), member.getName(), member.getAddress(), member.getEmail());
    }

    @GetMapping("/api/members")
    public String members(){
        return "hi";
    }
}
