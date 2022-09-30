package com.example.practicejpa.utils.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;


public class CustomJsonSerializer extends StdSerializer<GrantedAuthority> {
	public CustomJsonSerializer(){
		super(GrantedAuthority.class);
	}
	
	public CustomJsonSerializer(Class<GrantedAuthority> t) {
		super(t);
	}
	
	public CustomJsonSerializer(JavaType type) {
		super(type);
	}
	
	public CustomJsonSerializer(Class<?> t, boolean dummy) {
		super(t, dummy);
	}
	
	public CustomJsonSerializer(StdSerializer<?> src) {
		super(src);
	}
	
	@Override
	public void serialize(GrantedAuthority value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(value.getAuthority());
	}
}
