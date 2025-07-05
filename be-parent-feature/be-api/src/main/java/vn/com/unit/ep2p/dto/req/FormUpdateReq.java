/*******************************************************************************
 * Class        ：FormUpdateReq
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：taitt
 * Change log   ：2020/12/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * FormUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class FormUpdateReq {

    private Long formId;
    private Long categoryId;
    private String formName;
    private String description;
    private Long displayOrder;
    private String deviceType;
    private Long businessId;
    private String functionCode;
    
}
