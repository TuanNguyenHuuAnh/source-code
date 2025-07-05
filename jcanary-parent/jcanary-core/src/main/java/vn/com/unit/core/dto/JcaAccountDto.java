/*******************************************************************************
 * Class        ：AccountDto
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：SonND
 * Change log   ：2020/12/01：01-00 SonND create a new
 ******************************************************************************/

package vn.com.unit.core.dto;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import jp.sf.amateras.mirage.annotation.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.unit.core.security.jwt.JwtToken;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * AccountDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Setter
@Getter
@NoArgsConstructor
public class JcaAccountDto extends AbstractTracking {
    private Long userId;
    private String code;
    private String username;
    private String password;
    private String accountType;
    private String fullname;
    private String email;
    private String personalEmail;
    private Date birthday;
    private String phone;
    private String birthdayStr;
    private String avatar;
    private Long avatarRepoId;
    private Date loginDate;
    private int failedLoginCount;
    private String gender;
    private Boolean receivedNotification;
    private Boolean receivedEmail;
    private boolean actived;
    private boolean enabled;
    private Long companyId;
    private String companyName;
    private String positionId;
    private String positionName;
    private String orgName;
    private String genderName;
    private JwtToken authentication;
    
	private String authorities;
	private Date expiredDate;

    private String urlFacebook;

    private String urlZalo;
    private Integer googleFlag;
    private Integer facebookFlag;
    private Integer appleFlag;
    
    private String uid;
    

    public Boolean getReceivedEmail() {
        return receivedEmail;
    }
    
    public Boolean getReceivedNotification() {
        return receivedNotification;
    }
    private String province;
    private String provinceCity;
    private String office;
    private String officeName;
    private String faceMask;
    private boolean confirmApplyCandidate;
    private String channel;
    private String partner;
}
