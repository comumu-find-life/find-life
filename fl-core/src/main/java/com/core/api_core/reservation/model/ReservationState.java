package com.core.api_core.reservation.model;

public enum ReservationState {
    PENDING, //예약 중
    COMPLETED, //예약 완료
    CANCELLED_BY_PROVIDER, //임대인이 취소
    CANCELLED_BY_GETTER; //임차인이 취소

}
