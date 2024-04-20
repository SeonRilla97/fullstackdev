package seon.full.mallapi.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Embeddable // Element Collection 값 타입 객체임을 명시
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

    private String fileName;
    private int ord; // 해당 파일의 순서를 명시하여 대표 이미지를 출력하고자 할 때 사용

    public void setOrd(int ord) {
        this.ord = ord;
    }
}
