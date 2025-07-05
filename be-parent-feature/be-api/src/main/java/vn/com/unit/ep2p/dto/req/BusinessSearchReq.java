/*******************************************************************************
 * Class        ：BusinessSearchReq
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：KhuongTH
 * Change log   ：2020/12/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * <p> BusinessSearchReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class BusinessSearchReq {
    
    @ApiModelProperty(notes = "search by company", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "Search by type of process", example = "BPMN", required = true, position = 0)
    private String processType;
}
