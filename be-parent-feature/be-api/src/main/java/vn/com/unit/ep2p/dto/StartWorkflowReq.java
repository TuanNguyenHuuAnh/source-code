/*******************************************************************************
 * Class        ：StartWorkflowReq
 * Created date ：2021/01/21
 * Lasted date  ：2021/01/21
 * Author       ：KhuongTH
 * Change log   ：2021/01/21：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * StartWorkflowReq
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
@ApiModel(description = "Class properties for start workflow")
public class StartWorkflowReq {

    @ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;

    @ApiModelProperty(notes = "Code of business", example = "SIMPLE_BUSINESS", required = true, position = 1)
    private String businessCode;

    @ApiModelProperty(notes = "Comment note action", example = "start workflow...", required = true, position = 2)
    private String note;
}
