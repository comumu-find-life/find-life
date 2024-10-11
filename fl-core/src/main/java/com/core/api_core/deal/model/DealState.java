package com.core.api_core.deal.model;

import lombok.Getter;

@Getter
public enum DealState {
    NONE,
    REQUEST_DEAL, // 거래 요청
    ACCEPT_DEAL, // 거래 수락
    CANCEL_BEFORE_DEAL, // 거래 수락 전 취소
    CANCEL_DURING_DEAL, // 거래 수락 후 취소
    COMPLETE_DEAL, // 거래 완료


}
