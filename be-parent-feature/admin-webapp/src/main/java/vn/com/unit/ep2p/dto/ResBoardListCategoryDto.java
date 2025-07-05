package vn.com.unit.ep2p.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ResBoardListCategoryDto
 * @author TaiTT
 *10-7-2019
 */
public class ResBoardListCategoryDto {

	private Long categoryId;
	
	private String categoryName;
	
	List<ResBoardListServicesDto> services;
	
	 private Long totalServices;
	 
	 @JsonInclude(Include.NON_NULL)
	 private String functionCode;
	 
	 @JsonInclude(Include.NON_NULL)
	 private Long companyId;
	
	/**
	* Get CategoryId
	* @return categoryId
	* @author TaiTT
	*/
	public Long getCategoryId() {
		return categoryId;
	}
	
	/**
	* set CategoryId
	* @param categoryId
	* 	type Long
	* @author TaiTT
	*/
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	/**
	* Get CategoryName
	* @return categoryName
	* @author TaiTT
	*/
	public String getCategoryName() {
		return categoryName;
	}
	
	/**
	* set CategoryName
	* @param categoryName
	* 	type String
	* @author TaiTT
	*/
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	/**
	* Get ServicesList
	* @return servicesList
	* @author TaiTT
	*/
	public List<ResBoardListServicesDto> getServices() {
		return services;
	}
	
	/**
	* set ServicesList
	* @param servicesList
	* 	type List
	* @author TaiTT
	*/
	public void setServices(List<ResBoardListServicesDto> services) {
		this.services = services;
	}

	public Long getTotalServices() {
		return totalServices;
	}

	public void setTotalServices(Long totalServices) {
		this.totalServices = totalServices;
	}

	/**
	 * Get functionCode
	 * @return String
	 * @author taitt
	 */
	public String getFunctionCode() {
		return functionCode;
	}

	/**
	 * Set functionCode
	 * @param   functionCode
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	/**
	 * Get companyId
	 * @return String
	 * @author taitt
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * Set companyId
	 * @param   companyId
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
