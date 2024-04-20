package seon.full.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import seon.full.mallapi.domain.Member;
import seon.full.mallapi.domain.MemberRole;
import seon.full.mallapi.dto.MemberDTO;
import seon.full.mallapi.dto.MemberModifyDTO;
import seon.full.mallapi.repository.MemberRepository;

import java.util.LinkedHashMap;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO getKakakoMember(String accessToken) {
        String email = getEmailFromKakaoAccessToken(accessToken);

        log.info("email : " + email);

        Optional<Member> result = memberRepository.findById(email);

        if(result.isPresent()){
            //회원이라면
            MemberDTO memberDTO = entityToDTO(result.get());
            return memberDTO;
        }

        //비회원
        Member socialMember = makeSocialMember(email);

        memberRepository.save(socialMember);

        MemberDTO memberDTO = entityToDTO(socialMember);
        return memberDTO;
    }

    @Override
    public void modifyMember(MemberModifyDTO memberModifyDTO) {
        Optional<Member> result = memberRepository.findById(memberModifyDTO.getEmail());

        Member member = result.orElseThrow();

        member.changeNickname(memberModifyDTO.getNickname());
        member.changeSocial(false);
        member.changePw(passwordEncoder.encode(memberModifyDTO.getPw()));

        memberRepository.save(member);
    }

    private static String getEmailFromKakaoAccessToken(String accessToken) {

        //KAKAO 유저 정보 받아오는 URL
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        //AccessToken 받지 않았으면 예외처리
        if(accessToken == null) {
            throw new RuntimeException("Access Token is null");
        }

        //Spring(Backend) 에서 API 호출 [ 동기식 요청 ] 
        RestTemplate restTemplate = new RestTemplate();

        //Http Header 선언
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization","Bearer " +accessToken );
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //Http Entity
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        //요청 URI 설정
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();

        //api 호출
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(uriBuilder.toString(), HttpMethod.GET, entity, LinkedHashMap.class);

        log.info(response);

        //응답으로 부터 Body 추출
        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        log.info("-----------------------------");
        log.info(bodyMap);

        //바디에 있는 kakao_account 추출
        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");

        log.info("kakaoAccount: " + kakaoAccount);

        //email값 Return
        return kakaoAccount.get("email");

    }

    /**
     * 난수 발생기 [소셜 회원가입 비밀번호 랜덤 생성]
     * @return
     */
    private String makeTempPassword() {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < 10; i++) {
            buffer.append((char)( (int)(Math.random()*55) + 65));
        }
        return buffer.toString();
    }

    /**
     * 소셜 계정 신규 계정 생성
     * @param email
     * @return
     */
    private Member makeSocialMember (String email) {
        String tempPassword = makeTempPassword();

        log.info("tempPassword: " + tempPassword);

        String nickname = "소셜회원";

        Member member = Member.builder()
                .email(email)
                .pw(passwordEncoder.encode(tempPassword))
                .nickname(nickname)
                .social(true)
                .build();

        member.addRole(MemberRole.USER);

        return member;
    }
}
