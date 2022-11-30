package com.example.practicejpa.jwtSecurity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {
    private String userId;
    private String name;
    private Set<String> auths;
}
