/*******************************************************************************
 * Class        AccountAddDto
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/2101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

/**
 * AccountAddDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class AccountAddDto {

    /** accountId */
    private Long id;
    
    /** username */
    @Size(min=4,max=100)
    private String username;
    
    /** password */
    private String password;
    
    private String passwordConfirm;
    
    /** fullname */
    @Size(min=1,max=255)
    private String fullname;
    
    /** email */
    @Size(min=1,max=255)
    private String email;
    
    /** birthday */
    private Date birthday;
    
    /** phone */
    @Size(min=0,max=200)
    private String phone;
    
    /** enabled */
    private Boolean enabled;
    
    /** avatar */
    private String avatar;
    
    /** departmentId */
    private Long departmentId;
    
    /** positionId */
    private Long positionId;
    
    /** branchId */
    private Long branchId;
    
    private String url;
    
    private String description;
    
    private String teamId;
    
    private Boolean internal;
    
	private List<String> searchKeyIds;

	private String searchValue;
    
    /**
     * Get id
     * @return Long
     * @author KhoaNA
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get username
     * @return String
     * @author KhoaNA
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     * @param   username
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Get password
     * @return String
     * @author KhoaNA
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     * @param   password
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get fullname
     * @return String
     * @author KhoaNA
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set fullname
     * @param   fullname
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Get email
     * @return String
     * @author KhoaNA
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     * @param   email
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get birthday
     * @return Date
     * @author KhoaNA
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Set birthday
     * @param   birthday
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Get phone
     * @return String
     * @author KhoaNA
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set phone
     * @param   phone
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get enabled
     * @return Boolean
     * @author KhoaNA
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Set enabled
     * @param   enabled
     *          type Boolean
     * @return
     * @author  KhoaNA
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get avatar
     * @return String
     * @author KhoaNA
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Set avatar
     * @param   avatar
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Get departmentId
     * @return Long
     * @author KhoaNA
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Set departmentId
     * @param   departmentId
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Get positionId
     * @return Long
     * @author KhoaNA
     */
    public Long getPositionId() {
        return positionId;
    }

    /**
     * Set positionId
     * @param   positionId
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setPositionCode(Long positionId) {
        this.positionId = positionId;
    }

    /**
     * Get branchId
     * @return Long
     * @author KhoaNA
     */
    public Long getBranchId() {
        return branchId;
    }

    /**
     * Set branchId
     * @param   branchId
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    /**
     * Get url
     * @return String
     * @author thuydtn
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url
     * @param   url
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setUrl(String url) {
        this.url = url;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Boolean getInternal() {
		return internal;
	}

	public void setInternal(Boolean internal) {
		this.internal = internal;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	/**
	 * @return the searchKeyIds
	 * @author taitm
	 */
	public List<String> getSearchKeyIds() {
		return searchKeyIds;
	}

	/**
	 * @param searchKeyIds the searchKeyIds to set
	 * @author taitm
	 */
	public void setSearchKeyIds(List<String> searchKeyIds) {
		this.searchKeyIds = searchKeyIds;
	}

	/**
	 * @return the searchValue
	 * @author taitm
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @param searchValue
	 *            the searchValue to set
	 * @author taitm
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

}
