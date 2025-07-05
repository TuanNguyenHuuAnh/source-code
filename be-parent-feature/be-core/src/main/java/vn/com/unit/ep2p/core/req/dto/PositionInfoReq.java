/*******************************************************************************
 * Class        ：PositionInfoReq
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：minhnv
 * Change log   ：2020/12/09：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * PositionInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Getter
@Setter
public class PositionInfoReq {
    
    @ApiModelProperty(notes = "Name of position", example = "theanh", required = true, position = 3)
    private String name;

    @ApiModelProperty(notes = "Name other of position", example = "nameAbv", position = 4)
    private String nameAbv;
    
    @ApiModelProperty(notes = "Description of position", example = "description",  position = 5)
    private String description;

    @ApiModelProperty(notes = "Sort order of position", example = "1", required = true, position = 5)
    private Integer positionOrder;
    
    @ApiModelProperty(notes = "Parent id of position", example = "1", required = true, position = 5)
    private Long positionParentId;
    
    @ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "Position actived", example = "true", required = true, position = 5)
    private Boolean actived;
    
}
