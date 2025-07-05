/*******************************************************************************
 * Class        ：AbstractResApiExternalDto
 * Created date ：2020/11/23
 * Lasted date  ：2020/11/23
 * Author       ：taitt
 * Change log   ：2020/11/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.api.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.api.enumdef.APIStatus;

/**
 * AbstractResApiExternalDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AbstractResApiExternalDto {
    /** correlationId */
    private String correlationId;
    
    /** httpStatus */
    private HttpStatus httpStatus;
    
    /** status */
    private String status;
    
    /** objErrors */
    private List<ApiExternalResError> objErrors;
    
    /**
     * 
     * @author taitt
     */
    public AbstractResApiExternalDto () {
        super();
    }
    
    /**
     * @param correlationId
    /** @param status
    /** @param objErrors
     * @author taitt
     */
    public AbstractResApiExternalDto(String correlationId, String status, List<ApiExternalResError> objErrors) {
        super();
        this.correlationId = correlationId;
        this.status = status;
        this.objErrors = objErrors;
    }
    
    /**
     * 
     * setObjErrorsWithErrorMessage
     * @param error is Object ApiExternalErrorMessage
     * @author taitt
     */
    public void setObjErrorsWithErrorMessage(ApiExternalErrorMessage error) {
        List<ApiExternalResError> errorList = new ArrayList<>();
        ApiExternalResError resError = new ApiExternalResError();
        this.status = APIStatus.FAIL.toString();
        resError.setErrorCode(error.getErrorCode());
        resError.setErrorDesc(error.getErrorDesc());
        errorList.add(resError);
        this.objErrors = errorList;
    }
}
