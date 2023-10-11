package llustmarket.artmarket.web.controller.order;

import llustmarket.artmarket.web.dto.order.OrderPayDTO;
import llustmarket.artmarket.web.dto.order.OrderStatusDTO;
import llustmarket.artmarket.web.dto.order.SearchOrderDTO;
import llustmarket.artmarket.web.dto.payment.KakaoReadyResponse;
import llustmarket.artmarket.web.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 결제요청 처리.
     * 주문 정보 입력, 카카오페이 결제 준비 요청, 결과 저장
     *
     * @param orderPayDTO 주문 정보
     * @return KakaoReadyResponse
     */
    @PostMapping("/ready")
    public KakaoReadyResponse orderReady(@RequestBody @Valid OrderPayDTO orderPayDTO, HttpServletRequest request) {
        return orderService.doOrderReady(orderPayDTO, request);
    }


    //작가 회원
    @GetMapping("/mypage-orderAuthor/{member_id}") //멤버아이디 넣어서 시도해야함
    public ResponseEntity getOrderAuthorList(@PathVariable(value = "member_id") String memberId) {

        return new ResponseEntity<>(orderService.getOrderAuthorList(memberId), HttpStatus.OK);
    }

    //일반 회원
    @GetMapping("/mypage-orderMember/{member_id}") //멤버아이디 넣어서 시도해야함
    public ResponseEntity getOrderMemberList(@PathVariable(value = "member_id") String memberId) {

        return new ResponseEntity<>(orderService.getOrderMemberList(memberId), HttpStatus.OK);
    }

    //판매자의 검색 기능
    @GetMapping("/mypage-searchAuthor")
    public ResponseEntity SearchAuthor(SearchOrderDTO searchOrderDTO) {
        return new ResponseEntity<>(orderService.orderSearchAuthor(searchOrderDTO), HttpStatus.OK);
    }

    //구매자의 검색 기능
    @GetMapping("/mypage-searchMember")
    public ResponseEntity SearchMember(SearchOrderDTO searchOrderDTO) {
        return new ResponseEntity<>(orderService.orderSearchMember(searchOrderDTO), HttpStatus.OK);
    }


    //주문상태 변환
    @PostMapping("/order-status")
    public void changeOrderStatus(@RequestBody OrderPayDTO orderPayDTO) {

        orderService.OrderStatus(orderPayDTO);
    }

    @GetMapping("/count")
    public ResponseEntity getCountOrderStatus(OrderStatusDTO orderStatusDTO) {
        return new ResponseEntity<>(orderService.countOrderStatus(orderStatusDTO), HttpStatus.OK);

    }

}
