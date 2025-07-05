/*******************************************************************************
 * Class        ：GroupSystemConfigUpdateReq
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：ngannh
 * Change log   ：2020/12/15：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.GroupSystemConfigInfoReq;

/**
 * GroupSystemConfigUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class GroupSystemConfigUpdateReq extends GroupSystemConfigInfoReq{
    @ApiModelProperty(notes = "Id of group system config on system", example = "1", required = true, position = 0)
    private Long id;
}
