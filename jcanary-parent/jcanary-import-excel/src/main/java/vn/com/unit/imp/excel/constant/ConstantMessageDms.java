package vn.com.unit.imp.excel.constant;

public class ConstantMessageDms {
    /** constant type message language */
    public static final String LANG_EN = ".en";
    public static final String LANG_VI = ".vi";

    /** constant type message error for import */
    public static final int MSG_TYPE_ERROR = 2;
    public static final int MSG_TYPE_WARNING = 1;
    
    public static final String MSG_SUCCESS_SUBMIT = "message.success.submit.label";
    public static final String MSG_SUCCESS_SUBMIT_LANG = "message.success.import.submit.label";
    public static final String MSG_FAIL_SUBMIT = "message.submit.fail";

    public static final String ERROR_STRING_BINARY_DATA_STRING = "String or binary data would be truncated.";
    public static final String ERROR_STRING_BINARY_DATA = "message.error.string.or.binary.data";

    public static final String ERROR_DEADLOCK = "message.error.deadlock";
    public static final String ERROR_DEADLOCK_STRING = "was deadlocked on lock";

    public static final String ERROR_LOCK_TIMEOUT = "message.error.timeout";
    public static final String ERROR_LOCK_TIMEOUT_STRING = "Lock request time out period exceeded";
    
    public static final String ERROR_DATA_CHANGE = "message.error.data.change";
    
    public static final String ERROR_TYPE_EXCEL_VALID = "Invalid header signature";
    public static final String ERROR_TYPE_EXCEL = "excel.file.invalid";

}
