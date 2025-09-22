package com.study.vue_backend.controller;


import com.study.vue_backend.entity.Member;
import com.study.vue_backend.serviceImpl.MemberServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberServiceImpl memberServiceImpl;

    public AccountController(MemberServiceImpl memberServiceImpl){
        this.memberServiceImpl = memberServiceImpl;
    }

    @GetMapping("/api/account/login")
    public ResponseEntity<Member> getMember(@RequestParam String email,
                                            @RequestParam String password){

        logger.info("email => " + email);
        logger.info("password => " + password);
        return ResponseEntity.ok(memberServiceImpl.getMember(email, password));

    }

    @GetMapping("/api/account/token")
    public ResponseEntity<String> getToken(@RequestParam String email,
                                            @RequestParam String password){

        logger.info("email => " + email);
        logger.info("password => " + password);
        return ResponseEntity.ok(memberServiceImpl.login(email, password));

    }

}
