package llustmarket.artmarket.web.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchOrderDTO {
    private String orderId; // 의뢰(주문)번호
    private String category;
    private String nickname; // 구매자 닉네임
    private Long productId;
    private Long memberId;
    private Date orderDate; //주문 날짜
    private Long totalAmount; //결제 금액
    private Date deadline; // 마감 기한
    private String orderStatus; //주문 상태
    private String StartDate;
    private String endDate;


}
