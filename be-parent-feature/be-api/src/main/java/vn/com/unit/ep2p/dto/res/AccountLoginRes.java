/*******************************************************************************
 * Class        ：AccountDto
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：TaiTM
 * Change log   ：2020/12/01：01-00 TaiTM create a new
 ******************************************************************************/

package vn.com.unit.ep2p.dto.res;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AccountDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 * @Last updated: 22/03/2024	nt.tinh SR16136 - Fix lỗi phát hiện trong quá trình Pentest - 2023
 */
@Setter
@Getter
@NoArgsConstructor
public class AccountLoginRes {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private String expired;
    private String role;
    private String accountId;
    private String username;
    private String fullname;
    private String email;
    private Date birthday;
    private String phone;
    private String avatar;
    private String gender;
    private boolean enabled;
    private boolean passwordExpired;
    private boolean confirmDecree;
    private boolean firstLogin;
    private boolean forceChangePass;
    private boolean forceChangeGadPassword;

    private List<String> authorities;
    private String encodeAuthorities;
    private Date expiredDate;

    private String accountType;
    private String urlFacebook;

    private String urlZalo;
    private Integer googleFlag;
    private Integer facebookFlag;
    private Integer appleFlag;
    private Integer expiredTimeNumber;
    private String faceMask;
    private boolean isGad;
    private boolean resetPassword;
    private String apiToken;
    private String daiIchiOnUrl;
    private String menuInfo;
    private boolean confirmSop;
	private String channel;
}
