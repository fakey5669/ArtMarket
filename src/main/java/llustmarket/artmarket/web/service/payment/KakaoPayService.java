package llustmarket.artmarket.web.service.payment;

import llustmarket.artmarket.web.dto.order.OrderPayDTO;
import llustmarket.artmarket.web.dto.payment.*;
import llustmarket.artmarket.web.mapper.order.OrderMapper;
import llustmarket.artmarket.web.mapper.payment.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {

    private final OrderMapper orderMapper; // 저장할 db
    private final PaymentMapper paymentMapper; // 저장할 db
    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    static final String admin_Key = "800714ee8a4c1870bacddb473506cd71"; // 공개 조심! 본인 애플리케이션의 어드민 키를 넣어주세요
    private KakaoReadyResponse kakaoReady;

    //헤더 요청
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;

        httpHeaders.add("Authorization", auth);
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    public KakaoReadyResponse kakaoPayReady(OrderPayDTO orderPayDTO) {
        // 카카오페이 요청 양식
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        //MultiValueMap 키의 중복 허용 가능
        parameters.add("cid", cid); //가맹점 코드(test는 카카오에서 제공하는 샘플 코드)
        parameters.add("partner_order_id", orderPayDTO.getOrderId()); //주문번호
        parameters.add("partner_user_id", orderPayDTO.getNickname()); //주문자명
        parameters.add("item_name", orderPayDTO.getProductName()); //물품 이름
        parameters.add("quantity", 1); //수량
        parameters.add("total_amount", orderPayDTO.getTotalAmount()); //결제금액
        parameters.add("tax_free_amount", 0); //비과세

        parameters.add("approval_url", "http://localhost:8070/success-order"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8070/product/{product_id}"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8070/product/{product_id}"); // 실패 시 redirect url

        // 파라미터, http 헤더
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        //카카오 결제 요청 페이지 호출
        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponse.class);

        return kakaoReady;
    }


    // 결제 완료 승
    public KakaoApproveResponse ApproveResponse(String pgToken, HttpServletRequest request) {

        HttpSession session = request.getSession();
        String tid = (String) session.getAttribute("kakaoPaySession");
        PaymentDTO paymentDTO = paymentMapper.selectPayment(tid);
        // 카카오 요청
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", tid);
        parameters.add("partner_order_id", paymentDTO.getPartnerOrderId()); //주문번호
        parameters.add("partner_user_id", paymentDTO.getPartnerUserId()); //주문자명
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate(); //RestTemplate REST API 호출이후 응답을 받을 때까지 기다리는 동기방식이다

        KakaoApproveResponse approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponse.class);

        OrderPayDTO orderPayDTO = new OrderPayDTO();
        orderPayDTO.setOrderId(paymentDTO.getPartnerOrderId());
        orderPayDTO.setOrderStatus("결제 완료");
        orderMapper.updateOrderStatus(orderPayDTO);

        return approveResponse;
    }


    //결제 환불
    public KakaoCancelResponse kakaoCancel(RefundDTO refundDTO) {

        PaymentDTO pay = paymentMapper.selectPayCancel(refundDTO);
        // 카카오페이 요청
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", pay.getTid()); //tid 번호
        parameters.add("cancel_amount", pay.getTotalAmount());  //환불 금액
        parameters.add("cancel_tax_free_amount", 0); //비과세

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoCancelResponse cancelResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoCancelResponse.class);

        OrderPayDTO orderPayDTO = new OrderPayDTO();
        orderPayDTO.setOrderId(pay.getPartnerOrderId());
        orderPayDTO.setOrderStatus("환불 완료");
        orderMapper.updateOrderStatus(orderPayDTO);

        return cancelResponse;


    }
}
