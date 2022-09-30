package com.example.practicejpa.utils.Json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

public class CustomJsonDeserializer extends StdDeserializer<GrantedAuthority> {
	
	public CustomJsonDeserializer(){
		this(null);
	}
	
	protected CustomJsonDeserializer(Class<?> vc) {
		super(vc);
	}
	
	@Override
	public GrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String val = p.getValueAsString();
		return val != null? new SimpleGrantedAuthority(val):null;
	}
}
