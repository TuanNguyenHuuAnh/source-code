package vn.com.unit.ep2p.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ResBoardListDto
 * @author TaiTT
 *10-7-2019
 */
public class ResBoardListDto {
	
	private Long companyId;
	
	private String companyName;
	
	private String systemCode;
	
	private String systemName;
	
	private String logoPath;
	
	private Long logoRepoId;
	
	private List<ResBoardListCategoryDto> categories;
	
	
	
	// list temp Dto Service
	@JsonInclude(Include.NON_NULL)
	private Long categoryId;
	@JsonInclude(Include.NON_NULL)
	private Long serviceId;
	@JsonInclude(Include.NON_NULL)
	private Long totalDocument;
	
	/**
	* Get CompanyId
	* @return companyId
	* @author TaiTT
	*/
	public Long getCompanyId() {
		return companyId;
	}
	
	/**
	* set CompanyId
	* @param companyId
	* 	type Long
	* @author TaiTT
	*/
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	/**
	* Get CompanyName
	* @return companyName
	* @author TaiTT
	*/
	public String getCompanyName() {
		return companyName;
	}
	
	/**
	* set CompanyName
	* @param companyName
	* 	type String
	* @author TaiTT
	*/
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	/**
	* Get SystemCode
	* @return systemCode
	* @author TaiTT
	*/
	public String getSystemCode() {
		return systemCode;
	}
	
	/**
	* set SystemCode
	* @param systemCode
	* 	type String
	* @author TaiTT
	*/
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	
	/**
	* Get SystemName
	* @return systemName
	* @author TaiTT
	*/
	public String getSystemName() {
		return systemName;
	}
	
	/**
	* set SystemName
	* @param systemName
	* 	type String
	* @author TaiTT
	*/
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	/**
	* Get LogoPath
	* @return logoPath
	* @author TaiTT
	*/
	public String getLogoPath() {
		return logoPath;
	}
	
	/**
	* set LogoPath
	* @param logoPath
	* 	type String
	* @author TaiTT
	*/
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	/**
	* Get LogoRepoId
	* @return logoRepoId
	* @author TaiTT
	*/
	public Long getLogoRepoId() {
		return logoRepoId;
	}
	
	/**
	* set LogoRepoId
	* @param logoRepoId
	* 	type String
	* @author TaiTT
	*/
	public void setLogoRepoId(Long logoRepoId) {
		this.logoRepoId = logoRepoId;
	}

	public List<ResBoardListCategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(List<ResBoardListCategoryDto> categories) {
		this.categories = categories;
	}

	/**
	 * Get categoryId
	 * @return Long
	 * @author taitt
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Set categoryId
	 * @param   categoryId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Get serviceId
	 * @return Long
	 * @author taitt
	 */
	public Long getServiceId() {
		return serviceId;
	}

	/**
	 * Set serviceId
	 * @param   serviceId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * Get totalDocument
	 * @return Long
	 * @author taitt
	 */
	public Long getTotalDocument() {
		return totalDocument;
	}

	/**
	 * Set totalDocument
	 * @param   totalDocument
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setTotalDocument(Long totalDocument) {
		this.totalDocument = totalDocument;
	}


	
	
 	
}
