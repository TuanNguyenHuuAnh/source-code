/*******************************************************************************
 * Class        ：UserPrincipal
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：vinhlt
 * Change log   ：2021/01/28：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserPrincipal
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
@Getter
@Setter
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    /** Locale Request */
    private Locale locale;
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /** accountId */
    private Long accountId;
    /** username */
    private String username;
    /** fullname */
    private String fullname;
    /** password */
    private String password;
    /** email */
    private String email;
    /** enabled */
    private boolean enabled;
    /** birthday */
    private Date birthday;
    /** avatar */
    private String avatar;
    /** branchId */
    private Long branchId;
    /** departmentId */
    private Long departmentId;
    /** teamIds */
    private String teamIds;
    /** authorities */
    private List<GrantedAuthority> authorities;
    /** accountNonExpired */
    private boolean accountNonExpired = true;
    /** accountNonLocked */
    private boolean accountNonLocked = true;
    /** credentialsNonExpired */
    private boolean credentialsNonExpired = true;
    /** default language */
    private String defaultLang;
    /** avatarRepoId */
    private Long avatarRepoId;
    private String channel;
    private String phone;
    private String gender;

    /** default runtimeEngines */
    /* private Map<String, RuntimeEngine> runtimeEngines; */

    /** create date */

    private Date createdDate;

    private Long positionId;

    private int ldapFlag;
    
    private Long companyId;
    private String companyName;
    private String systemCode;
    private String systemName;
    private String shortcutIcon;
    private Long shortcutIconRepoId;
    private String logoLarge;
    private Long logoLargeRepoId;
    private String logoMini;
    private Long logoMiniRepoId;
    private boolean companyAdmin;
    private boolean forceChangePass;
    private String style;
    private String packageShortcutIcon;
    private String packageLogoLarge;
    private String packageLogoMini;
    private List<Long> companyIdList;
    private boolean fingerprint;
    private boolean pushNotification;
    private boolean pushEmail;
    private List<Long> companyIdDataList;
    private List<Long> companyIdEmailList;
    private boolean localAccount;
    private String sessionId;
    private String integUrl;
    private Long sessionLoginId;
    private String code;
    /**cmnd*/
    private String cmnd;
    
    /** List function delegator */
    private List<String> functionDelegator;
    
    /** List function performer */
    private List<String> functionPerformer;
    
    private boolean archiveFlag;
    
    /** TaiTT add hotfix 20200818 bug No 173 and bug No 343 */
    private String os;
    private String versionApp;
    /** END */
    
    private boolean canSendHO;
    private boolean isHO;
    
    private boolean firstLogin;
    private boolean confirmDecree;
    private String accountType;
    private boolean passwordExpired;
    // Tracking
    private Date authDate;
    private String tokenType;
    private Long tokenValidityMillis;
    private boolean requireTracking = true;
    private String urlFacebook;

    private String urlZalo;
    private Integer googleFlag;
    private Integer facebookFlag;
    private Integer appleFlag;
    private String agentName;
    private boolean forceChangeGadPassword;
    private String faceMask;
    private String apiToken;
    private boolean isGad;
    private boolean resetPassword;
    private String deviceId;
    private String deviceToken;
    
    private String daiIchiOnUrl;
    private String menuInfo;
    private boolean confirmSop;
    
    public UserPrincipal(String username, String password, boolean accountNonExpired, boolean accountNonLocked,
            boolean credentialsNonExpired, boolean enable, List<GrantedAuthority> authorities, Date createdDate, Long positionId, String faceMask) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enable;
        this.authorities = authorities;
        this.createdDate = createdDate;
        this.positionId = positionId;
        this.faceMask = faceMask;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean hasPermission(String permission) {
        return getAuthorities().contains(new SimpleGrantedAuthority(permission));
    }

}
