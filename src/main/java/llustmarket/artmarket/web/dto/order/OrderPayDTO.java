package llustmarket.artmarket.web.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPayDTO {

    private String orderId; // 의뢰(주문)번호
    private String nickname; // 구매자 닉네임
    private Long productId;
    private Long memberId;
    private String productName; //상품 이름
    private Date orderDate; //주문 날짜
    @NotNull
    private Long totalAmount; //결제 금액
    private int quantity; // 수량
    @NotNull
    private LocalDateTime deadline; // 마감 기한
    private String orderStatus; //주문 상태

}
