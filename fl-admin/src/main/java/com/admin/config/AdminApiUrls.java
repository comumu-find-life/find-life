package com.admin.config;

public class AdminApiUrls {
    public static final String BASE_API_URL = "/v1/api/admin";

    // 유저 관련 API URL
    public static final String USERS = BASE_API_URL + "/users";
    public static final String USER = USERS+"/{userId}";
    public static final String USER_SIGN_UP = USERS + "/sign-up";
    public static final String USER_INACTIVE = USERS + "/{userId}/inactive";

    // 거래 관련 API URL
    public static final String DEALS = BASE_API_URL + "/deals";
    public static final String DEALS_REQUEST_DEPOSIT = DEALS + "/request-deposit";
    public static final String DEAL_BY_ID = DEALS + "/{dealId}";
    public static final String DEAL_CONFIRM_DEPOSIT = DEALS + "/{dealId}/confirm-deposit";
    public static final String DEAL_CANCEL_DEPOSIT = DEALS + "/{dealId}/cancel-deposit";
    public static final String DEAL_SUBMIT_COMPLETED = DEALS + "/submit-completed";
    public static final String DEAL_COMPLETE = DEALS + "/{dealId}/complete";
}
