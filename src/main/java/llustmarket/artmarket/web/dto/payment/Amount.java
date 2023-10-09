package llustmarket.artmarket.web.dto.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


// 총 금액

@Getter
@Setter
@ToString

public class Amount {
    private int total; // 취소된 전체 누적 금액
    private int tax_free; // 취소된 비과세 누적 금액
}
