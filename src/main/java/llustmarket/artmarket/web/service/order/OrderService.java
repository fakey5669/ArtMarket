package llustmarket.artmarket.web.service.order;

import llustmarket.artmarket.domain.order.Order;
import llustmarket.artmarket.web.dto.board.AuthorDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.dto.order.OrderDTO;
import llustmarket.artmarket.web.dto.order.OrderPayDTO;
import llustmarket.artmarket.web.dto.order.OrderStatusDTO;
import llustmarket.artmarket.web.dto.order.SearchOrderDTO;
import llustmarket.artmarket.web.dto.payment.KakaoReadyResponse;
import llustmarket.artmarket.web.dto.payment.PaymentDTO;
import llustmarket.artmarket.web.mapper.order.OrderMapper;
import llustmarket.artmarket.web.mapper.payment.PaymentMapper;
import llustmarket.artmarket.web.service.payment.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Service
public class OrderService {
    private final ModelMapper modelMapper;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;
    private final KakaoPayService kakaoPayService;

    public OrderDTO selectOne(long productId, long memberId) {
        Order vo = Order.builder().productId(productId).memberId(memberId).build();
        Order order = orderMapper.searchOneDeadline(vo);
        log.info("order : {}", order);
        if (order == null) return null;
        return modelMapper.map(order, OrderDTO.class);
    }


    public void insertOrder(OrderPayDTO orderPayDTO) {

        orderPayDTO.setOrderId(orderMapper.selectOrderId());
        try {
            orderMapper.insertOrder(orderPayDTO);
        } catch (DuplicateKeyException d) {
            orderMapper.insertOrder(orderPayDTO);
            //throw new RuntimeException("키 중복되었으니 다시 시도 바랍니다.");
        }
    }


    public List<AuthorDTO> getOrderAuthorList(String memberId) {
        List<AuthorDTO> authorList = orderMapper.selectAuthor(memberId);
        return authorList;
    }


    public List<MemberDTO> getOrderMemberList(String memberId) {
        List<MemberDTO> memberList = orderMapper.selectMember(memberId);
        return memberList;
    }

    public List<SearchOrderDTO> orderSearchAuthor(SearchOrderDTO searchOrderDTO) {
        List<SearchOrderDTO> getSearch = orderMapper.orderSearchAuthor(searchOrderDTO);
        return getSearch;
    }

    public List<SearchOrderDTO> orderSearchMember(SearchOrderDTO searchOrderDTO) {
        List<SearchOrderDTO> getSearch = orderMapper.orderSearchMember(searchOrderDTO);
        return getSearch;
    }


    //주문 상태 변경
    public void OrderStatus(OrderPayDTO orderPayDTO) {

        orderMapper.updateOrderStatus(orderPayDTO);
    }

    public List<OrderStatusDTO> countOrderStatus(OrderStatusDTO orderStatusDTO) {

        List<OrderStatusDTO> count = orderMapper.countOrderStatus(orderStatusDTO);
        return count;
    }

    //주문
    public KakaoReadyResponse doOrderReady(OrderPayDTO orderPayDTO, HttpServletRequest request) {
        // step1. 주문 정보 저장
        insertOrder(orderPayDTO);
        // step2. 카카오페이 결제 준비 api 호출
        KakaoReadyResponse response = kakaoPayService.kakaoPayReady(orderPayDTO);
        // step3. tid 저장
        log.info("tid : {}", response.getTid());
        HttpSession session = request.getSession();
        session.setAttribute("kakaoPaySession", response.getTid());
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setTid(response.getTid());
        paymentDTO.setPartnerOrderId(orderPayDTO.getOrderId());
        paymentDTO.setPartnerUserId(orderPayDTO.getNickname());
        paymentMapper.insertPayment(paymentDTO);

        return response;
    }
}
