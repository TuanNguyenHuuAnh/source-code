/*******************************************************************************
 * Class        ：ResError
 * Created date ：2019/03/01
 * Lasted date  ：2019/03/01
 * Author       ：HungHT
 * Change log   ：2019/03/01：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.res;

import lombok.Getter;
import lombok.Setter;

/**
 * ResError
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@Setter
public class ResError {
    
    /** errorField */
    private String errorField;
    
    /** errorCode */
    private String errorCode;
    
    /** errorDesc */
    private String errorDesc;
}
