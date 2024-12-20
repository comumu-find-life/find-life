package com.api.config;

public class ApiUrlConstants {
    public static final String BASE_API_URL = "/v1/api";

    // 집 게시글 API URL
    public static final String HOMES_BASE_URL = BASE_API_URL + "/homes";
    public static final String HOMES_VALIDATE_ADDRESS = HOMES_BASE_URL + "/address/validate";
    public static final String HOMES_FIND_BY_USER_ID = HOMES_BASE_URL + "/users/{userId}";
    public static final String HOMES_FIND_BY_ID = HOMES_BASE_URL + "/{homeId}";
    public static final String HOMES_UPDATE_IMAGE = HOMES_BASE_URL + "/{homeId}/image";
    public static final String HOMES_CHANGE_STATUS = HOMES_BASE_URL + "/{homeId}/status/{status}";
    public static final String HOMES_FIND_ALL = HOMES_BASE_URL + "/overview";
    public static final String HOMES_FIND_BY_CITY = HOMES_BASE_URL + "/city";
    public static final String HOMES_FIND_BY_CITY_PATH = HOMES_BASE_URL + "/cities/{city}";
    public static final String HOMES_FIND_FAVORITE = HOMES_BASE_URL + "/favorite";
    public static final String HOMES_DELETE = HOMES_BASE_URL + "/{homeId}";

    // 채팅 API URL
    public static final String DM_BASE_URL = BASE_API_URL + "/dm";
    public static final String DM_FIND_ROOM_INFO = DM_BASE_URL + "/dm-rooms/{dmRoomId}";
    public static final String DM_FIND_ALL_ROOMS = DM_BASE_URL + "/dm-rooms/users/{userId}";

    // 사용자 API URL
    public static final String USERS_BASE_URL = BASE_API_URL + "/users";
    public static final String USERS_SIGN_UP = USERS_BASE_URL + "/sign-up";
    public static final String USERS_CHECK_DUPLICATE_EMAIL = USERS_BASE_URL + "/verification/email/{email}";

    // 이메일 API URL
    public static final String SEND_EMAIL_URL = USERS_BASE_URL+"/verification/email/send/{email}";
    public static final String VERIFICATION_EMAIL_CODE_URL = USERS_BASE_URL + "/verification/email/confirm/{email}/{code}";

    public static final String USERS_FIND_BY_ID = USERS_BASE_URL + "/{userId}";
    public static final String USERS_UPDATE = USERS_BASE_URL;
    public static final String USER_ACCOUNT_REGISTER_URL = USERS_BASE_URL + "/{userId}/account";
    public static final String USER_ACCOUNT_EXIST_URL = USERS_BASE_URL + "/{userId}/account/exist";
    public static final String USER_POINT_CHARGE_URL = USERS_BASE_URL + "/{userId}/point";
    public static final String USERS_IMAGE_UPDATE = USERS_BASE_URL + "/{userId}/image";
    public static final String USERS_GET_MY_USER_ID = USERS_BASE_URL + "/me/userId";
    public static final String USERS_GET_PROFILE = USERS_BASE_URL + "/profile/{userId}";
    public static final String USERS_FIND_LOGIN_USER = USERS_BASE_URL + "/user-info";

    // 안전거래 API URL

    public static final String DEALS_BASE_URL = BASE_API_URL + "/deals";
    public static final String DEALS_REQUEST = DEALS_BASE_URL;
    public static final String DEALS_GETTER_READ = DEALS_BASE_URL + "/read/getter";
    public static final String DEALS_FIND_ALL_BY_USER_ID = DEALS_BASE_URL + "/users/{userId}";
    public static final String DEALS_ACCEPT_REQUEST = DEALS_BASE_URL + "/accept/{dealId}";
    public static final String DEALS_REQUEST_COMPLETE_URL = DEALS_BASE_URL + "/{dealId}/complete";
    public static final String DEALS_CANCEL_BEFORE_URL = DEALS_BASE_URL + "/{dealId}/cancel/before";
    public static final String DEALS_CANCEL_AFTER = DEALS_BASE_URL + "/{dealId}/cancel/after";

    //포인트 API URL
    public static final String POINT_BASE_URL = BASE_API_URL + "/points";
    public static final String POINT_CHARGE_PAYPAL = POINT_BASE_URL + "/charge/paypal";
    public static final String APPLY_WITH_DRAW_URL = POINT_BASE_URL + "/apply/with-draw";
    public static final String APPLY_DEPOSIT_URL = POINT_BASE_URL + "/apply/deposit";

    //admin 만
    public static final String CHARGE_POINT_URL = POINT_BASE_URL + "/charge/{userId}";
}


