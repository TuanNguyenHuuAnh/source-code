/*******************************************************************************
 * Class        ：PagingReq
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * PagingReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public abstract class PagingReq extends CommonReq {
    
	@ApiModelProperty(notes = "Number record on page", example = "5" , required = true, position = 0)
    private Integer pageSize;
	
	@ApiModelProperty(notes = "Current page", example = "0" , required = true, position = 0)
    private Integer pageIndex;
	
	@ApiModelProperty(notes = "Paging on list", example = "1" , required = true, position = 0)
    private Integer isPaging;
}
