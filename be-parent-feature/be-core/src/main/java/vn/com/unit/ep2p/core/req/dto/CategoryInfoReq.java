/*******************************************************************************
 * Class        ：ReqCategoryDto
 * Created date ：2020/12/17
 * Lasted date  ：2020/12/17
 * Author       ：TrongNV
 * Change log   ：2020/12/17：01-00 TrongNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * CategoryInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TrongNV
 */

@Getter
@Setter
public class CategoryInfoReq {
	@ApiModelProperty(notes = "Name of category on system", example = "theanh", required = true, position = 0)
    private String categoryName;
	@ApiModelProperty(notes = "Description of category on system", example = "description", required = true, position = 0)
    private String description;
	@ApiModelProperty(notes = "Display order of category on system", example = "1", required = true, position = 0)
    private Long displayOrder;
}
