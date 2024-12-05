package com.api.reservation;

import com.common.reservation.request.ReservationGeneratorRequest;
import com.common.utils.SuccessResponse;
import com.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.api.config.ApiUrlConstants.RESERVATION_SAVE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    /**
     * 방문 날짜 예약, 예치금 입금
     */
    @PostMapping(RESERVATION_SAVE)
    public ResponseEntity<?> reserve(@RequestBody ReservationGeneratorRequest request){
        Long reserveId = reservationService.reserve(request);
        SuccessResponse response = new SuccessResponse(true, "", reserveId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
