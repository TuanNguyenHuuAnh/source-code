/*******************************************************************************
 * Class        ：AccountAddReq
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.AccountInfoReq;

/**
 * AccountAddReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class LoginGadReq {
    @ApiModelProperty(notes = "GAD password", example = "123456", required = true, position = 0)
    private String password;
}
