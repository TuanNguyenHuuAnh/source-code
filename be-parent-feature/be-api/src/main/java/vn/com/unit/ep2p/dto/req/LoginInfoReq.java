/*******************************************************************************
 * Class        ：LoginInfoReq
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：KhoaNA
 * Change log   ：2020/12/03：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.CommonReq;

/**
 * LoginInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@Setter
public class LoginInfoReq extends CommonReq {

    @NotNull
    @Size(min = 1, max = 50)
    private String systemCode;
    
    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    private Boolean rememberMe;
}
