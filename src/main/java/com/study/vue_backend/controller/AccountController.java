package com.study.vue_backend.controller;


import com.study.vue_backend.entity.Member;
import com.study.vue_backend.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MemberService memberService;

    public AccountController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/api/account/login")
    public ResponseEntity<Member> getMember(@RequestParam String email,
                                            @RequestParam String password){

        logger.info("email => " + email);
        logger.info("password => " + password);
        return ResponseEntity.ok(memberService.getMember(email, password));

    }


}
