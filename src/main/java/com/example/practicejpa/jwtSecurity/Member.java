package com.example.practicejpa.jwtSecurity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private String userId;
    private String userPw;
    private String name;
    private Set<String> auths;
    private JWTResult token;
}
