/*******************************************************************************
 * Class        ：CategoryUpdateReq
 * Created date ：2020/12/17
 * Lasted date  ：2020/12/17
 * Author       ：SonND
 * Change log   ：2020/12/17：01-00 TrongNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.CategoryInfoReq;

/**
 * AccountUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class CategoryUpdateReq extends CategoryInfoReq {
	@ApiModelProperty(notes = "Id of user on system", example = "1", required = true, position = 0)
	private Long categoryId;
    
}
