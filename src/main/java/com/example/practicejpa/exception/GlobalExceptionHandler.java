package com.example.practicejpa.exception;

import com.example.practicejpa.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice()
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseDto<?> handleAccessDeniedException(Exception ex, WebRequest request){
        return ResponseDto.builder().status(HttpStatus.FORBIDDEN).message(ex.getMessage()).build();
    }
    
    @ExceptionHandler(value = GlobalException.class)
    public ResponseDto<?> handleException(GlobalException ex){
        return ResponseDto.builder().status(ex.getHttpStatus()).message(ex.getMessage()).build();
    }
    
    @ExceptionHandler(value = Exception.class)
    public String exceptionTest(Exception e){
        return "오류가 발생하였습니다.";
    }
}
