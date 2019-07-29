package cn.tag.advice;

import cn.tag.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity errorHandle(Exception ex){
        Map map = new HashMap();
        map.put("code",100);
        map.put("message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity customHandle(CustomException ex){
        Map map = new HashMap();
        map.put("code",ex.getCode());
        map.put("message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
       // return map;
    }
}
