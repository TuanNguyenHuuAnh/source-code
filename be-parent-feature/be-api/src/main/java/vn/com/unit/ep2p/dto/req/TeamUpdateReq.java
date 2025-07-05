/*******************************************************************************
 * Class        ：TeamUpdateReq
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
 * TeamUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Setter
@Getter
public class TeamUpdateReq extends TeamInfoReq{
    @ApiModelProperty(notes = "Search by teamId", example = "1", required = true, position = 1)
    private Long teamId;
    @ApiModelProperty(notes = "Actived for team", example = "true", required = true, position = 0)
    private Boolean actived;
    
    public Boolean getActived() {
        return actived;
    }
}