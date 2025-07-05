/**
 * 
 */
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.dto.CurrencyAddOrEditDto;
import vn.com.unit.cms.admin.all.dto.CurrencyDto;
import vn.com.unit.cms.admin.all.dto.CurrencyLanguageDto;
import vn.com.unit.cms.admin.all.dto.CurrencyListDto;
import vn.com.unit.cms.admin.all.dto.CurrencySearchDto;
import vn.com.unit.cms.admin.all.entity.Currency;
import vn.com.unit.cms.admin.all.entity.CurrencyLanguage;
//import vn.com.unit.cms.admin.all.enumdef.CurrencySearchEnum;
import vn.com.unit.cms.admin.all.enumdef.StatusSearchEnum;
//import vn.com.unit.cms.admin.all.enumdef.TermSearchEnum;
import vn.com.unit.cms.admin.all.repository.CurrencyLanguageRepository;
import vn.com.unit.cms.admin.all.repository.CurrencyRepository;
import vn.com.unit.cms.admin.all.service.CurrencyService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.ep2p.core.utils.Utility;

/**
 * @author phunghn
 *
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	CurrencyRepository currencyRepository;
	
	@Autowired
	CurrencyLanguageRepository currencyLanguageRepository;
	
	@Autowired
	private SystemConfig systemConfig;
	
	@Autowired
    private LanguageService languageService;
	   
    @Autowired
    CmsCommonService commonService;
	
    private static final String PREFIX_CODE = "CURR.";
	
	/**
	 * Get list of Currency list filtered by criteria specified in CurrencySearchDto object
	 * @param page page for paging
	 * @param currencySearchDto object contains search criteria
	 * @param locale contains location information
	 * @return pageWrapper object contains currency list along with paging information
	 */
	@Override
	public PageWrapper<CurrencyListDto> list(int page, CurrencySearchDto currencySearchDto, Locale locale) {
        int sizeOfPage = currencySearchDto.getPageSize() != null ? currencySearchDto.getPageSize() : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);        
		// prepare the search criteria for before querying.
//		setSearchCriteria(currencySearchDto);
		
		//int pageSize = systemConfig.getIntConfig(AppSystemConfig.PAGING_SIZE);
		
		int count = currencyRepository.countBySearchCondition(currencySearchDto, locale.toString());
		if ((count % sizeOfPage == 0 && page > count / sizeOfPage) || (count % sizeOfPage > 0 && page - 1 > count / sizeOfPage)) {
		    page = 1;
        }
		
		PageWrapper<CurrencyListDto> pageWrapper = new PageWrapper<CurrencyListDto>(page, sizeOfPage);
		List<CurrencyListDto> currencyListDtos = new ArrayList<>();
		if(count > 0){
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			currencyListDtos = currencyRepository.findBySearchCondition(offsetSQL, sizeOfPage, currencySearchDto, locale.toString());
		}
		
		pageWrapper.setDataAndCount(currencyListDtos, count);
		
		return pageWrapper;
	}
	
	/**
	 * fulfill search criteria to prepare for searching
	 * @param currencySearchDto contain search information
	 */
//	private void setSearchCriteria(CurrencySearchDto currencySearchDto) {
//		String searchKeyWord = currencySearchDto.getSearchKeyWord();
//		if(searchKeyWord != null)
//			searchKeyWord = searchKeyWord.trim();
//		
//		if (null == currencySearchDto.getSelectedField()) {
//            currencySearchDto.setSelectedField(new ArrayList<String>());
//        }
//
//        if (currencySearchDto.getSelectedField().isEmpty()) {
//            currencySearchDto.setName(searchKeyWord);
//            currencySearchDto.setTitle(searchKeyWord);
//        } else {
//            for (String field : currencySearchDto.getSelectedField()) {
//                if (StringUtils.equals(field, CurrencySearchEnum.TITLE.name())) {
//                    currencySearchDto.setTitle(searchKeyWord);;
//                    continue;
//                }
//                if (StringUtils.equals(field, CurrencySearchEnum.NAME.name())) {
//                    currencySearchDto.setName(searchKeyWord);
//                    continue;
//                }
//            }
//        }
//	}
	
	/**
	 * Returns information for a specific currency
	 * @param id
	 * @param language
	 * @return termEditDto object represents term data 
	 */
	@Override
	public CurrencyAddOrEditDto get(Long id, String language) {
		Currency currency = currencyRepository.findOne(id);
		
		CurrencyAddOrEditDto currencyAddOrEditDto = new CurrencyAddOrEditDto();
		if(currency != null){
			currencyAddOrEditDto.setId(currency.getId());
			currencyAddOrEditDto.setCode(currency.getCode());
			currencyAddOrEditDto.setName(currency.getName());
			currencyAddOrEditDto.setNote(currency.getNote());
			currencyAddOrEditDto.setDescription(currency.getDescription());
		}
		
		List<CurrencyLanguageDto> currencyLanguageList = getLanguageList(id);
		currencyAddOrEditDto.setCurrencyLanguageList(currencyLanguageList);
		
		String url = UrlConst.CURRENCY.concat(UrlConst.DETAIL);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        currencyAddOrEditDto.setUrl(url);
		
		return currencyAddOrEditDto;
	}
	
	/**
	 * Returns a list of Currency Language object for a specific currency id id
	 * @param id id of currency
	 * @return
	 */
	private List<CurrencyLanguageDto> getLanguageList(Long id) {
		List<CurrencyLanguageDto> currencyLanguageDtoList = new ArrayList<>();
		List<CurrencyLanguage> currencyLanguageList = currencyLanguageRepository.findAllByCurrencyId(id);
		
		for(CurrencyLanguage item : currencyLanguageList){
			CurrencyLanguageDto currencyLanguageDto = new CurrencyLanguageDto();
			currencyLanguageDto.setId(item.getId());
			currencyLanguageDto.setCurrencyId(item.getCurrencyId());
			currencyLanguageDto.setTitle(item.getTitle());
			currencyLanguageDto.setLanguageCode(item.getLanguageCode());
			currencyLanguageDtoList.add(currencyLanguageDto);
		}
		
		return currencyLanguageDtoList;
	}
	
	/**
	 * Get term by it's id
	 * @param id term's id
	 * @param languageCode
	 * @return
	 */
	@Override
	public Currency getById(Long id, String languageCode) {
		return currencyRepository.getCurrency(id, languageCode);
	}
	
	/**
	 * Get data for preparing editing currency
	 * @param id id of currency
	 */
	@Override
	public CurrencyAddOrEditDto getEdit(Long id) {
		
		CurrencyAddOrEditDto currencyAddOrEditDto = new CurrencyAddOrEditDto();
		
		if(id == null){
		    currencyAddOrEditDto.setStatus(StatusSearchEnum.SAVED.toString());
		    return currencyAddOrEditDto;
		}
		
		Currency currency = currencyRepository.findOne(id);
		
		if(currency != null){
			currencyAddOrEditDto.setId(currency.getId());
			currencyAddOrEditDto.setCode(currency.getCode());
			currencyAddOrEditDto.setName(currency.getName());
			currencyAddOrEditDto.setDescription(currency.getDescription());
			currencyAddOrEditDto.setNote(currency.getNote());
			currencyAddOrEditDto.setStatus(currency.getStatus());
			currencyAddOrEditDto.setCreateBy(currency.getCreateBy());
			currencyAddOrEditDto.setApproveBy(currency.getApproveBy());
			currencyAddOrEditDto.setPublishBy(currency.getPublishBy());;
		}
		// set language list
		List<CurrencyLanguageDto> currencyLanguageDtoList = getLanguageList(id);
		currencyAddOrEditDto.setCurrencyLanguageList(currencyLanguageDtoList);
		
		String url = UrlConst.CURRENCY.concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        currencyAddOrEditDto.setUrl(url);
		
		return currencyAddOrEditDto;
	}
	
	/**
	 * Initialize language list
	 */
	@Override
	public void initLanguageList(ModelAndView modelAndView) {
		List<LanguageDto> languageList = languageService.getLanguageDtoList();

		modelAndView.addObject("languageList", languageList);
		
	}
	
	/**
	 * Delete term based on its id
	 * @param id term's id
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		Currency currency = currencyRepository.findOne(id);
		if(currency == null)
			throw new BusinessException("Cannot find Currency with id:" + id);
		
		String userName = UserProfileUtils.getUserNameLogin();
		
		deleteCurrencyLanguage(id, userName);
		deleteCurrency(currency, userName);
	}
	
	/**
	 * Marks currency corresponding language objects as deleted
	 * @param id currency id
	 * @param userName deleting person
	 */
	private void deleteCurrencyLanguage(Long id, String userName) {
		currencyLanguageRepository.deleteByCurrencyId(id, new Date(), userName);
	}
	
	/**
	 * Marks currency object as deleted
	 * @param currency deleted currency
	 * @param userName deleting person
	 */
	private void deleteCurrency(Currency currency, String userName) {
		currency.setDeleteBy(userName);
		currency.setDeleteDate(new Date());
		currencyRepository.save(currency);
	}
	
	/**
	 * Creates or edits Currency
	 * @param CurrencyAddOrEditDto currencyAddOrEditDto object contains necessary information for creating Currency object
	 * 
	 */
	@Override
	@Transactional
	public void createOrEdit(CurrencyAddOrEditDto currencyAddOrEditDto) {
		String userName = UserProfileUtils.getUserNameLogin();
		
		createOrEditCurrency(currencyAddOrEditDto, userName);
		
		createOrEditcurrencyLanguage(currencyAddOrEditDto, userName);
	}
	
	/**
	 * Creates or edits currency
	 * @param currencyAddOrEditDto
	 * @param userName
	 */
	private void createOrEditCurrency(CurrencyAddOrEditDto currencyAddOrEditDto, String userName) {
		Currency currency = new Currency();
		Long id = currencyAddOrEditDto.getId();
		
		if(id != null){
			currency = currencyRepository.findOne(id);
			if(currency == null)
				throw new BusinessException("Cannot find Currency with id: " + id);
			currency.setUpdateBy(userName);
			currency.setUpdateDate(new Date());
		} else {
			currency.setCreateBy(userName);
			currency.setCreateDate(new Date());
			currency.setCode(CommonUtil.getNextCode(PREFIX_CODE, commonService.getMaxCode("M_CURRENCY", PREFIX_CODE)));
		}
		
		currency.setName(currencyAddOrEditDto.getName());
		currency.setDescription(currencyAddOrEditDto.getDescription());
		currency.setNote(currencyAddOrEditDto.getNote());
		currency.setStatus(StatusSearchEnum.SAVED.toString());
		currency.setCurrencyComment(currencyAddOrEditDto.getCurrencyComment());
		
		currencyRepository.save(currency);
		
		currencyAddOrEditDto.setId(currency.getId());
	}
	
	/**
	 * Creates and edits Currency Language object
	 * @param currencyAddOrEditDto
	 * @param userName
	 */
	private void createOrEditcurrencyLanguage(CurrencyAddOrEditDto currencyAddOrEditDto, String userName) {
		for(CurrencyLanguageDto item : currencyAddOrEditDto.getCurrencyLanguageList()){
			CurrencyLanguage currencyLanguage = new CurrencyLanguage();
			
			if(item.getId() != null){
				currencyLanguage = currencyLanguageRepository.findOne(item.getId());
				if(currencyLanguage == null)
					throw new BusinessException("Cannot found Currency Language with id : " + item.getId());
				currencyLanguage.setUpdateDate(new Date());
				currencyLanguage.setUpdateBy(userName);
			} else {
				currencyLanguage.setCreateDate(new Date());
				currencyLanguage.setCreateBy(userName);
			}
			
			currencyLanguage.setLanguageCode(item.getLanguageCode());
			currencyLanguage.setCurrencyId(currencyAddOrEditDto.getId());
			currencyLanguage.setTitle(item.getTitle());
			
			currencyLanguageRepository.save(currencyLanguage);
		}
	}

	
	@Override
	public List<CurrencyDto> findAllActive(String locate) {
		return currencyRepository.findByLangeCode(locate);
	}
	
	/** getMaxCode
    *
    * @author nhutnn
    * @return max code
    */
    @Override
    public String getMaxCode() {
        return currencyRepository.getMaxCode();
    }
}
