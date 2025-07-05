/*******************************************************************************
 * Class        ：JcaRoleForCompanyRepository
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaRoleForCompanyDto;
import vn.com.unit.core.dto.JcaRoleForCompanySearchDto;
import vn.com.unit.core.entity.JcaRoleForCompany;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRoleForCompanyRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface JcaRoleForCompanyRepository extends DbRepository<JcaRoleForCompany, Long>  {

    /**
     * getRoleForCompanyDtoByCondition.
     *
     * @param reqSearch
     *            type {@link JcaRoleForCompanySearchDto}
     * @param pageableAfterBuild
     *            type {@link Pageable}
     * @return {@link Page<JcaRoleForCompanyDto>}
     * @author ngannh
     */
    Page<JcaRoleForCompanyDto> getRoleForCompanyDtoByCondition(@Param("jcaRoleForCompanySearchDto") JcaRoleForCompanySearchDto reqSearch, Pageable pageableAfterBuild);

    /**
     * countRoleForCompanyDtoByCondition.
     *
     * @param reqSearch
     *            type {@link JcaRoleForCompanySearchDto}
     * @return {@link int}
     * @author ngannh
     */
    int countRoleForCompanyDtoByCondition(@Param("jcaRoleForCompanySearchDto") JcaRoleForCompanySearchDto reqSearch);

    /**
     * <p>
     * Get jca role for company by role id.
     * </p>
     *
     * @param roleId
     *            type {@link Long}
     * @param companyId
     *            type {@link Long}
     * @return {@link List<JcaRoleForCompanyDto>}
     * @author SonND
     */
    List<JcaRoleForCompanyDto> getJcaRoleForCompanyByRoleId(@Param("roleId")Long roleId, @Param("companyId") Long companyId);

    /**
     * <p>
     * Delete all role by role id.
     * </p>
     *
     * @param roleId
     *            type {@link Long}
     * @author SonND
     */
    @Modifying
    void deleteAllRoleByRoleId(@Param("roleId")Long roleId);
    
    
    /////////New/////////////
    
    public JcaRoleForCompanyDto findJcaRoleForCompanyById(@Param("orgId")Long orgId, @Param("roleId")Long roleId, @Param("companyId")Long companyId);
    
    @Modifying
    public void deleteJcaRoleForCompanyById(@Param("orgId")Long orgId, @Param("roleId")Long roleId, @Param("companyId")Long companyId);
    
}
