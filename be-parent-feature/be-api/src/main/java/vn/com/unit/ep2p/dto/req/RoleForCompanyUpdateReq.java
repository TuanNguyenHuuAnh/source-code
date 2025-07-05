/*******************************************************************************
 * Class        ：RoleForCompanyUpdateReq
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：ngannh
 * Change log   ：2020/12/23：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.RoleForCompanyInforReq;

/**
 * RoleForCompanyUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class RoleForCompanyUpdateReq extends RoleForCompanyInforReq{
    @ApiModelProperty(notes = "Id of company role on system", example = "1", required = true, position = 0)
    private Long id;
    @ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
    @ApiModelProperty(notes = "Id of role", example = "1", required = true, position = 0)
    private Long roleId;
}
