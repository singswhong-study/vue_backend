package com.study.vue_backend.service;


import com.study.vue_backend.Exception.MemberNotFoundException;
import com.study.vue_backend.entity.Member;
import com.study.vue_backend.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Member getMember(String email, String password){
        return memberRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new MemberNotFoundException("대상이 존재하지 않습니다."));
    }
}
