/*******************************************************************************
 * Class        ：ItemSearchReq
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
 * ItemSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Setter
@Getter
public class ItemSearchReq{
    @ApiModelProperty(example = "true", required = false, position = 7)
    private boolean displayFlag;
    @ApiModelProperty(notes = "Id of company", example = "2", required = true, position = 0)
    private Long companyId;
}
