package llustmarket.artmarket.web.dto.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//결제 취소 요청 시 사용

@Getter
@Setter
@ToString
public class KakaoCancelResponse {
    private String tid; // 결제 고유 번호
    private String cid; // 가맹점 코드
    private String status; // 결제 상태
    private String partner_order_id; // 가맹점 주문 번호
    private String partner_user_id; // 가맹점 회원 ID
    private Amount amount; // 결제 금액 정보
    private String item_name; // 상품 이름
    private String created_at; // 결제 준비 요청 시각
    private String approved_at; // 결제 승인 시각
    private String canceled_at; // 결제 취소 시각

}
