package com.osj.stockinfomation.C;

    /*
id : service@holdempass.com
pw : @James1006

key pw : holdempass
api key : holdempass
 */

public class C {
    //운영
    public static final String BASE_URL = "http://holdempass.com";
    public static final String Main = "/index.php";
    public static final String listView = "/sub/locallist.php?area1=&type=";

    //디바이스 토큰 등록 API
//    public static final String PUSH_EQIPS = "notice/api/v1/notice/eqips";
    //푸시, 마케팅 수신 여부 업데이트 API
//    public static final String PUSH_MKT_UPDATE = "notice/api/v1/notice/eqips/{device_id}/push/{pushYn}/mkt/{mktYn}";
    //로그인 상태 업데이트 API
    public static final String GET_STORE_LIST = "/api/getStoreList.php";
    //좌표로 근처 매장 정보 불러오기
    public static final String SET_STORE_LIKE = "/api/setStoreLike.php";

    // 푸시 payload
    public static final String PUSH_PAYLOAD = "push_payload";

}
