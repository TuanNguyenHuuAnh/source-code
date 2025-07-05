/*******************************************************************************
 * Class        ：TeamAddReq
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：minhnv
 * Change log   ：2020/12/09：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.TeamInfoReq;

/**
 * TeamAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Getter
@Setter
public class TeamAddReq extends TeamInfoReq{
    @ApiModelProperty(notes = "Search by code", example = "code", required = true, position = 2)
    private String code;
    @ApiModelProperty(notes = "Actived for team", example = "true", required = true, position = 0)
    private Boolean actived;
    
    public Boolean getActived() {
        return actived;
    }
}
