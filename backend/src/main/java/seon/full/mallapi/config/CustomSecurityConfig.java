package seon.full.mallapi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import seon.full.mallapi.security.filter.JWTCheckFilter;
import seon.full.mallapi.security.handler.APILoginFailHandler;
import seon.full.mallapi.security.handler.APILoginSuccessHandler;
import seon.full.mallapi.security.handler.CustomAccessDeniedHandler;

import java.util.Arrays;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableMethodSecurity
public class CustomSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        log.info("=====Security config=====");


        // Cors 설정
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        //Session 생성 X => 무상태를 유지하기 위해서
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //CSRF
        http.csrf(config -> config.disable());

        // API 서버로 로그인 할 수 있도록 설정 변경 ( POST -> username , password 파라미터 필요 )
        http.formLogin(config -> {
            config.loginPage("/api/member/login");
            config.successHandler(new APILoginSuccessHandler());
            config.failureHandler(new APILoginFailHandler());
        });

        http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class); //JWT 체크

        //인가(권한) 에러처리
        http.exceptionHandling(config -> {
            config.accessDeniedHandler(new CustomAccessDeniedHandler());
        });
        
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 모든 Cross Origin 허용
        configuration.setAllowedMethods(Arrays.asList("HEAD","GET","POST","PUT","DELETE")); // Http Method 허용
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type")); //
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }


}
