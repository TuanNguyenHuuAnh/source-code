/*******************************************************************************
 * Class        JpmBusinessService
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service;

import java.util.List;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.process.workflow.dto.AppBusinessDto;
import vn.com.unit.process.workflow.dto.AppBusinessSearchDto;
import vn.com.unit.workflow.entity.JpmBusiness;

/**
 * JpmBusinessService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AppBusinessService{
	/**
     * Get JpmBusinessDto by searchDto.
     * 
     * @param page
     *          type int
     * @param pageSize
     *          type int
     * @param searchDto
     *          type JpmBusinessSearchDto
     * @return PageWrapper<JpmBusinessDto>
     * @author KhoaNA
	 * @throws DetailException 
     */
    PageWrapper<AppBusinessDto> search(int page, int pageSize, AppBusinessSearchDto searchDto) throws DetailException;
    
	/**
     * Get JpmBusinessDto by id.
     * 
     * @param id
     *          type Long
     * @return JpmBusinessDto
     * @author KhoaNA
     */
    AppBusinessDto getAppBusinessDtoById(Long id);
    
    /**
     * Get JpmBusinessDto by code, companyId and deletedBy is null
     * 
     * @param code
     *          type String
     * @param companyId
     *          type Long
     * @return JpmBusinessDto
     * @author KhoaNA
     */
    AppBusinessDto getJpmBusinessByCodeAndCompanyId(String code, Long companyId);
    
    /**
     * Save JpmBusiness from JpmBusinessDto without auto version.
     * JpmBusinessHistory is not saved.
     *
     * @param  objectDto
     * 			type JpmBusinessDto
     * @return JpmBusiness
     * @author KhoaNA
     */
    public JpmBusiness saveJpmBusinessDto(AppBusinessDto objectDto);
    
    /**
     * Delete JpmBusiness by id.
     *
     * @param  id
     * 			type Long
     * @return boolean
     * @author KhoaNA
     */
    boolean deletedById(Long id);
    
    /**
     * get Select2Dto of JpmBusiness list by term, companyId, companyAdmin and isPaging
     * 
     * @param term
     * 			type String
     * @param companyId
     * 			type Long
     * @param companyAdmin
     * 			type boolean
     * @param isPaging
     * 			type boolean
     * @return List<Select2Dto>
     * @author KhoaNA
     */
    List<Select2Dto> getSelect2DtoListByTermAndCompanyId(String term, Long companyId, boolean companyAdmin, boolean isPaging);
    
    /**
     * find JpmBusinessDto list  by CompanyId.
     *
     * @param  companyId
     * 			type Long
     * @return List<JpmBusinessDto>
     * @author KhuongTH
     */
    List<AppBusinessDto> findJpmBusinessDtoListByCompanyId(Long companyId);
    
    /**
     * find JpmBusinessDto list  by CompanyId.
     *
     * @param  companyId
     * 			type Long
     * @return List<Select2Dto>
     * @author KhuongTH
     */
    List<Select2Dto> getSelect2DtoListCompanyId(Long companyId);
    
    /**
     * getSelect2DtoListHasAuthorityByCompanyId
     *
     * @param companyId
     * @return List<Select2Dto>
     * @author KhuongTH
     */
    List<Select2Dto> getSelect2DtoListHasAuthorityByCompanyId(Long companyId);
    
    /**
     * updateHasParamById
     * @param id
     * @param hasParam
     * @return boolean
     * @author KhuongTH
     */
    boolean updateHasParamById(Long id, boolean hasParam);
    
    /**
     * findSelect2DtoUnregisteredByCompanyId
     * @param keySearch
     * @param companyId
     * @param formType
     * @param isPaging
     * @return
     * @author trieuvd
     */
    public List<Select2Dto> findSelect2DtoUnregisteredByCompanyId(String keySearch, Long companyId, int formType, boolean isPaging);

}
