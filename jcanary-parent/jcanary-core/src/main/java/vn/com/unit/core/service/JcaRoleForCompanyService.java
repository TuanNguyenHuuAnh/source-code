/*******************************************************************************
 * Class        ：JcaRoleForCompany
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaRoleForCompanyDto;
import vn.com.unit.core.dto.JcaRoleForCompanySearchDto;
import vn.com.unit.core.entity.JcaRoleForCompany;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaRoleForCompany.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface JcaRoleForCompanyService extends DbRepositoryService<JcaRoleForCompany, Long>{
    
     /** The Constant TABLE_ALIAS_JCA_ROLE_FOR_COMPANY. */
     static final String TABLE_ALIAS_JCA_ROLE_FOR_COMPANY = "rolecompany";

    /**
     * getRoleForCompanyDtoByCondition.
     *
     * @param reqSearch
     *            the req search
     * @param pageableAfterBuild
     *            the pageable after build
     * @return the role for company dto by condition
     * @author ngannh
     */
    List<JcaRoleForCompanyDto> getRoleForCompanyDtoByCondition(JcaRoleForCompanySearchDto reqSearch, Pageable pageableAfterBuild);

    /**
     * countRoleForCompanyDtoByCondition.
     *
     * @param reqSearch
     *            the req search
     * @return the int
     * @author ngannh
     */
    int countRoleForCompanyDtoByCondition(JcaRoleForCompanySearchDto reqSearch);

    /**
     * saveJcaRoleForCompanyDto.
     *
     * @param objectDto
     *            the object dto
     * @return the jca role for company
     * @author ngannh
     */
    public JcaRoleForCompany saveJcaRoleForCompanyDto(JcaRoleForCompanyDto objectDto);


    /**
     * getJcaRoleForCompanyById.
     *
     * @param id
     *            the id
     * @return the jca role for company by id
     * @author ngannh
     */
    JcaRoleForCompany getJcaRoleForCompanyById(Long id);

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
    List<JcaRoleForCompanyDto> getJcaRoleForCompanyByRoleId(Long roleId, Long companyId);

    /**
     * <p>
     * Delete all role by role id.
     * </p>
     *
     * @param roleId
     *            type {@link Long}
     * @author SonND
     */
    void deleteAllRoleByRoleId(Long roleId);
    
    //////New///////
    
    public JcaRoleForCompanyDto findJcaRoleForCompanyById(Long orgId, Long roleId, Long companyId);
    
    public void deleteJcaRoleForCompanyById(Long orgId, Long roleId, Long companyId);

}
