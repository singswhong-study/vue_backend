package com.study.vue_backend.interceptor;

import com.study.vue_backend.entity.Member;
import com.study.vue_backend.security.JwtProvider;
import com.study.vue_backend.serviceImpl.MemberServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;
    private final MemberServiceImpl memberService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //토큰체크 예외처리 화이트리스트
    private static final List<String> WHITE_LIST = List.of(
            "/api/account/login",
            "/api/account/token"
    );

    public JwtInterceptor(JwtProvider jwtProvider, MemberServiceImpl memberService){
        this.jwtProvider = jwtProvider;
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res,
                             Object handler) throws Exception {

        logger.info(">>>>>>>>>>>>>> JWT INTERCEPTOR START <<<<<<<<<<<<<<<");

        String path = req.getRequestURI();

        for(String url : WHITE_LIST){
            if(path.startsWith(url)){
                logger.info(">>>>>>>>>>>>>> WHITE LIST. END <<<<<<<<<<<<<<<");
                return true;
            }
        }

        String accessToken = null;
        String refreshToken = null;

        if(req.getCookies() != null){
            for(Cookie cookie : req.getCookies()){
                if ("accessToken".equals(cookie.getName())) accessToken = cookie.getValue();
                if ("refreshToken".equals(cookie.getName())) refreshToken = cookie.getValue();
            }
        }

        if(accessToken != null && jwtProvider.validateToken(accessToken)){
            logger.info(">>>>>>>>>>>>>> ACCESSTOKEN NOT EXPIRED. END <<<<<<<<<<<<<<<");
            return true;
        }

        logger.info(">>>>>>>>>>>>>> ACCESSTOKEN EXPIRED. <<<<<<<<<<<<<<<");

        if(refreshToken != null && jwtProvider.validateToken(refreshToken)){
            logger.info(">>>>>>>>>>>>>> REFRESH TOKEN NOT EXPIRED <<<<<<<<<<<<<<<");
            logger.info(">>>>>>>>>>>>>> ACCESS TOKEN REGENERATE <<<<<<<<<<<<<<<");
            String email = jwtProvider.getClaimsFromToken(refreshToken).get("email").toString();
            Member m = memberService.getMemberByEmail(email);

            String newAccessToken = jwtProvider.generateAccessToken(m.getMember_id(), m.getEmail(), m.getName());

            ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(5 * 60)
                    .sameSite("Lax")
                    .build();

            res.addHeader("Set-Cookie", accessCookie.toString());
            return true;
        }

        logger.info(">>>>>>>>>>>>>> REFRESH TOKEN EXPIRED. END <<<<<<<<<<<<<<<");

        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write("로그인이 필요합니다.");
        return false;
    }

}
