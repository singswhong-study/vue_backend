package com.study.vue_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponse {
    private Long memberId;
    private String token;

    public LoginResponse(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }
}
