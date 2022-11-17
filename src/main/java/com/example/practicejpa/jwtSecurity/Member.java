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
public class Member {
    
    private String userId;
    private String userPw;
    private String name;
    
    // @JsonSerialize(contentUsing = JwtGrantSerializer.class)
    // @JsonDeserialize(contentUsing = JwtGrantDeserializer.class)
    private Set<String> auths;
    
    private JWTResult token;
}
