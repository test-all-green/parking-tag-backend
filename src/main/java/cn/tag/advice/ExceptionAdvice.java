package cn.tag.advice;

import cn.tag.exception.GlobalException;
import cn.tag.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = GlobalException.class)
    public ResponseEntity exceptionHandler(GlobalException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrMessage());
    }

    @ExceptionHandler(value = LoginException.class)
    public ResponseEntity loginExceptionHandler(LoginException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrMessage());
    }
}