/*******************************************************************************
 * Class        ：ApiExternalErrorMessage
 * Created date ：2020/11/23
 * Lasted date  ：2020/11/23
 * Author       ：taitt
 * Change log   ：2020/11/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ApiExternalErrorMessage
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class ApiExternalErrorMessage {

    private String errorCode;
    private String errorDesc;
    
    public ApiExternalErrorMessage(String errorCode, String errorDesc) {
        super();
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
