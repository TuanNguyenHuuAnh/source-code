package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.admin.dto.AuthorityDetailDto;

import org.springframework.data.repository.query.Param;

public interface AuthorityDetailRepository extends DbRepository<Object, Long>  {
	
	List<AuthorityDetailDto> searchAuthorityDetailOracle(@Param("authorityDetailDto") AuthorityDetailDto authorityDetailDto,@Param("constEnable") String constEnable,@Param("constDisable") String constDisable,@Param("constActivated") String constActivated,@Param("constInactive") String constInactive);
	
	int countRecordsOracle(@Param("authorityDetailDto") AuthorityDetailDto authorityDetailDto);
	/**
	 * searchAuthorityDetailOracle
	 *
	 * @param authorityDetailDto
	 * @param constEnable
	 * @param constDisable
	 * @param constActivated
	 * @param constInactive
	 * @return
	 * @author phatvt
	 */
	List<AuthorityDetailDto> searchAuthorityDetailSQL(@Param("authorityDetailDto") AuthorityDetailDto authorityDetailDto,@Param("constEnable") String constEnable,@Param("constDisable") String constDisable,@Param("constActivated") String constActivated,@Param("constInactive") String constInactive);
    /**
     * countRecordsOracle
     *
     * @param authorityDetailDto
     * @return
     * @author phatvt
     */
    int countRecordsSQL(@Param("authorityDetailDto") AuthorityDetailDto authorityDetailDto);
    
	/**
	 * find AuthorityDetailDto List By FunctionCode And CompanyId
	 *
	 * @param functionCodeList
	 * 			type List<String>
	 * @param companyId
	 * 			type Long
	 * @return List<AuthorityDetailDto>
	 * @author KhoaNA
	 */
    List<AuthorityDetailDto> findAuthorityDetailDtoListByFunctionCodeAndCompanyId(@Param("functionCodeList") List<String> functionCodeList, @Param("companyId") Long companyId);
    
    /**
	 * find AuthorityDetailDto List By Username, RoleId And FunctionType.
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
    List<AuthorityDetailDto> findAuthorityDetailDtoListByUserNameAndRoleIdAndFunctionType(@Param("username") String username, @Param("roleId") Long roleId
    			, @Param("functionType") String functionType, @Param("companyId") Long companyId);
}
