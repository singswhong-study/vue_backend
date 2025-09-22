package com.study.vue_backend.serviceImpl;


import com.study.vue_backend.Exception.MemberNotFoundException;
import com.study.vue_backend.dto.LoginRequset;
import com.study.vue_backend.dto.LoginResponse;
import com.study.vue_backend.entity.Member;
import com.study.vue_backend.repository.MemberRepository;
import com.study.vue_backend.security.JwtProvider;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;


    public MemberServiceImpl(MemberRepository memberRepository, JwtProvider jwtProvider){
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    public Member getMember(String email, String password){
        return memberRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new MemberNotFoundException("대상이 존재하지 않습니다."));
    }

    public Member getMemberByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberNotFoundException("대상이 존재하지 않습니다."));
    }

    public LoginResponse login(LoginRequset loginRequset){

        Member m = memberRepository.findByEmailAndPassword(loginRequset.getEmail(), loginRequset.getPassword()).orElseThrow(() -> new MemberNotFoundException("대상이 존재하지 않습니다."));
        String accessToken = jwtProvider.generateAccessToken(m.getMember_id(), m.getEmail(), m.getName());
        String refreshToken = jwtProvider.generateRefreshToken(m.getMember_id(),  m.getEmail(), m.getName());

        return new LoginResponse(m, accessToken, refreshToken);
    }

    public LoginResponse validToken(String accessToken, String refreshToken){
        return null;
    }
}
