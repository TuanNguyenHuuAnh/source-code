/*******************************************************************************
 * Class        ：ItemAddReq
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：minhnv
 * Change log   ：2020/12/09：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * ItemAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Getter
@Setter
public class ItemAddReq extends ItemInfoReq{
    @ApiModelProperty(notes = "Search by functionCode", example = "1", required = true, position = 2)
    private String functionCode;
}
