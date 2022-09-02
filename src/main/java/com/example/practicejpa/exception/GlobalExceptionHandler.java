package com.example.practicejpa.exception;

import com.example.practicejpa.utils.responseEntity.ResultMessage;
import com.example.practicejpa.utils.codeMessage.FileFailMessage;
import com.example.practicejpa.utils.codeMessage.SystemMessage;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({
    // 최대 파일 용량 초과 에러
    MaxUploadSizeExceededException.class,
    // 파일이 용량 제한 에러
    SizeLimitExceededException.class,
    })
    public ResponseEntity<ResultMessage> fileUploadMaxSizeOverException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultMessage.messageCode(FileFailMessage.FILE_MAX_OVER_SIZE));
    }
   
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ResultMessage> handleAccessDeniedException(Exception ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResultMessage.fail(ex.getMessage()));
        //return CommResponse.builder().status(HttpStatus.FORBIDDEN).message(ex.getMessage()).build();
    }
    
    @ExceptionHandler(value = GlobalException.class)
    public ResponseEntity<ResultMessage> handleException(GlobalException ex){
        return new ResponseEntity<>(ResultMessage.fail(ex.getMessage()), ex.getHttpStatus());
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResultMessage> exceptionTest(Exception e){
        return new ResponseEntity<>(ResultMessage.messageCode(SystemMessage.ERROR_REQUEST_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

