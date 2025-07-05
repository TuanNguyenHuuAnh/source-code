/*******************************************************************************
 * Class        ：AccountInfoRes
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：taitt
 * Change log   ：2020/12/08：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;

/**
 * CheckCaptchaRes
 * 
 * @version 01-00
 * @since 01-00
 * @author tinhnt
 */
@Getter
@Setter
public class CheckCaptchaRes {
 
	private String errorCode;
	private String captcha;
	
}
