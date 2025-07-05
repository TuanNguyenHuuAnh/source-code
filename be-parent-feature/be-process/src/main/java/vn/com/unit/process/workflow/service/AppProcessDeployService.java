/*******************************************************************************
 * Class        AppProcessDeployService
 * Created date 2019/07/04
 * Lasted date  2019/07/04
 * Author       KhuongTH
 * Change log   2019/07/04 01-00 KhuongTH create a new
 ******************************************************************************/

package vn.com.unit.process.workflow.service;

import java.sql.SQLException;
import java.util.List;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.process.workflow.dto.AppProcessDeployDto;
import vn.com.unit.process.workflow.dto.AppProcessDeploySearchDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.dto.LanguageMapDto;

/**
 * AppProcessDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface AppProcessDeployService {

	/**
     * Get AppProcessDto by searchDto.
     * 
     * @param page
     *          type int
     * @param pageSize
     *          type int
     * @param searchDto
     *          type AppProcessSearchDto
     * @return PageWrapper<AppProcessDeployDto>
     * @author KhuongTH
	 * @throws DetailException 
     */
    PageWrapper<AppProcessDeployDto> search(int page, int pageSize, AppProcessDeploySearchDto searchDto) throws DetailException;
    
	/**
     * Get AppProcessDto by id.
     * 
     * @param id
     *          type Long
     * @return AppProcessDto
     * @author KhuongTH
     */
    AppProcessDeployDto getJpmProcessDeployDtoById(Long id);
    
    /**
     * Get AppProcessDto by code, companyId and deletedBy is null
     * 
     * @param code
     *          type String
     * @param companyId
     *          type Long
     * @return AppProcessDto
     * @author KhuongTH
     */
    AppProcessDeployDto getJpmProcessDeployByCodeAndCompanyId(String code, Long companyId);
    
    /**
     * Delete JpmProcess by id.
     *
     * @param  id
     * 			type Long
     * @return boolean
     * @author KhuongTH
     */
    boolean deletedById(Long id);

    /**
     * get by form id
     *
     * @param  businessId
     * 			type Long
     * @param processId
     * 			type Long
     * @return AppProcessDeployDto
     * @author KhuongTH
     */
	AppProcessDeployDto getByFormId(Long businessId, Long processId);
	
	/**
     * findJpmProcessDtoByBusinessId
     *
     * @param  businessId
     * 			type Long
     * @return List<AppProcessDeployDto>
     * @author KhuongTH
     */
	List<AppProcessDeployDto> findJpmProcessDtoByBusinessId(Long businessId, String lang);
	
	/**
     * Get AppProcessDto By BusinessId 
     *
     * @param  businessId
     * 			type Long
     * @return List<Select2Dto>
     * @author KhoaNA
     */
	List<Select2Dto> getJpmProcessDtoTypeSelect2DtoByBusinessId(Long businessId, String lang);
	
	/**
     * Get JpmProcessDeploy For Document
     * If processDeployId is null, it will get process deploy by max effective date
     *
     * @param  companyId
     * 			type Long
     * @param  businessId
     * 			type Long
     * @param  processDeployId
     * 			type Long
     * @return AppProcessDeployDto
     * @author KhoaNA
     */
	AppProcessDeployDto getJpmProcessDeployForDocument(Long companyId, Long businessId, Long processDeployId);
	
	/**
	 * getJpmProcessDeployListByFormId 
	 * 
	 * @param keySearch
	 * @param formId
	 * @param isPaging
	 * @return List<Select2Dto>
	 * @author KhoaNA
	 */
	List<Select2Dto> getJpmProcessDeployListByFormId(String keySearch, Long formId, boolean isPaging, String lang);
	
	List<AppProcessDeployDto> findJpmProcessDtoListByCompanyId(Long companyId) throws Exception;
    
    /**
     * exportJpmProcessDeploy.
     *
     * @param processDeployId type {@link Long}
     * @return JpmProcessExportDto
     * @author KhuongTH
     */
    public JpmProcessImportExportDto exportJpmProcessDeploy(Long processDeployId);
    
    /**
     * findJpmProcessDeployDtoListByProcessId
     * @param processId
     * @return List<AppProcessDeployDto>
     * @author KhuongTH
     */
    public List<AppProcessDeployDto> findJpmProcessDeployDtoListByProcessId(Long processId);
    
    String getLangByIdAndLangCode(Long processId, String lang);
    
    /**
     * @param processId
     * @return
     */
    List<LanguageMapDto> getProcessNameListById(Long processId) throws SQLException;
    
}
