/*******************************************************************************
 * Class        ：FormCaptchaReq
 * Created date ：2023/09/20
 * Lasted date  ：2023/09/20
 * Author       ：tinhnt
 * Change log   ：2023/09/20：01-00 tinhnt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * FormCaptchaReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class FormCaptchaReq {

    private String userId;
    private String captcha;
    
}
