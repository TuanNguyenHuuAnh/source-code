/*******************************************************************************
 * Class        ：FormRegisterReq
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：taitt
 * Change log   ：2020/12/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * FormRegisterReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class FormRegisterReq {

    private String imageOzr;
    private String formName;
    private String description;
    private Long displayOrder;
    private String deviceType;
    private Long categoryId;
    private Long businessId;
    private Long companyId;
    private String reportPath;
}
