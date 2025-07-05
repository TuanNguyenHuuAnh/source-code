/*******************************************************************************
 * Class        ：OrganizationUpdateReq
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
 * OrganizationUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class OrganizationUpdateReq extends OrganizationInfoReq {
	@ApiModelProperty(notes = "Id of organization", example = "1", required = true, position = 0)
	private Long orgId;
    
}
