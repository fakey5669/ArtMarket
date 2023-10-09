package llustmarket.artmarket.web.dto.payment;

import lombok.Data;

@Data
public class PaymentDTO {
    private String tid;
    private String partnerOrderId;
    private String partnerUserId;
    private Long totalAmount;
}
