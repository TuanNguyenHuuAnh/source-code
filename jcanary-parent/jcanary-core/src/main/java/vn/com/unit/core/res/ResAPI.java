/*******************************************************************************
 * Class        ：ResAPI
 * Created date ：2019/03/01
 * Lasted date  ：2019/03/01
 * Author       ：HungHT
 * Change log   ：2019/03/01：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.res;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.ErrorMessage;
import vn.com.unit.dts.api.enumdef.APIStatus;

/**
 * ResAPI
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@Setter
public class ResAPI {

    /** correlationId */
    private String correlationId;

    /** httpStatus */
    private HttpStatus httpStatus;

    /** status */
    private String status;

    /** objErrors */
    private List<ResError> objErrors;

    /**
     * setObjErrors
     * 
     * @param error
     * @author HungHT
     */
    public void setObjErrorsWithErrorMessage(ErrorMessage error) {
        List<ResError> errorList = new ArrayList<>();
        ResError resError = new ResError();
        this.status = APIStatus.FAIL.toString();
        resError.setErrorCode(error.getErrorCode());
        resError.setErrorDesc(error.getErrorDesc());
        errorList.add(resError);
        this.objErrors = errorList;
    }
}
