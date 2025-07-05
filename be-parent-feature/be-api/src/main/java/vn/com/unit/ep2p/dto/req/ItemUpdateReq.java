/*******************************************************************************
 * Class        ：ItemUpdateReq
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
 * ItemUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Setter
@Getter
public class ItemUpdateReq extends ItemInfoReq{
    @ApiModelProperty(notes = "Search by itemId", example = "1", required = true, position = 1)
    private Long itemId;
    
}
