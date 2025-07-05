package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.admin.dto.AuthorityDetailDto;

public interface AuthorityDetailService {
	
	public PageWrapper<AuthorityDetailDto> search(AuthorityDetailDto authorityDetailDto,int page, int pageSize,Locale locale);
	
	void exportExcel(AuthorityDetailDto authorityDetailDto, HttpServletResponse response, Locale locale);
	
    /**
     * Get AuthorityDetailDto list by functionCode list and companyId
     * @param functionCodeList
     * 			type List<String>
     * @param companyId
     * 			type Long
     * @return List<AuthorityDetailDto>
     * @author KhoaNA
     */
    List<AuthorityDetailDto> getAuthorityDetailDtoListByFunctionCode(List<String> functionCodeList, Long companyId);
    
    /**
     * Get AuthorityDetailDto list by userName, roleId and functionType
     * roleId is Option
     * 
     * @param username
     * 			type String
     * @param roleId
     * 			type Long
     * @param functionType
     * 			type String
     * @param companyId
     * 			type Long
     * @return List<AuthorityDetailDto>
     * @author KhoaNA
     */
    List<AuthorityDetailDto> getAuthorityDetailDtoListByUserNameAndRoleIdAndFunctionType(String username, Long roleId, String functionType, Long companyId);
}
