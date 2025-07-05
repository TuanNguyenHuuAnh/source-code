/*******************************************************************************
 * Class        ：TeamInfoReq
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：MinhNV
 * Change log   ：2020/12/09：01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * TeamInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Getter
@Setter
public class TeamInfoReq {
    @ApiModelProperty(notes = "Search by name", example = "theanh", required = true, position = 3)
    private String name;
    @ApiModelProperty(notes = "Search by nameAbv", example = "nameAbv", required = true, position = 4)
    private String nameAbv;
    @ApiModelProperty(notes = "Search by description", example = "description", required = true, position = 5)
    private String description;
    @ApiModelProperty(notes = "effectedDate", example = "20201208", required = true, position = 6)
    private String effectedDate;
    @ApiModelProperty(notes = "expiredDate", example = "20201208", required = true, position = 6)
    private String expiredDate;
    @ApiModelProperty(notes = "Search by orgId", example = "1", required = true, position = 6)
    private Long orgId;
    @ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
}
