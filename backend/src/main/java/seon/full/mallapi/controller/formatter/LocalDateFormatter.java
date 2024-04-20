package seon.full.mallapi.controller.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * LocalDateFormatter : 화면에서 원하는 데이터로 포맷팅
 *  Client(문자열) <=> Server (LocalDate , LocalDateTime)
 *  다음의 포매팅은 Spring MVC Config를 작성해야한다.
 */
public class LocalDateFormatter implements Formatter<LocalDate> {

    /**
     * Client --> Server 전송 데이터 파싱
     */
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Server -> Client 내려주는 데이터 타입 명시
     */
    @Override
    public String print(LocalDate object, Locale locale) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
    }
}
