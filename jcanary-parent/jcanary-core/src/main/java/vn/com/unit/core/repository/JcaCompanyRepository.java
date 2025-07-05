/*******************************************************************************
 * Class        ：JcaCompanyRepository
 * Created date ：2020/12/07
 * Lasted date  ：2020/12/07
 * Author       ：NganNH
 * Change log   ：2020/12/07：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.dto.JcaCompanySearchDto;
import vn.com.unit.core.entity.JcaCompany;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaCompanyRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface JcaCompanyRepository extends DbRepository<JcaCompany, Long> {
    /**
     * 
     * Total company by condition
     * 
     * @param keySearch
     * @param companySearchDto
     * @return int 
     * @author NganNH
     */
    int countCompanyByCondition( @Param("companySearchDto") JcaCompanySearchDto companySearchDto);
    
    /**
     * 
     * Get all company by condition
     * 
     * @param keySearch
     * @param companySearchDto
     * @param pageSize
     * @param startIndex
     * @return List<JcaCompanyDto>
     * @author NganNH
     */
    public Page<JcaCompanyDto> getCompanyByCondition( @Param("companySearchDto") JcaCompanySearchDto companySearchDto,Pageable pageable);

    /**
     * getJcaCompanyDtoById
     * @param id
     * @return
     * @author ngannh
     */
    public JcaCompanyDto getJcaCompanyDtoById(@Param("id")Long id);

    int countJcaCompanyDtoBySystemCode(@Param("systemCode")String systemCode);

    int countJcaCompanyDtoBySystemName(@Param("systemName")String systemName,@Param("companyId") Long companyId);
    
}

