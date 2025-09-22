package com.study.vue_backend.dto;

import com.study.vue_backend.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponse {
    private Member member;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(Member member, String accessToken, String refreshToken) {
        this.member = member;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
