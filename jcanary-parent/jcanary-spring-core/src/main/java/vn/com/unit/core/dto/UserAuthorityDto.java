/*******************************************************************************
 * Class        ：UserAuthorityDto
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：vinhlt
 * Change log   ：2021/02/01：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * UserAuthorityDto
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
@Getter
@Setter
public class UserAuthorityDto {
    private Long id;
    
    private String username;
    
    private String password;

    private String fullname;
    
    private String email;
    
    private Date birthday;
    
    private String phone;

    private boolean enabled;

    private String avatar;
    
    private Long repositoryId;
    
    private String code;
    
    private String description;
    
    private boolean actived;
    
    private Date loginDate;
    
    private Integer failedLoginCount;
    
    private Long positionId;
    
    private String gender;
    
    private String channel;
   
    private Long companyId;
    
    private boolean receivedEmail;
    
    private boolean receivedNotification;
    
    private String accountType;
    private String urlFacebook;

    private String urlZalo;
    private Integer appleLogin;
    private Integer facebookLogin;
    private Integer googleLogin;
    private String faceMask;
}
