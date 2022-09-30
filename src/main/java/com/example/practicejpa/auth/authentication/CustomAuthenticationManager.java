package com.example.practicejpa.auth.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationManager implements AuthenticationManager {
    
    AuthenticationProvider provider;
    
    public CustomAuthenticationManager(AuthenticationProvider provider) {
        this.provider = provider;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return this.provider.authenticate(authentication);
    }
}
