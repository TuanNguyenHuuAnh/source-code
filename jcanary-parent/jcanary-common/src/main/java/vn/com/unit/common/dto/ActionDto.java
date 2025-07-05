/*******************************************************************************
 * Class        ：ActionDto
 * Created date ：2020/04/09
 * Lasted date  ：2020/04/09
 * Author       ：KhuongTH
 * Change log   ：2020/04/09：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.constant.CommonConstant;

/**
 * ActionDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class ActionDto {

    private String buttonId;
    private String buttonType;
    private String buttonValue;
    private String buttonName;
    private String buttonNamePassive;
    private String functionCode;
    private boolean isSave;
    private boolean isSubmit;
    private boolean isAuthenticate;
    private boolean isSign;
    private boolean isExportPdf;
    private boolean fieldSign;
    private boolean displayHistory;
    private String confirmNote;
    private List<String> elapseTimeList;

    public ActionDto() {
        super();
        confirmNote = CommonConstant.EMPTY;
    }

    public ActionDto(String buttonId, String buttonType, String buttonValue, String functionCode, boolean isSave, boolean isAuthenticate,
            boolean isSign, boolean isExportPdf, boolean fieldSign) {
        super();
        this.buttonId = buttonId;
        this.buttonType = buttonType;
        this.buttonValue = buttonValue;
        this.functionCode = functionCode;
        this.isSave = isSave;
        this.isAuthenticate = isAuthenticate;
        this.isSign = isSign;
        this.isExportPdf = isExportPdf;
        this.fieldSign = fieldSign;
        this.confirmNote = CommonConstant.EMPTY;
    }

}
