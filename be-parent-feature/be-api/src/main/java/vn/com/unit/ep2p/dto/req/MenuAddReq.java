/*******************************************************************************
 * Class        ：MenuAddReq
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
 * MenuAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class MenuAddReq extends MenuInfoReq {
	@ApiModelProperty(notes = "Code of menu", example = "MENU", required = true, position = 0)
    private String code;
    
}
