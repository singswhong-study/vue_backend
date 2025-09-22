package com.study.vue_backend.controller;


import com.study.vue_backend.dto.LoginRequset;
import com.study.vue_backend.dto.LoginResponse;
import com.study.vue_backend.entity.Member;
import com.study.vue_backend.serviceImpl.MemberServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getToken(LoginRequset loginRequset, HttpServletResponse res ){

        logger.info("loginRequset => " + loginRequset.toString());

        LoginResponse loginRes = memberServiceImpl.login(loginRequset);

        Cookie cookie = new Cookie("accessToken", loginRes.getAccessToken());
        cookie.setHttpOnly(true);   // js 로는 접근 X
        cookie.setPath("/");

        res.addCookie(cookie);

//        return new ResponseEntity<>(loginRes.getMemberId(), HttpStatus.OK);
        return ResponseEntity.ok(loginRes.getMember().getMember_id());
    }

    @PostMapping("/api/account/token")
    public ResponseEntity<?> getLoginToken(@RequestBody LoginRequset loginRequset, HttpServletResponse res ){

        logger.info("loginRequset => " + loginRequset.toString());

        LoginResponse loginRes = memberServiceImpl.login(loginRequset);

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", loginRes.getAccessToken())
                .httpOnly(true)
                .secure(false) //https 환경일때 true
                .path("/")
                .maxAge(300) //초단위
                .sameSite("Lax")
                .build();
        logger.info("ACCESS TOKEN > " + loginRes.getAccessToken());

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", loginRes.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 24 * 7) // 7일
                .sameSite("Lax")
                .build();
        logger.info("REFRESH TOKEN > " + loginRes.getRefreshToken());


        res.addHeader("Set-Cookie", accessTokenCookie.toString());
        res.addHeader("Set-Cookie", refreshTokenCookie.toString());

        return ResponseEntity.ok(loginRes.getMember());
    }

//    @PostMapping("/api/account/valid-token")
//    public ResponseEntity<?> validToken(@CookieValue(name = "refreshToken") String refreshToken,    //이런식으로도 바로 가져올 수 있고
//                                        HttpServletRequest req,
//                                        HttpServletResponse res){
//
//
//        // 이렇게 가져올 수도 있다.
//        Cookie[] cookies = req.getCookies();
//        String accessToken = null;
//
//        if(cookies != null){
//            for(Cookie cookie : cookies){
//                if("accessToken".equals(cookie.getName())){
//                    accessToken = cookie.getValue();
//                }
//            }
//        }
//
//        return ResponseEntity.ok(memberServiceImpl.validToken(accessToken, refreshToken));
//    }

}
