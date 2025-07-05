/*******************************************************************************
 * Class        ：RoleUpdateReq
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：ngannh
 * Change log   ：2020/12/03：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.RoleInfoReq;

/**
 * RoleUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class RoleUpdateReq extends RoleInfoReq{
   
    @ApiModelProperty(notes = "Id of role", example = "1", required = true, position = 0)
    private Long roleId;
   
}
