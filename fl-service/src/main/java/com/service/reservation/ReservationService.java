package com.service.reservation;

import com.common.reservation.mapper.ReservationMapper;
import com.common.reservation.request.ReservationGeneratorRequest;
import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.reservation.model.Reservation;
import com.core.api_core.reservation.repository.ReservationRepository;
import com.core.api_core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper mapper;

    public Long reserve(ReservationGeneratorRequest request){
        Reservation reservation = mapper.toEntity(request);
        return reservationRepository.save(reservation).getId();
    }

    public void completeReservation(){

    }

    public void cancelByProvider(){

    }

    public void cancelByGetter(){

    }
}
