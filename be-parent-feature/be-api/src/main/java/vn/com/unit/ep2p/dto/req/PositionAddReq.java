/*******************************************************************************
 * Class        ：PositionAddReq
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：minhnv
 * Change log   ：2020/12/09：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.PositionInfoReq;

/**
 * PositionAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Setter
@Getter
public class PositionAddReq extends PositionInfoReq{
    @ApiModelProperty(notes = "Code of position", example = "THE_ANH", required = true, position = 3)
    private String code;
}
