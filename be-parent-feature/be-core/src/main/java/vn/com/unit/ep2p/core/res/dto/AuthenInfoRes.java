/*******************************************************************************
 * Class        ：AuthenInfoRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthenInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AuthenInfoRes {

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private Date expired;
    private boolean firstLogin;

//    private AuthenUserInfoRes userInfo;
}
