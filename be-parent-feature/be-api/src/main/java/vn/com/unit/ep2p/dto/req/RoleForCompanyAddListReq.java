/*******************************************************************************
 * Class        ：RoleForCompanyAddReq
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * RoleForCompanyAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class RoleForCompanyAddListReq{
    @ApiModelProperty(notes = "List role for company information create", required = true, position = 0)
    private List<RoleForCompanyAddReq> listRoleForCompanyAddReq;
    
}
