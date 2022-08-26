package com.example.practicejpa.exception;

import com.example.practicejpa.dto.ResponseDto;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    static final String DEFAULT_FAIL_MESSAGE = "처리 중 오류가 발생하였습니다";
   
    @ExceptionHandler({
    // 최대 파일 용량 초과 에러
    MaxUploadSizeExceededException.class,
    // 파일이 용량 제한 에러
    SizeLimitExceededException.class,
    })
    public ResponseEntity<?> fileUploadMaxSizeOverException(Exception e){
        return new ResponseEntity<String>("허용 용량을 초과된 파일입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
   
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDeniedException(Exception ex, WebRequest request){
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.FORBIDDEN);
        //return ResponseDto.builder().status(HttpStatus.FORBIDDEN).message(ex.getMessage()).build();
    }
    
    @ExceptionHandler(value = GlobalException.class)
    public ResponseEntity<?> handleException(GlobalException ex){
        return new ResponseEntity<String>(ex.getMessage(), ex.getHttpStatus());
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exceptionTest(Exception e){
        return new ResponseEntity<String>(DEFAULT_FAIL_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

