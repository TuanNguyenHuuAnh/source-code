package vn.com.unit.ep2p.admin.repository;

import java.util.Date;
import java.util.List;

import vn.com.unit.core.dto.JcaRoleForCompanyDto;
import vn.com.unit.core.entity.JcaRoleForCompany;
import vn.com.unit.db.repository.DbRepository;
import jp.xet.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

/**
 * RoleForCompanyRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author DaiTrieu
 */
public interface RoleForCompanyRepository extends DbRepository<JcaRoleForCompany, Long> {
    
	/**
	 * findRoleForCompanyByRoleId
	 * @param roleId
	 * @param companyIds
	 * @param isAdmin
	 * @return
	 * @author DaiTrieu
	 */
	public List<JcaRoleForCompanyDto> findRoleForCompanyByRoleId(@Param("roleId") Long roleId, @Param("companyIds") List<Long> companyIds, @Param("isAdmin") Boolean isAdmin);
	
	/**
	 * deleteRoleForCompanyByRoleId
	 * @param roleId
	 * @param userNameLogin
	 * @param systemDate
	 * @author DaiTrieu
	 */
	@Modifying
	void deleteRoleForCompanyByRoleId(@Param("roleId") Long roleId, @Param("userNameLogin") String userNameLogin, @Param("systemDate") Date systemDate);

    
    /**
     * getListRoleForCompanyPageWrapper
     * @param offsetSQL
     * @param sizeOfPage
     * @param roleId
     * @param companyIds
     * @param isAdmin
     * @return
     * @author DaiTrieu
     */
    public List<JcaRoleForCompanyDto> getListRoleForCompanyPageWrapper(@Param("offsetSQL") int offsetSQL, @Param("sizeOfPage") int sizeOfPage, @Param("roleId") Long roleId, @Param("companyIds") List<Long> companyIds, @Param("isAdmin") boolean isAdmin);
    
    /**
     * findByCondition
     * @param roleId
     * @param companyId
     * @param orgId
     * @return
     * @author DaiTrieu
     */
    public JcaRoleForCompanyDto findByCondition(@Param("roleId") Long roleId, @Param("companyId") Long companyId, @Param("orgId") Long orgId);
    
    /**
     * countRoleForCompanyByRoleId
     * @param roleId
     * @param companyIds
     * @param isAdmin
     * @return
     * @author DaiTrieu
     */
    public int countRoleForCompanyByRoleId(@Param("roleId") Long roleId, @Param("companyIds") List<Long> companyIds, @Param("isAdmin") Boolean isAdmin);
    
    @Modifying
    public void deleteByCompanyIdAndOrgIdAndRoleId(@Param("roleId") Long roleId, @Param("companyId") Long companyId, @Param("orgId") Long orgId);
}
