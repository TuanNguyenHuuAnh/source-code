package vn.com.unit.ep2p.constant;

public class ApiOtpConstant {
    // cache based on username and OPT MAX 8
    public static final Integer OTP_EXPIRE_MINS = 5;

    public static final Integer OTP_TYPE_EXPIRE_MINS = 10;

    public static final String OTP_TYPE_EMAIL_PERSONAL = "email_personal";
    public static final String OTP_TYPE_EMAIL_DLVN = "email_dlvn";
    public static final String OTP_TYPE_ANSWER_QUESTION = "answer_questions";
    public static final String OTP_TYPE_SMS = "sms";
}
