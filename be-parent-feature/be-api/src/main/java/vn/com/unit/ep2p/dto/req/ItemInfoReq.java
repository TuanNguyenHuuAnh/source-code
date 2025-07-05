/*******************************************************************************
 * Class        ：ItemInfoReq
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
 * ItemInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Getter
@Setter
public class ItemInfoReq {
    @ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
    @ApiModelProperty(notes = "Search by functionName", example = "MasterData", required = false, position = 3)
    private String functionName;
    @ApiModelProperty(notes = "Search by description", example = "Master Data", required = false, position = 4)
    private String description;
    @ApiModelProperty(notes = "Search by functionType", example = "1", required = true, position = 5)
    private String functionType;
    @ApiModelProperty(example = "1", required = false, position = 6)
    private int displayOrder;
    @ApiModelProperty(example = "true", required = false, position = 7)
    private boolean displayFlag;
}
