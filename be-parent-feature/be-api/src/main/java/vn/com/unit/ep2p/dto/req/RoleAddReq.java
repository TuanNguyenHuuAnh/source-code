/*******************************************************************************
 * Class        ：RoleAddReq
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
 * RoleAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class RoleAddReq extends RoleInfoReq{
    
    @ApiModelProperty(notes = "Code of the role", example = "admin", required = true, position = 0)
    private String code;
    
}
