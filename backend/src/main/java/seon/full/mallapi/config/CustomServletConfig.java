package seon.full.mallapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import seon.full.mallapi.controller.formatter.LocalDateFormatter;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    // Custom Format : 문자열 <-> LocalDate, LocalDateTime
    public void addFormatters(FormatterRegistry registry){
        registry.addFormatter(new LocalDateFormatter());
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
////                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedMethods(CorsConfiguration.ALL)
//                .maxAge(300)
//                .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
//
//    }
}
