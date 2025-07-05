/*******************************************************************************
 * Class        ：MenuUpdateReq
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.MenuInfoReq;

/**
 * MenuUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class MenuUpdateReq extends MenuInfoReq {
	@ApiModelProperty(notes = "Id of menu to update", example = "1", required = true, position = 0)
	private Long menuId;
    
}
