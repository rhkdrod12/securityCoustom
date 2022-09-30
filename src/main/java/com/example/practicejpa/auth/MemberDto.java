package com.example.practicejpa.auth;

import com.example.practicejpa.utils.Json.CustomJsonDeserializer;
import com.example.practicejpa.utils.Json.CustomJsonSerializer;
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
public class MemberDto {
    private String userId;
    private String userPw;
    private String name;
    private int age;
    
    @JsonSerialize(contentUsing = CustomJsonSerializer.class)
    @JsonDeserialize(contentUsing = CustomJsonDeserializer.class)
    private Set<GrantedAuthority> auths;
}
