/*******************************************************************************
 * Class        ：JcaCompanyService
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：NganNH
 * Change log   ：2020/12/07：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.dto.JcaCompanySearchDto;
import vn.com.unit.core.entity.JcaCompany;
import vn.com.unit.db.service.DbRepositoryService;

/**
 * JcaCompanyService.
 *
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface JcaCompanyService extends DbRepositoryService<JcaCompany, Long> {

    /** The Constant TABLE_ALIAS_JCA_COMPANY. */
    static final String TABLE_ALIAS_JCA_COMPANY = "COMPANY";

    /**
     * Total company by condition.
     *
     * @param companySearchDto
     *            the company search dto
     * @return int
     * @author NganNH
     */
    public int countCompanyByCondition(JcaCompanySearchDto companySearchDto);

    /**
     * Get all company by condition .
     *
     * @param companySearchDto
     *            the company search dto
     * @param pageable
     *            the pageable
     * @return List<JcaCompanyDto>
     * @author NganNH
     */
    public List<JcaCompanyDto> getCompanyByCondition(JcaCompanySearchDto companySearchDto, Pageable pageable);

    /**
     * getCompanyById.
     *
     * @param companyId
     *            the company id
     * @return JcaCompany
     * @author NganNH
     */
    public JcaCompany getCompanyById(Long companyId);

    /**
     * saveJcaCompany.
     *
     * @param jcaCompany
     *            the jca company
     * @return the jca company
     * @author ngannh
     */
    public JcaCompany saveJcaCompany(JcaCompany jcaCompany);

    /**
     * saveJcaCompanyDto.
     *
     * @param jcaCompanyDto
     *            the jca company dto
     * @return JcaCompany
     * @author ngannh
     */
    public JcaCompany saveJcaCompanyDto(JcaCompanyDto jcaCompanyDto);

    /**
     * getJcaCompanyDtoById.
     *
     * @param id
     *            the id
     * @return the jca company dto by id
     * @author ngannh
     */
    JcaCompanyDto getJcaCompanyDtoById(Long id);

    /**
     * getSystemCodeByCompanyId.
     *
     * @param companyId
     *            type {@link Long}
     * @return the system code by company id
     * @author taitt
     */
    String getSystemCodeByCompanyId(Long companyId);

    /**
     * <p>
     * Delete jca company by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author sonnd
     */
    public void deleteJcaCompanyById(Long id);

    /**
     * <p>
     * Count jca company dto by system code.
     * </p>
     *
     * @param systemCode
     *            type {@link String}
     * @return {@link int}
     * @author sonnd
     */
    public int countJcaCompanyDtoBySystemCode(String systemCode);

    /**
     * <p>
     * Count jca company dto by system name.
     * </p>
     *
     * @param systemName
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return {@link int}
     * @author sonnd
     */
    public int countJcaCompanyDtoBySystemName(String systemName, Long companyId);

}
