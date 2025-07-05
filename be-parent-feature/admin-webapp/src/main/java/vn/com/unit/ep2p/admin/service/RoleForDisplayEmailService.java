package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.ep2p.admin.dto.RoleForDisplayEmailDto;
import vn.com.unit.ep2p.admin.dto.RoleForDisplayEmailEditDto;



/**
 * RoleForCompanyService
 * 
 * @version 01-00
 * @since 01-00
 * @author DaiTrieu
 */
public interface RoleForDisplayEmailService {
    
	/**
	 * getListRoleForDisplayEmail
	 * @param roleId
	 * @return
	 * @author trieuvd
	 */
	public List<RoleForDisplayEmailDto> getListRoleForDisplayEmail(Long roleId);
	
	
	/**
	 * saveRoleForDisplayEmail
	 * @param roleDisplayEmailEditDto
	 * @param locale
	 * @return
	 * @throws Exception
	 * @author trieuvd
	 */
	Boolean saveRoleForDisplayEmail(RoleForDisplayEmailEditDto roleDisplayEmailEditDto, Locale locale) throws Exception;
	
	/**
	 * deleteById
	 * @param id
	 * @author trieuvd
	 */
	void deleteById(Long id);
	
	/**
	 * validateRoleForDisplayEmail
	 * @param roleDisplayEmailEditDto
	 * @param locale
	 * @return
	 * @author trieuvd
	 */
	String validateRoleForDisplayEmail(RoleForDisplayEmailEditDto roleDisplayEmailEditDto, Locale locale);
}
