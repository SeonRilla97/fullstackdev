package seon.full.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seon.full.mallapi.util.CustomJWTException;
import seon.full.mallapi.util.JWTUtil;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {


    @RequestMapping("/api/member/refresh")
    public Map<String, Object> refresh(@RequestHeader("Authorization")String authHeader, String refreshToken){
        //RefreshToken이 없으면 예외
        if(refreshToken == null){
            throw new CustomJWTException("NULL_REFRESH");
        }
        // 유효하지 않은 JWT일 경우 예외
        if(authHeader == null || authHeader.length()< 7) {
            throw  new CustomJWTException("INVALID_STRING");
        }

        String accessToken = authHeader.substring(7);

        // AccessToken Expired 여부 확인
        if(checkExpiredToken(accessToken) == false){
            return Map.of("accessToken",accessToken,"refreshToken",refreshToken);
        }

        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);

        log.info("refresh ... claims " + claims);

        // 10분 간격 AccessToken 재발급
        String newAccessToken = JWTUtil.generateToken(claims, 10);

        // RefreshToken 만료 1시간 전이면 재발급 , 아니면 기존것 유지
        String newRefreshToken = checkTime((Integer) claims.get("exp")) == true ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;

        return Map.of("accessToken",newAccessToken,"refreshToken",newRefreshToken);

    }

    //1시간 이하로 남았을 경우 Refresh 하도록
    private boolean checkTime (Integer exp) {
        Date expDate = new Date((long) exp * (1000));

        long gap = expDate.getTime() - System.currentTimeMillis();

        long leftMin = gap / (1000 * 60);

        return leftMin < 60;
    }
    //Token 만료 여부 확인
    private boolean checkExpiredToken(String token) {
        try{
            JWTUtil.validateToken(token);
        }catch (CustomJWTException ex){
            if (ex.getMessage().equals("Expired")){
            return true;
            }
        }
        return true;
    }

}

