package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import vn.com.unit.common.dto.AbstractCompanyDto;

public class AuthorityDetailDto extends AbstractCompanyDto{
	private String grantedType;
	private String username;
	private String fullname;
	private String email;
	private String enabled;
	private String actived;
	private String orgCode;
	private String orgName;
	private String positionCode;
	private String positionName;
	private String groupCode;
	private String groupName;
	private String roleCode;
	private String roleName;
	private String roleActived;
	private String functionCode;
	private String functionName;
	private String accessRight;
	//for paging
	private int currentPage;
	private int sizePage;
	private String fieldSearch;
	private List<String> fieldValues;
	
	private String token;
	
	private String passExport;
	
	// KhoaNA - 20190909
	private Long accountId;
	
	/**
	 * @return the fieldSearch
	 */
	public String getFieldSearch() {
		return fieldSearch;
	}
	/**
	 * @param fieldSearch the fieldSearch to set
	 */
	public void setFieldSearch(String fieldSearch) {
		this.fieldSearch = fieldSearch;
	}
	/**
	 * @return the fieldValues
	 */
	public List<String> getFieldValues() {
		return fieldValues;
	}
	/**
	 * @param fieldValues the fieldValues to set
	 */
	public void setFieldValues(List<String> fieldValues) {
		this.fieldValues = fieldValues;
	}
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the sizePage
	 */
	public int getSizePage() {
		return sizePage;
	}
	/**
	 * @param sizePage the sizePage to set
	 */
	public void setSizePage(int sizePage) {
		this.sizePage = sizePage;
	}
	/**
	 * @return the grantedType
	 */
	public String getGrantedType() {
		return grantedType;
	}
	/**
	 * @param grantedType the grantedType to set
	 */
	public void setGrantedType(String grantedType) {
		this.grantedType = grantedType;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}
	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the actived
	 */
	public String getActived() {
		return actived;
	}
	/**
	 * @param actived the actived to set
	 */
	public void setActived(String actived) {
		this.actived = actived;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the positionCode
	 */
	public String getPositionCode() {
		return positionCode;
	}
	/**
	 * @param positionCode the positionCode to set
	 */
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}
	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	/**
	 * @return the groupCode
	 */
	public String getGroupCode() {
		return groupCode;
	}
	/**
	 * @param groupCode the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}
	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the roleActived
	 */
	public String getRoleActived() {
		return roleActived;
	}
	/**
	 * @param roleActived the roleActived to set
	 */
	public void setRoleActived(String roleActived) {
		this.roleActived = roleActived;
	}
	/**
	 * @return the functionCode
	 */
	public String getFunctionCode() {
		return functionCode;
	}
	/**
	 * @param functionCode the functionCode to set
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}
	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	/**
	 * @return the accessRight
	 */
	public String getAccessRight() {
		return accessRight;
	}
	/**
	 * @param accessRight the accessRight to set
	 */
	public void setAccessRight(String accessRight) {
		this.accessRight = accessRight;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPassExport() {
		return passExport;
	}
	public void setPassExport(String passExport) {
		this.passExport = passExport;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
}
