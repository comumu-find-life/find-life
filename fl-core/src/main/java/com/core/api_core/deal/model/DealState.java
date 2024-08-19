package com.core.api_core.deal.model;

import lombok.Getter;

@Getter
public enum DealState {
    NONE,
    // 입금 전 상태
    BEFORE_DEPOSIT,
    // 입금 신청 상태
    DURING_DEPOSIT,
    //입금 완료 상태
    DONE_DEPOSIT,
    // 입금 취소 상태
    FAIL_DEPOSIT,
    //거래 완료 신청
    SUBMIT_DEAL,
    //거래 완료 상태
    FINISH,
    //거래 취소 상태
    CANCEL,

}
