package com.nexmotion.code;

import lombok.Data;

@Data
public class ErrorCode {

    /* DB 오류 */
    public static final String ERROR_DATABASE = "03";

    /* 통신 오류 */
    public static final String ERROR_COMMUNICATION = "05";

    /* 최대 처리 건수 초과 자료 */
    public static final String ERROR_MAX_OVER = "07";

    /* 미 등록 전문 코드 */
    public static final String ERROR_NOT_REGISTERED = "11";

    /* 업부 구분 오류 */
    public static final String ERROR_WORK_DIVISION = "12";

    /* 요청 구분 오류 */
    public static final String ERROR_REQUEST_DIVISION = "13";

    /* 망 구분 오류 */
    public static final String ERROR_NET_DIVISION = "14";

    /* 미 등록 단말기 */
    public static final String ERROR_NOT_REGISTERED_DEVICE = "15";

    /* 사용자 확인 */
    public static final String ERROR_USER_CHECK = "16";

    /* 사용자 주민번호 오류 */
    public static final String ERROR_USER_NUMBER = "17";

    /* 조회 구분 오류 */
    public static final String ERROR_CHECK_DIVISION = "18";

    /* 요청 건수 오류 */
    public static final String ERROR_REQUEST_QUANTITY = "19";

    /* 실명 인증 오류 */
    public static final String ERROR_UNAUTHENTICATED = "21";

    /* 입력 자료 확인 */
    public static final String ERROR_INPUT_DATA = "30";

    /* 기타 오류 */
    public static final String ERROR_ETC = "99";

    public String get(String respCd) {
        switch (respCd) {
            case ERROR_DATABASE:
                return "ERROR_DATABASE";
            case ERROR_COMMUNICATION:
                return "ERROR_COMMUNICATION";
            case ERROR_MAX_OVER:
                return "ERROR_MAX_OVER";
            case ERROR_NOT_REGISTERED:
                return "ERROR_NOT_REGISTERED";
            case ERROR_WORK_DIVISION:
                return "ERROR_WORK_DIVISION";
            case ERROR_REQUEST_DIVISION:
                return "ERROR_REQUEST_DIVISION";
            case ERROR_NET_DIVISION:
                return "ERROR_NET_DIVISION";
            case ERROR_NOT_REGISTERED_DEVICE:
                return "ERROR_NOT_REGISTERED_DEVICE";
            case ERROR_USER_CHECK:
                return "ERROR_USER_CHECK";
            case ERROR_USER_NUMBER:
                return "ERROR_USER_NUMBER";
            case ERROR_CHECK_DIVISION:
                return "ERROR_CHECK_DIVISION";
            case ERROR_REQUEST_QUANTITY:
                return "ERROR_REQUEST_QUANTITY";
            case ERROR_UNAUTHENTICATED:
                return "ERROR_UNAUTHENTICATED";
            case ERROR_INPUT_DATA:
                return "ERROR_INPUT_DATA";
            case ERROR_ETC:
                return "ERROR_ETC";
            default:
                return null;
        }

    }
}
