package llustmarket.artmarket.web.mapper.payment;

import llustmarket.artmarket.web.dto.payment.PaymentDTO;
import llustmarket.artmarket.web.dto.payment.RefundDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    void insertPayment(PaymentDTO paymentDTO);

    PaymentDTO selectPayment(String tid);

    PaymentDTO selectPayCancel(RefundDTO refundDTO);

}
