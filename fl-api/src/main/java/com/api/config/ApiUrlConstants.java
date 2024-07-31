package com.api.config;


public class ApiUrlConstants {
    public static final String BASE_API_URL = "/v1/api";

    public static final String SAVE_DEAL = BASE_API_URL + "/deals";
    public static final String READ_DEAL = BASE_API_URL + "/deals/read";
    public static final String FIND_ALL_BY_USER_ID = BASE_API_URL + "/deals/{userId}";
    public static final String REQUEST_DEPOSIT = BASE_API_URL + "/deals/request/deposit/{dealId}";
    public static final String DONE_DEPOSIT = BASE_API_URL + "/deals/done/deposit/{dealId}";
    public static final String DONE_DEAL = BASE_API_URL + "/deals/done/{dealId}";
    public static final String CANCEL_DEAL = BASE_API_URL + "/deals/cancel/{dealId}";
}