package llustmarket.artmarket.web.service.alert;

import llustmarket.artmarket.domain.alert.Alert;
import llustmarket.artmarket.domain.alert.AlertType;
import llustmarket.artmarket.web.dto.alert.AlertDTO;
import llustmarket.artmarket.web.dto.alert.AlramDTO;
import llustmarket.artmarket.web.dto.alert.Alrams;
import llustmarket.artmarket.web.dto.chat.ChatRoomDTO;
import llustmarket.artmarket.web.dto.member.MemberDTO;
import llustmarket.artmarket.web.mapper.alert.AlertMapper;
import llustmarket.artmarket.web.service.DateTimeService;
import llustmarket.artmarket.web.service.chat.ChatRoomService;
import llustmarket.artmarket.web.service.member.EmailService;
import llustmarket.artmarket.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class AlertServiceImpl implements AlertService {

    private final ModelMapper modelMapper;

    private final MemberService memberService;
    private final ChatRoomService chatRoomService;
    private final AlertMapper alertMapper;

    private final EmailService emailService;
    private final DateTimeService dateTimeService;


    @Transactional
    @Override
    public void registerAlert(long memberId, long alertPath, AlertType alertType) {
        log.info("# 알림 전송");
        //현제 회원 정보
        MemberDTO member = null;
        // 상대 회원 Id
        long authorMemberId = 0;

        switch (alertType) {
            case MESSAGE: {
                // 현제 회원 구하기
                member = memberService.selectOne(memberId);
                // 상대 회원 아이디
                ChatRoomDTO chatRoomDTO = chatRoomService.searchChatRoomId(alertPath);
                authorMemberId = chatRoomDTO.getChatFromId();
                if (memberId == authorMemberId) authorMemberId = chatRoomDTO.getChatToId();
            }

            default: {
                // 주문상품


            }

        }

        Alert alert = Alert.builder()
                //상대방 회원 정보
                .memberId(authorMemberId)
                //나의 정보
                .alertWriter(member.getNickname())
                .alertIdentity(member.getIdentity())
                .alertType(String.valueOf(alertType))
                .alertPath(alertPath)
                .alertDate(LocalDateTime.now())
                .build();
        alertMapper.insertOne(alert);
        alertEmail(alert);
    }

    @Override
    public void updateStatus(long memberId) {
        alertMapper.updateStatus(memberId);
    }

    @Override
    public void updateOneCheck(long memberId, long pathId, AlertType pathType) {
        alertMapper.updateOneStatus(Alert.builder().memberId(memberId).alertType(String.valueOf(pathType)).alertPath(pathId).build());
    }


    @Override
    public void updateDate(AlertDTO dto) {
        log.info("# 알림 시간 업데이트");
        dto.setAlertDate(LocalDateTime.now());
        Alert alert = modelMapper.map(dto, Alert.class);
        alertMapper.updateDate(alert);
        alertEmail(alert);
    }


    private void alertEmail(Alert alert) {
        // 상대방 회원 이메일 정보
        long memberId = alert.getMemberId();
        MemberDTO memberDTO = memberService.selectOne(memberId);
        emailService.sendAlertByEmail(
                memberDTO.getEmail(),
                AlramDTO.builder()
                        .alramSender(alert.getAlertWriter())
                        .alramType(alert.getAlertType())
                        .alertDate(dateTimeService.DateToString(alert.getAlertDate()))
                        .build());
    }


    @Override
    public Alrams searchOneAlert(long memberId) {
        log.info("# 회원의 알림 리스트");
        List<Alert> alerts = alertMapper.selectOne(memberId);
        List<AlramDTO> alramList = alerts.stream().map(item -> {
            return AlramDTO.builder()
                    .alramSender(item.getAlertWriter())
                    .alramType(item.getAlertType())
                    .alertDate(dateTimeService.DateToString(item.getAlertDate()))
                    .build();
        }).collect(Collectors.toList());
        return Alrams.builder().alrams(alramList).build();
    }

    @Override
    public AlertDTO searchOnePath(long pathId, AlertType alertType) {
        log.info("동일한 메시지 찾아서 가져오기");
        Alert vo = Alert.builder().alertPath(pathId).alertType(String.valueOf(alertType)).build();
        Alert alerts = null;
        try {
            alerts = alertMapper.selectOnePathId(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (alerts != null) return modelMapper.map(alerts, AlertDTO.class);
        return null;
    }

    @Override
    public int searchOneAlertNumber(long alramTotalID) {
        int number = alertMapper.selectTotalNumber(alramTotalID);
        log.info("selectTotalNumber : {}", number);
        return number;
    }
}
