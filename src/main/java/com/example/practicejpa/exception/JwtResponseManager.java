package com.example.practicejpa.exception;

import com.example.practicejpa.utils.codeMessage.SystemMessage;
import com.example.practicejpa.utils.responseEntity.CommResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class JwtResponseManager {
	@Autowired
	private ObjectMapper objectMapper;
	private boolean responseData = false;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public static String JWT_RESPONSE_YN = "JWT_RESPONSE_YN";
	
	public void init(){
		responseData = false;
	}
	public boolean isResponseData(){
		return responseData;
	}
	
	public void setHttpServlet(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	public void responseJson(HttpServletRequest request, HttpServletResponse response, ResponseEntity<?> responseEntity)  {
		
		if(!responseData){
			// 응답여부 확인 셋팅
			responseData = true;
			
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
			// response.setHeader("Access-Control-Expose-Headers", "Content-Disposition, Authorization");
			response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Origin");
			
			response.setStatus(responseEntity.getStatusCodeValue());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			
			try {
				PrintWriter writer = response.getWriter();
				writer.write(objectMapper.writeValueAsString(responseEntity.getBody()));
			} catch (Exception e ) {
				throw new GlobalException(SystemMessage.REQUEST_FAIL);
			}
		}
	}
	
	public void sendResponse(ResponseEntity<?> responseEntity){
		this.responseJson(this.request, this.response, responseEntity);
	}
	
	public void sendData(Object result) {
		this.responseJson(this.request, this.response, CommResponse.done(result));
	}
	
	public void sendError(GlobalException exception){
		this.responseJson(this.request, this.response, CommResponse.fail(exception));
	}
	
	public void sendError(HttpServletRequest request, HttpServletResponse response, GlobalException exception){
		this.responseJson(request, response, CommResponse.fail(exception));
	}
	
}
