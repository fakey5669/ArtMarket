package llustmarket.artmarket.web.dto.payment;

import lombok.Data;

@Data
public class RefundDTO {
    private String orderId; // 의뢰(주문)번호
    private Long memberId;

}
