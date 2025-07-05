/*******************************************************************************
 * Class        AccountEditDto
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/2101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;
import java.util.List;

/**
 * AccountEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class AccountDetailDto {

	/** accountId */
	private Long id;

	/** username */
	private String username;

	/** fullname */
	private String fullname;

	/** email */
	private String email;

	/** birthday */
	private Date birthday;

	/** phone */
	private String phone;

	/** enabled */
	private Boolean enabled;

	/**
	 * status code
	 */
	private String statusCode;

	/** avatar */
	private String avatar;

	/** departmentId */
	private String departmentName;

	/** positionId */
	private String positionName;

	/** branchId */
	private String branchName;

	private List<RoleForAccountDto> listRoleForAccount;

	private String url;

	private List<Long> lstTeamId;

	private String teamName;
	
	private String departmentCode;
	
	private String reportingToCodes;
	
    private String costCenterCodes;
    
	private String costCenterCode;
	
	private String costCenterName;
	
	private int lockedFlag;
	
	private Long branchId;
	

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	/**
	 * Get id
	 * 
	 * @return Long
	 * @author KhoaNA
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id type Long
	 * @return
	 * @author KhoaNA
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get username
	 * 
	 * @return String
	 * @author KhoaNA
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username
	 * 
	 * @param username type String
	 * @return
	 * @author KhoaNA
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get fullname
	 * 
	 * @return String
	 * @author KhoaNA
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * Set fullname
	 * 
	 * @param fullname type String
	 * @return
	 * @author KhoaNA
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * Get email
	 * 
	 * @return String
	 * @author KhoaNA
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set email
	 * 
	 * @param email type String
	 * @return
	 * @author KhoaNA
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get birthday
	 * 
	 * @return Date
	 * @author KhoaNA
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * Set birthday
	 * 
	 * @param birthday type Date
	 * @return
	 * @author KhoaNA
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Get phone
	 * 
	 * @return String
	 * @author KhoaNA
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set phone
	 * 
	 * @param phone type String
	 * @return
	 * @author KhoaNA
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Get enabled
	 * 
	 * @return Boolean
	 * @author KhoaNA
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * Set enabled
	 * 
	 * @param enabled type Boolean
	 * @return
	 * @author KhoaNA
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get avatar
	 * 
	 * @return String
	 * @author KhoaNA
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Set avatar
	 * 
	 * @param avatar type String
	 * @return
	 * @author KhoaNA
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Get listRoleForAccount
	 * 
	 * @return List<RoleForAccountDto>
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public List<RoleForAccountDto> getListRoleForAccount() {
		return listRoleForAccount;
	}

	/**
	 * Set listRoleForAccount
	 * 
	 * @param listRoleForAccount type List<RoleForAccountDto>
	 * @return
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public void setListRoleForAccount(List<RoleForAccountDto> listRoleForAccount) {
		this.listRoleForAccount = listRoleForAccount;
	}

	/**
	 * Get departmentName
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * Set departmentName
	 * 
	 * @param departmentName type Long
	 * @return
	 * @author thuydtn
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * Get positionName
	 * 
	 * @return Long
	 * @author thuydtn
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * Set positionName
	 * 
	 * @param positionName type Long
	 * @return
	 * @author thuydtn
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * Get branchName
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * Set branchName
	 * 
	 * @param branchName type String
	 * @return
	 * @author thuydtn
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * Get statusCode
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * Set statusCode
	 * 
	 * @param statusCode type String
	 * @return
	 * @author thuydtn
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Get url
	 * 
	 * @return String
	 * @author thuydtn
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set url
	 * 
	 * @param url type String
	 * @return
	 * @author thuydtn
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Get lstTeamId
	 * 
	 * @return List<Long>
	 * @author VinhLT
	 */
	public List<Long> getLstTeamId() {
		return lstTeamId;
	}

	/**
	 * Set lstTeamId
	 * 
	 * @param lstTeamId type List<Long>
	 * @return
	 * @author VinhLT
	 */
	public void setLstTeamId(List<Long> lstTeamId) {
		this.lstTeamId = lstTeamId;
	}

	/**
	 * Get teamName
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * Set teamName
	 * 
	 * @param teamName type String
	 * @return
	 * @author VinhLT
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getReportingToCodes() {
		return reportingToCodes;
	}

	public void setReportingToCodes(String reportingToCodes) {
		this.reportingToCodes = reportingToCodes;
	}

	public String getCostCenterCodes() {
		return costCenterCodes;
	}

	public void setCostCenterCodes(String costCenterCodes) {
		this.costCenterCodes = costCenterCodes;
	}

	public String getCostCenterCode() {
		return costCenterCode;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public int getLockedFlag() {
		return lockedFlag;
	}

	public void setLockedFlag(int lockedFlag) {
		this.lockedFlag = lockedFlag;
	}
	
}
