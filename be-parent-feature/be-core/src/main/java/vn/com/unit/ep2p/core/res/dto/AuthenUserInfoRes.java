/*******************************************************************************
 * Class        ：AuthenUserInfoRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * AuthenUserInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class AuthenUserInfoRes {

    private Long userId;
    private String fullName;
    private String username;
    private String email;
    private String phoneNumber;
    private String isFingerprint;
    private Boolean isEmail;
    private Boolean isPushNotification;
    private Boolean pushNotification;
    private String avatarPath;
    private Long avatarRepoId;
    private Long companyId;
    private String companyName;
    private String position;
    private String language;
    
    private List<AuthenDepartmentRes> departments;
    private List<AuthenCompanyRes> companies;

}
