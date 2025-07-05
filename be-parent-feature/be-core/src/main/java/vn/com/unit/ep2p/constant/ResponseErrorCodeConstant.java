package vn.com.unit.ep2p.constant;

/**
 * @author TaiTM
 */
public class ResponseErrorCodeConstant {
	
    public static final String Successfully = "000";
    
    public static final String PASSWORD_EXPIRED = "Password Expiry";
    public static final String PASSWORD_EXPIRED_VI = "Mật khẩu của Bạn đã hết hạn, vui lòng thay đổi mật khẩu mới để tiếp tục sử dụng!";

    public static final String WRONG_PASSWORD= "Incorrect password";
    public static final String WRONG_PASSWORD_VI= "Mật khẩu hiện tại không đúng.";
    public static final String WRONG_USERNAME = "Account does not exist";
    public static final String WRONG_USERNAME_VI = "Tài khoản đăng nhập không tồn tại trên hệ thống.";

    public static final String DISCIPLINE= "User is inactive";
    public static final String DISCIPLINE_VI= "Tài khoản của Bạn bị khóa do chấm dứt mã số. Vui lòng liên hệ Hotline để được hỗ trợ.";
    public static final String TERMINATED= "User is terminated";
    public static final String TERMINATED_VI= "Tài khoản của Bạn bị tạm khóa do vi phạm. Vui lòng liên hệ Hotline để được hỗ trợ.";

    public static final String TERM= "Term";
    
    public static final String OTHER_ERROR = "004";

}
