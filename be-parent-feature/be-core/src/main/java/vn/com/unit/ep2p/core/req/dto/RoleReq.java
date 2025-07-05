/*******************************************************************************
 * Class        ：ReqRoleDto
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：NganNH
 * Change log   ：2020/12/03：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * ReqRoleDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
@Getter
@Setter
public class RoleReq {
    
    @ApiModelProperty(notes = "Name of the role", example = "admin", required = true, position = 0)
    private String name;
    
    @ApiModelProperty(notes = "Description of the role", example = "The administration of a business, organization", required = true, position = 0)
    private String description;
    
    @ApiModelProperty(notes = "Active of the role", example = "true", required = true, position = 0)
    private Boolean active;
   
    @ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
}
