package seon.full.mallapi.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import seon.full.mallapi.dto.MemberDTO;
import seon.full.mallapi.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 모든 요청에 대해 체크
 */
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {


    /**
     * 필터가 체크하지 않을 경로,메소드 지정
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {


        // Preflight 요청은 체크하지 않는다
        if(request.getMethod().equals("OPTIONS")){return true;}

        String path = request.getRequestURI();
        log.info("check uri........" + path);
        
        //api/member 호출은 체크하지 않는다
        if(path.startsWith("/api/member")){
            return true;
        }
        //이미지 조회 경로는 체크하지 않는다
        if(path.startsWith("/api/products/view/")){
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("---------JWT Check Filter--------------");

        String authHeaderStr = request.getHeader("Authorization");

        try {
            //Accesss Token 확인
            String accessToken = authHeaderStr.substring(7);

            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: " + claims);



            String email = (String) claims.get("email");
            String pw = (String) claims.get("pw");
            String nickname = (String) claims.get("nickname");
            boolean social = (boolean) claims.get("social");
            List<String> roleNames = (List<String>) claims.get("roleNames");

            MemberDTO memberDTO = new MemberDTO(email, pw, nickname, social, roleNames);

            log.info("-------------------------");
            log.info(memberDTO);
            log.info(memberDTO.getAuthorities());

            //인가를 위함
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberDTO, pw, memberDTO.getAuthorities());

            //이 후 로직에서 인증내용을 사용하기 위함
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }catch (Exception e){
            //Access Token 만료 시
            log.error("-------JWT Check Error-------");

            log.error(e.getMessage());

            Gson gson = new Gson();

            String jsonStr = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(jsonStr);
            printWriter.close();
        }
    }
    
}
