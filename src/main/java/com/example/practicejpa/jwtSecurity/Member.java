package com.example.practicejpa.jwtSecurity;

import com.example.practicejpa.jwtSecurity.util.JwtGrantDeserializer;
import com.example.practicejpa.jwtSecurity.util.JwtGrantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    
    private String userId;
    private String userPw;
    private String name;
    private int age;
    
    @JsonSerialize(contentUsing = JwtGrantSerializer.class)
    @JsonDeserialize(contentUsing = JwtGrantDeserializer.class)
    private Set<GrantedAuthority> auths;
    
    private JWTResult token;
}
