/*******************************************************************************
 * Class        ：ProcessDeploySearchReq
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * <p>
 * ProcessDeploySearchReq
 * </p>
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class ProcessDeploySearchReq {
    
    /** The company id. */
    @ApiModelProperty(notes = "search by company", example = "1", required = true, position = 0)
    private Long companyId;
    
    /** The business id. */
    @ApiModelProperty(notes = "search by business", example = "1", required = true, position = 0)
    private Long businessId;
    
    /** The process id. */
    @ApiModelProperty(notes = "search by processId", example = "1", required = true, position = 0)
    private Long processId;
    
}
