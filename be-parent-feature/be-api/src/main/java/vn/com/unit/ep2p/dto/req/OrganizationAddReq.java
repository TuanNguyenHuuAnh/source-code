/*******************************************************************************
 * Class        ：OrganizationAddReq
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：SonND
 * Change log   ：2020/12/15：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.OrganizationInfoReq;

/**
 * OrganizationAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class OrganizationAddReq extends OrganizationInfoReq {
    @ApiModelProperty(notes = "Code of organization", example = "ROOT", required = true, position = 0)
    private String code;
    
}
