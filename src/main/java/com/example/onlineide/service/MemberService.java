package com.example.onlineide.service;


import com.example.onlineide.domain.Member;
import com.example.onlineide.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String join(Member member){
        validateDuplicatedMember(member);
        memberRepository.saveMember(member);

        return member.getId();
    }

    private void validateDuplicatedMember(Member member) {
        Member findMember = memberRepository.findOne(member.getId());
        if (findMember != null) {
            throw new IllegalStateException("duplicated Member");
        }
    }

    public void login(Member member){
        Member findMember = memberRepository.findOne(member.getId());
        if (!findMember.getId().equals(member.getId())) {
            throw new IllegalStateException("not exit ID");
        }

        if (!findMember.equals(member.getPassword())){
            throw new IllegalStateException("password is not match");
        }
    }

}
