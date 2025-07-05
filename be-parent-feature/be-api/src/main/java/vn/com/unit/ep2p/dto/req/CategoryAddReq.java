/*******************************************************************************
 * Class        ：CategoryAddReq
 * Created date ：2020/12/17
 * Lasted date  ：2020/12/17
 * Author       ：TrongNV
 * Change log   ：2020/12/17：01-00 TrongNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.CategoryInfoReq;

/**
 * CategoryAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TrongNV
 */
@Getter
@Setter
public class CategoryAddReq extends CategoryInfoReq {
	@ApiModelProperty(notes = "Id of company", example = "1", required = true, position = 0)
    private Long companyId;
    
}
