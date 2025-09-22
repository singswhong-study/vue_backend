package com.study.vue_backend.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginRequset {
    private String email;
    private String password;

    public LoginRequset(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
