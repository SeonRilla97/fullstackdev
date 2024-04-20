package seon.full.mallapi.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString(exclude = "owner")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "tbl_cart",
        indexes = {@Index(name="idx_cart_email", columnList = "member_owner")}
)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    @OneToOne
    @JoinColumn(name = "member_owner")
    private Member owner;

}
