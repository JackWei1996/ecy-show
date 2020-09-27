package com.ecy.show.global;

/**
 * 系统常量
 */
public interface Constant {

    int YES = 1;
    int NO = 0;

    int ADMIN_ROLE = 1;
    int STAFF_ROLE = 2;
    int USER_ROLE = 3;
    int AUDIT_ADMIN_ROLE = 4;

    int BOOK_TIME_OUT = 30;

    /**
     * 访客预约最大时长（秒） 2小时
     */
    int BOOK_MAX_TIME = 7200;

    /**
     * JWT 请求头
     */
    String AUTHORIZATION = "AUTHORIZATION";
    /**
     * token 有效期
     */
    long TOKEN_MAX_VALID_TIME = 15 * 24 * 60 * 60 * 1000;
    /**
     * jwt验证头，此字符串代表使用jwt验证
     */
    String BEARER = "Bearer";
    /**
     * jwt payload的key
     */
    String KEY_ID = "id";

    String ROLE_USER = "user";

    /**
     * 字典查询
     */
    String DICT_CAR_TYPE = "car-type";
    String DICT_REJECT_TYPE = "reject-type";
    String DICT_BUSINESS_TYPE = "business-type";

    String KEYTOP_USER = "ktapi";
    String KEYTOP_URL = "http://280479b2n7.zicp.vip/";
    String KEYTOP_PWD = "0506E3";
    String KEYTOP_APP_KEY = "693207DB8148FBC9D8410179";
//    String KEYTOP_URL = "http://220.160.111.118:9099/";
//    String KEYTOP_PWD = "0306A9";
//    String KEYTOP_APP_KEY = "F7A0B971B199FD2A1017CEC5";
    String KEYTOP_PAEK_CODE = "1";
    Integer KEYTOP_FLOOR_ID = 3;
    Integer KEYTOP_AREA_ID = -1;
}