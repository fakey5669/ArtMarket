package llustmarket.artmarket.web.controller.payment;


import llustmarket.artmarket.web.dto.payment.KakaoApproveResponse;
import llustmarket.artmarket.web.dto.payment.KakaoCancelResponse;
import llustmarket.artmarket.web.dto.payment.RefundDTO;
import llustmarket.artmarket.web.service.payment.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequiredArgsConstructor //final 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Log4j2
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;


    //결제 성공
    @GetMapping("/success-order")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken, HttpServletRequest request) {
        KakaoApproveResponse kakaoApprove = kakaoPayService.ApproveResponse(pgToken, request);
        return new ResponseEntity<>(kakaoApprove, HttpStatus.CREATED);
    }


    //결제 진행 중 취소 및 결제 실패
    @GetMapping("/product/{product_id}")
    public String cancel() {

        return "redirect:/product"; //상품페이지로 돌아가게
    }

    //환불
    @PostMapping("/refund")
    public ResponseEntity refund(@RequestBody RefundDTO refundDTO) {
        KakaoCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel(refundDTO);
        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }
}

