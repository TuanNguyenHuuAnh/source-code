package vn.com.unit.dts.exception;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.utils.DtsStringUtil;

@Getter
@Setter
public class ExceptionCode {

    private String text;
    private int value;

    public ExceptionCode(String exceptionErrorCode) {
        int indexUnderlinedFirst = DtsStringUtil.indexOf(exceptionErrorCode, DtsConstant.UNDERLINED);
        this.value = Integer.parseInt(DtsStringUtil.substring(exceptionErrorCode, 0, indexUnderlinedFirst));
        this.text = DtsStringUtil.substring(exceptionErrorCode, indexUnderlinedFirst + 1, DtsStringUtil.length(exceptionErrorCode));
    }
}
