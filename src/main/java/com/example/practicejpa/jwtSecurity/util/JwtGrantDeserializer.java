package com.example.practicejpa.jwtSecurity.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

public class JwtGrantDeserializer extends StdDeserializer<GrantedAuthority> {
	
	public JwtGrantDeserializer(){
		this(null);
	}
	
	protected JwtGrantDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public GrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String val = p.getValueAsString();
		return val != null? new SimpleGrantedAuthority(val):null;
	}
}
