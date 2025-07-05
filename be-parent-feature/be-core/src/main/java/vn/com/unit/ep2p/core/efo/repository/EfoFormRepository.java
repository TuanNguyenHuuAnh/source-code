/*******************************************************************************
 * Class        :EfoFormRepository
 * Created date :2019/04/17
 * Lasted date  :2019/04/17
 * Author       :NhanNV
 * Change log   :2019/04/17:01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormSearchDto;
import vn.com.unit.ep2p.core.efo.entity.EfoForm;


/**
 * EfoFormRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
public interface EfoFormRepository extends DbRepository<EfoForm, Long> {

    /**
     * findEfoFormByRolesAccountId.
     *
     * @param efoFormSearchDto
     *            type {@link EfoFormSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return List for EfoForm object
     * @author taitt
     */
    Page<EfoFormDto> getEfoFormDtoByCondition(@Param("efoFormSearchDto") EfoFormSearchDto efoFormSearchDto, Pageable pageable
    		,@Param("langCode") String langCode, @Param("accountId") Long accountId, @Param("dbType") String dbType);
    
    /**
     * countByRolesAccountIdAndConditionsSearch.
     *
     * @param efoFormSearchDto
     *            type {@link EfoFormSearchDto}
     * @return int for total item list
     * @author taitt
     */
    int countEfoFormByCondition(@Param("efoFormSearchDto")EfoFormSearchDto efoFormSearchDto, @Param("accountId")Long accountId);
    
    /**
     * <p>
     * Get select 2 dto with efo form by company id.
     * </p>
     *
     * @param companyId
     *            type {@link Long}
     * @param keySearch
     *            type {@link String}
     * @return {@link List<Select2Dto>}
     * @author taitt
     */
	List<Select2Dto> getSelect2DtoWithEfoFormByCompanyId(@Param("companyId") Long companyId,
			@Param("keySearch") String keySearch, @Param("accountId") Long accountId, @Param("dbType") String dbType);
    
    /**
     * <p>
     * Get efo form dto by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link EfoFormDto}
     * @author taitt
     */
    EfoFormDto getEfoFormDtoById(@Param("id")Long id);
    
    List<EfoFormDto> getEfoFormDtoByCompanyIdAndFileName(@Param("companyId")Long id, @Param("fileNameList") List<String> fileNameList);
    
    EfoFormDto getEfoFormDtoByCompanyIdAndEfoFormName(@Param("companyId")Long companyId, @Param("efoFormName") String efoFormName, @Param("currentFormId") Long currentFormId);
    
    Long findMaxDisplayOrderByCompanyId(@Param("companyId") Long companyId);
    
}