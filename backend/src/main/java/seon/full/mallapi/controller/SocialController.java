package seon.full.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seon.full.mallapi.service.MemberService;
import seon.full.mallapi.dto.MemberDTO;
import seon.full.mallapi.dto.MemberModifyDTO;
import seon.full.mallapi.util.JWTUtil;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SocialController {

    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken) {
        log.info("access Token");
        log.info(accessToken);

        MemberDTO memberDTO = memberService.getKakakoMember(accessToken);

        Map<String, Object> claims = memberDTO.getClaims();

        //Access / Refresh 각각 정보를 가지고 있는게 맞는지, 이를 한꺼번에 전달하는게 맞는지 알아보는 과정 필요
        String jwtAccessToken = JWTUtil.generateToken(claims, 10);
        String jwtRefreshToken = JWTUtil.generateToken(claims, 60*24);

        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);

        return claims;
    }

    @PutMapping("/api/member/modify")
    public Map<String, String> modify (@RequestBody MemberModifyDTO memberModifyDTO){
        log.info("member modify:" + memberModifyDTO);

        memberService.modifyMember(memberModifyDTO);

        return Map.of("result", "modified");
    }
}
