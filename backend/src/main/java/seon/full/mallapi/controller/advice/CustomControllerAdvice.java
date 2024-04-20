package seon.full.mallapi.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import seon.full.mallapi.util.CustomJWTException;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 예외를 받아서 한꺼번에 처리하는 Controller ( Custom)
 */
@RestControllerAdvice
public class CustomControllerAdvice {

    /**
     * 없는 데이터 조회시
     */
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<?> notExist(NoSuchElementException e) {
        String msg = e.getMessage();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", msg));
    }

    /**
     * 타입에 맞지 않는 데이터가 들어왔을 때
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handlerIllegalArgumentException(MethodArgumentNotValidException e) {
        String msg = e.getMessage();

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", msg));
    }

    /**
     * JWT Token 재발급 시
     */
    @ExceptionHandler(CustomJWTException.class)
    protected ResponseEntity<?> handleJWTException (CustomJWTException e) {
        String msg = e.getMessage();

        return ResponseEntity.ok().body(Map.of("error",msg));
    }
}
