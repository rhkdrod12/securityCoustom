package com.example.practicejpa.jwtSecurity.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;


public class JwtGrantSerializer extends StdSerializer<GrantedAuthority> {
	public JwtGrantSerializer(){
		super(GrantedAuthority.class);
	}
	
	public JwtGrantSerializer(Class<GrantedAuthority> t) {
		super(t);
	}
	
	public JwtGrantSerializer(JavaType type) {
		super(type);
	}
	
	public JwtGrantSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}
	
	public JwtGrantSerializer(StdSerializer<?> src) {
		super(src);
	}
	
	@Override
	public void serialize(GrantedAuthority value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getAuthority());
	}
}
