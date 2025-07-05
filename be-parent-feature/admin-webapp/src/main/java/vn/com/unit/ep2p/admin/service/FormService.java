/*******************************************************************************
 * Class        FormService
 * Created date 2019/04/12
 * Lasted date  2019/04/12
 * Author       KhoaNA
 * Change log   2019/04/12 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.sql.SQLException;
import java.util.List;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.entity.EfoForm;
import vn.com.unit.ep2p.dto.LanguageMapDto;


/**
 * FormService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface FormService {

	/**
     * getFormById
     * @param id
     * @return
     * @author KhoaNA
     */
	EfoForm getFormById(Long id);
	
	EfoFormDto getFormDtoById(Long id, String lang); 
	
    /**
     * getFormListByCompanyId
     * 
     * @param keySearch
     * @param companyId
     * @param accountId
     * @param isPaging
     * @return
     * @author HungHT
     */
	List<Select2Dto> getFormListByCompanyId(String keySearch, Long companyId, Long accountId, boolean isPaging, String lang);
	
	/**
	 * getStatusListByCompanyIdAndFormId
	 * @param keySearch
	 * @param companyId
	 * @param formId
	 * @param isPaging
	 * @param lang
	 * @return
	 * @author KhoaNA
	 */
	List<Select2Dto> getStatusListByCompanyIdAndFormId(String keySearch, Long companyId, Long processId, boolean isPaging, String lang);
	
	/**
	 * getStatusListCommon
	 * @return List<Select2Dto>
	 * @author KhoaNA
	 */
	List<Select2Dto> getStatusListCommon();
	
	/**
	 * findByCompanyId
	 * @param companyId
	 * @return
	 * @author trieuvd
	 */
	List<Select2Dto> findByCompanyId(Long companyId);
	
	/**
	 * 
	 * getBusinessIdByFormId
	 * @param formId
	 * @return
	 * @author KhuongTH
	 */
	Long getBusinessIdByFormId(Long formId);
	
	/**
	 * getFormIdByBusinessId
	 *
	 * @param businessId
	 * @return Long
	 * @author KhuongTH
	 */
	Long getFormIdByBusinessId(Long businessId);
	
	/**
	 * @param companyId
	 * @param docType
	 * @return
	 * @throws SQLException
	 */
	List<Long> getIdsByDocType(Long companyId, String docType) throws SQLException;
	
	/**
	 * @param keySearch
	 * @param companyId
	 * @param userName
	 * @param isPaging
	 * @return
	 */
	List<Select2Dto> getListByCompanyIdAndUserName(String keySearch, Long companyId, String userName, List<String> processTypeIgnores, boolean isPaging, String lang) throws SQLException;
	
	/**
	 * 
	 * getListByCompanyIdAndUserName
	 * @param keySearch
	 * @param companyId
	 * @param userName
	 * @param processTypeIgnores
	 * @param lang
	 * @param pageIndex
	 * @param pageSize
	 * @param isPaging
	 * @return
	 * @throws SQLException
	 * @author taitt
	 */
    List<Select2Dto> getListByCompanyIdAndUserName(String keySearch, Long companyId, String userName, List<Integer> processTypeIgnores,
            String lang, int pageIndex, int pageSize, int isPaging) throws SQLException;
	
    Long countListByKeySearchAndCompanyId(String keySearch, Long companyId, String userName, List<Integer> processTypeIgnores, String lang,
            int pageIndex, int pageSize, int isPaging) throws SQLException;
	
	/**
	 * @param formId
	 * @return
	 * @throws SQLException
	 */
	List<LanguageMapDto> getFormNameListById(Long formId) throws SQLException;
	
	/**
	 * 
	 * getFormDtoByBusinessId
	 * @param businessId
	 * @param lang
	 * @return
	 * @author taitt
	 */
	EfoFormDto getFormDtoByBusinessId(Long businessId,String lang);
	
	/**
	 * getSelect2DtoForUser
	 * @param keySearch
	 * @param companyId
	 * @param accountId
	 * @param processTypeIgnores
	 * @param isPaging
	 * @param lang
	 * @return
	 * @author trieuvd
	 */
	List<Select2Dto> getSelect2DtoForUser(String keySearch, Long companyId, Long accountId, List<String> processTypeIgnores, boolean isPaging, String lang);
	
}
