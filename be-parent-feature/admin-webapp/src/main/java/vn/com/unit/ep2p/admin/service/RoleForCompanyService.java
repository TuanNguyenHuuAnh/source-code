package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.JcaRoleForCompanyDto;
import vn.com.unit.ep2p.dto.RoleForCompanyEditDto;

/**
 * RoleForCompanyService
 * 
 * @version 01-00
 * @since 01-00
 * @author DaiTrieu
 */
public interface RoleForCompanyService {
    
	/**
	 * getListRoleForCompany
	 * @param roleId
	 * @return
	 * @author DaiTrieu
	 */
	public List<JcaRoleForCompanyDto> getListRoleForCompany(Long roleId);
	
	/**
	 * saveRoleForCompany
	 * @param roleCompanyDto
	 * @param locale
	 * @return
	 * @author trieuvd
	 * @throws Exception 
	 */
	Boolean saveRoleForCompany(RoleForCompanyEditDto roleCompanyDto, Locale locale) throws Exception;
	
	/**
	 * deleteById
	 * @param id
	 * @author DaiTrieu
	 */
	void deleteById(Long id);
	
	/**
	 * validateRoleForCompay
	 * @param roleCompany
	 * @param locale
	 * @return
	 * @author trieuvd
	 */
	String validateRoleForCompay(RoleForCompanyEditDto roleCompany, Locale locale);
	
	/**
	 * getListRoleForCompanyPageWrapper
	 * @param page
	 * @param pageSize
	 * @param roleId
	 * @return
	 * @author DaiTrieu
	 */
	public PageWrapper<JcaRoleForCompanyDto> getListRoleForCompanyPageWrapper (int page, int pageSize, Long roleId);
	
	public void deleteByCompanyIdAndOrgIdAndRoleId(Long companyId, Long orgId, Long roleId);
}
