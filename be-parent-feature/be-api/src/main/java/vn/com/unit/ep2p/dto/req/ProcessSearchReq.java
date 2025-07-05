/*******************************************************************************
 * Class        ：ProcessSearchReq
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
 * <p> ProcessSearchReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class ProcessSearchReq {
    
    @ApiModelProperty(notes = "search by company", example = "1", required = true, position = 0)
    private Long companyId;
    
    @ApiModelProperty(notes = "search by business", example = "1", required = true, position = 0)
    private Long businessId;
    
}
