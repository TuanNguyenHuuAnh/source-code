/*******************************************************************************
 * Class        ：AccountOrgSearchReq
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：SonND
 * Change log   ：2020/12/16：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * AccountOrgSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class AccountOrgSearchReq{

    @ApiModelProperty(notes = "Id of account", example = "1", required = true, position = 0)
    private Long userId;
    @ApiModelProperty(notes = "Account organization actived", example = "true",  position = 0)
    private Boolean actived;
    @ApiModelProperty(notes = "Name of organization", example = "Tây Ninh",  position = 0)
    private String orgName;
    @ApiModelProperty(notes = "Name of position", example = "Minh",  position = 0)
    private String positionName;
    
    public boolean getActived() {
        return actived;
    }
    
}
