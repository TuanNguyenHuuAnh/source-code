/*******************************************************************************
 * Class        ：ApiExternalResError
 * Created date ：2020/11/23
 * Lasted date  ：2020/11/23
 * Author       ：taitt
 * Change log   ：2020/11/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ApiExternalResError
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class ApiExternalResError {

    /** errorField */
    private String errorField;
    
    /** errorCode */
    private String errorCode;
    
    /** errorDesc */
    private String errorDesc;
}
