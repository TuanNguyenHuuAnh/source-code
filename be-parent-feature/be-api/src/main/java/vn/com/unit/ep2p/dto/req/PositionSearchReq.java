/*******************************************************************************
 * Class        ：PositionSearchReq
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
 * PositionSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Setter
@Getter
public class PositionSearchReq{
    @ApiModelProperty(notes = "account actived", example = "1", required = true, position = 0)
    private boolean actived; 

    @ApiModelProperty(notes = "Id of company", example = "2", required = true, position = 0)
    private Long companyId;
    
    public boolean getActived() {
        return actived;
    }
}
