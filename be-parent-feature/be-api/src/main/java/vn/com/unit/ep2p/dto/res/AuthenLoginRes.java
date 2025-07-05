/*******************************************************************************
 * Class        ：AuthenLoginRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.res.dto.AuthenInfoRes;

/**
 * AuthenLoginRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AuthenLoginRes extends AuthenInfoRes {
    private Integer expiredTimeNumber;
    private boolean passwordExpired;
    private boolean forceChangePass;
    private boolean forceChangeGadPassword;
    private boolean isGad;
    private String apiToken;
	private boolean confirmDecree;
	private String daiIchiOnUrl;
	private String channel;
	private String menuInfo;
	private boolean confirmSop;
}
