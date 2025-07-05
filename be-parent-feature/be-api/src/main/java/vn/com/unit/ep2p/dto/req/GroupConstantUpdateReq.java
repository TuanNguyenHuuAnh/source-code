/*******************************************************************************
 * Class        ：GroupConstantUpdateReq
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：tantm
 * Change log   ：2020/12/23：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.GroupConstantInfoReq;

/**
 * GroupConstantUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class GroupConstantUpdateReq extends GroupConstantInfoReq {

    @ApiModelProperty(notes = "Id of group constant on system", example = "123", required = true, position = 0)
    private Long id;
}
