package seon.full.mallapi.util;

/**
 * JWT 관련 예외를 처리하기 위한 클래스
 */
public class CustomJWTException  extends RuntimeException{
    public CustomJWTException(String message) {
        super(message);
    }
}
