/*******************************************************************************
 * Class        FormServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       KhoaNA
 * Change log   2016/06/21 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.repository.FormLangRepository;
import vn.com.unit.ep2p.admin.repository.FormRepository;
import vn.com.unit.ep2p.admin.service.FormService;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;
import vn.com.unit.ep2p.core.efo.entity.EfoForm;
import vn.com.unit.ep2p.dto.LanguageMapDto;
//import vn.com.unit.ies.workflow.repository.AppButtonDeployRepository;
//import vn.com.unit.ies.workflow.service.AppButtonDeployService;
//import vn.com.unit.ies.workflow.service.AppButtonService;
//import vn.com.unit.ies.workflow.service.AppProcessDeployService;
//import vn.com.unit.ies.workflow.service.AppStepDeployService;

/**
 * FormServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FormServiceImpl implements FormService {

	@Autowired
	private FormRepository formRepository;
	
	@Autowired
	FormLangRepository formLangRepository;
	
//	@Autowired
//	private JcaConstantService jcaConstantService;
	
//	@Autowired 
//	AppProcessDeployService appProcessDeployService;
	
//	@Autowired
//	AppStepDeployService appStepDeployService;
	
//	@Autowired
//	AppButtonDeployRepository appButtonDeployRepository;
	
//	@Autowired
//	AppButtonService appButtonService;
	
//	@Autowired
//	AppButtonDeployService appButtonDeployService;
	
	/**
     * getFormById
     * @param id
     * @return EfoForm
     * @author KhoaNA
     */
	public EfoForm getFormById(Long id) {
	    return null;
//		return formRepository.findOne(id);
	}

	public EfoFormDto getFormDtoById(Long id, String lang) {
	    return formRepository.getFormDtoById(id, lang);
	}
	
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
    public List<Select2Dto> getFormListByCompanyId(String keySearch, Long companyId, Long accountId, boolean isPaging, String lang) {
        return formRepository.getFormListByCompanyId(keySearch, companyId, accountId, isPaging, lang);
    }
    
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
    public List<Select2Dto> getStatusListByCompanyIdAndFormId(String keySearch, Long companyId, Long processId, boolean isPaging, String lang) {
    	List<Select2Dto> lst = new ArrayList<>();
    	lst = formRepository.getStatusListByCompanyIdAndFormId(keySearch, companyId, processId, isPaging, lang);
    	return lst;
    }
    
	public List<Select2Dto> getStatusListCommon() {
		List<Select2Dto> lst = new ArrayList<>();
		
//		List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByKind(kind, langCode);
//		
//		if( statusList != null && !statusList.isEmpty() ) {
//			for (ConstantDisplay constantDisplay : statusList) {
//				String id = constantDisplay.getCat();
//				String name = constantDisplay.getCatAbbrName();
//				String text = constantDisplay.getCatAbbrName();
//				Select2Dto item = new Select2Dto(id, name, text);
//				lst.add(item);
//			}
//		}
		
		return lst;
	}

    @Override
    public List<Select2Dto> findByCompanyId(Long companyId) {
        return formRepository.findByCompanId(companyId);
    }

	@Override
	public Long getBusinessIdByFormId(Long formId) {
		return formRepository.getBusinessIdByFormId(formId);
	}


    /**
     * @author KhuongTH
     */
    @Override
    public Long getFormIdByBusinessId(Long businessId) {
        return formRepository.getFormIdByBusinessId(businessId);
    }

	@Override
	public List<Long> getIdsByDocType(Long companyId, String docType) throws SQLException {
		return formRepository.findIdsByDocType(companyId, docType);
	}

	@Override
	public List<Select2Dto> getListByCompanyIdAndUserName(String keySearch, Long companyId, String userName
			, List<String> processTypeIgnores, boolean isPaging, String lang) throws SQLException {
		return formRepository.findListByCompanyIdAndUserName(keySearch, companyId, userName, processTypeIgnores, isPaging, lang);
	}

	@Override
	public List<LanguageMapDto> getFormNameListById(Long formId) throws SQLException {
		return formLangRepository.findFormNameListById(formId);
	}

	/* (non-Javadoc)
	 * @see vn.com.unit.ppl.service.FormService#getFormDtoByBusinessId(java.lang.Long, java.lang.String)
	 */
	@Override
	public EfoFormDto getFormDtoByBusinessId(Long businessId, String lang) {
		return formRepository.getFormDtoById(businessId, lang);
	}

	/**
	 * only Apply with Mobile because not create service by config = 'pc'
	 * Taitt
	 */
	@Override
	public List<Select2Dto> getListByCompanyIdAndUserName(String keySearch, Long companyId, String userName,
			List<Integer> processTypeIgnores, String lang, int pageIndex, int pageSize, int isPaging)
			throws SQLException {
		
		return formRepository.findListByCompanyIdAndUserNameWithPaging(keySearch, companyId, userName, processTypeIgnores, lang,pageIndex,pageSize,isPaging);
	}

	   /**
     * only Apply with Mobile because not create service by config = 'pc'
     * Taitt
     */
	@Override
	public Long countListByKeySearchAndCompanyId(String keySearch, Long companyId, String userName, List<Integer> processTypeIgnores, String lang,int pageIndex,int pageSize,int isPaging ) throws SQLException{
		return formRepository.countListByCompanyIdAndUserNameWithPaging(keySearch, companyId, userName, processTypeIgnores, lang, pageIndex, pageSize, isPaging);
	}

    @Override
    public List<Select2Dto> getSelect2DtoForUser(String keySearch, Long companyId, Long accountId,
            List<String> processTypeIgnores, boolean isPaging, String lang) {
        return formRepository.getSelect2DtoForUser(keySearch, companyId, accountId, processTypeIgnores, isPaging, lang);
    }

}
