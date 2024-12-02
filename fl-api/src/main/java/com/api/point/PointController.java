package com.api.point;

import com.api.user.SuccessUserMessages;
import com.common.utils.SuccessResponse;
import com.service.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.api.config.ApiUrlConstants.*;
import static com.api.point.SuccessPointMessages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;



    /**
     * 입금 신청 api
     * 계좌로 포인트 충전 api
     */
    @PostMapping(APPLY_DEPOSIT_URL)
    public ResponseEntity<?> applyDeposit(@RequestParam Integer price) throws IllegalAccessException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        pointService.applyDepositByAccount(email, price);
        SuccessResponse response = new SuccessResponse(true, APPLY_DEPOSIT_ACCOUNT, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 출금 신청 api
     */
    @PostMapping(APPLY_WITH_DRAW_URL)
    public ResponseEntity<?> applyWithDraw(@RequestParam Integer price) throws IllegalAccessException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        pointService.applyWithDraw(email, price);
        SuccessResponse response = new SuccessResponse(true, APPLY_WITH_DRAW, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 포인트 충전 todo 삭제 예정
     */
    @PostMapping(CHARGE_POINT_URL)
    public ResponseEntity<?> chargePoint(@RequestBody Integer point){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        pointService.chargePoint(email, point);
        SuccessResponse response = new SuccessResponse(true, SuccessUserMessages.CHARGE_POINT_SUCCESS, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
