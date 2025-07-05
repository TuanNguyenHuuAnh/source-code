/*******************************************************************************
 * Class        ：RoleForCompanyInforReq
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * RoleForCompanyInforReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class RoleForCompanyInforReq {

    @ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "Id of organization", example = "1", required = true, position = 1)
    private Long orgId;
    
    @ApiModelProperty(notes = "Status of role", example = "true/false", required = true, position = 2)
    private Boolean isAdmin;
}
