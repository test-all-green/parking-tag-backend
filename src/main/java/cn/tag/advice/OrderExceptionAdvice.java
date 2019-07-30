package cn.tag.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class OrderExceptionAdvice {
    @ExceptionHandler(value = ObjectOptimisticLockingFailureException.class)
    public ResponseEntity concurrentException(Exception ex){
        Map map = new HashMap();
        map.put("code","401");
        map.put("massage","订单已被抢");
        map.put("errorMassage",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
}
