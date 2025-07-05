package vn.com.unit.imp.excel.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.imp.excel.constant.MessageList;

@Getter
@Setter
public class UploadResultDto {
    public static final String INVALID_DATA = "INVALID_DATA";
    public static final String INVALID_EXCEL_FILE = "INVALID_EXCEL_FILE";
    public static final String DEADLOCK = "DEADLOCK";
    public static final String TIMEOUT = "TIMEOUT";
    public static final String ERROR_NOT_TEMPLATE = "ERROR_NOT_TEMPLATE";
    public static final String ERROR_ROW_EMPTY = "ERROR_ROW_EMPTY";
    public static final String ERROR_ROW_START = "ERROR_ROW_START";
    public static final String ERROR_INVALID_DATA = "ERROR_INVALID_DATA";
    private String messageError;
    private String typeError;
    private String sessionKey;
    private String fileName;
    private boolean uploadStatus = true;
    private ImportExcelSearchDto searchDto;
    private MessageList messageList;
}
