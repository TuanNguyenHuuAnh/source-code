/**
 * 
 */
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.dto.CurrencyDto;
import vn.com.unit.cms.admin.all.dto.InterestRateDto;
import vn.com.unit.cms.admin.all.dto.InterestRateLanguageDto;
import vn.com.unit.cms.admin.all.dto.InterestRateListDto;
import vn.com.unit.cms.admin.all.dto.InterestRateSearchDto;
import vn.com.unit.cms.admin.all.entity.InterestRate;
import vn.com.unit.cms.admin.all.entity.InterestRateLanguage;
import vn.com.unit.cms.admin.all.entity.Term;
import vn.com.unit.cms.admin.all.entity.TermLanguage;
import vn.com.unit.cms.admin.all.enumdef.InterestRateImportEnum;
import vn.com.unit.cms.admin.all.repository.CurrencyRepository;
import vn.com.unit.cms.admin.all.repository.InterestRateLanguageRepository;
import vn.com.unit.cms.admin.all.repository.InterestRateRepository;
import vn.com.unit.cms.admin.all.repository.TermLanguageRepository;
import vn.com.unit.cms.admin.all.repository.TermRepository;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.InterestRateHistoryService;
import vn.com.unit.cms.admin.all.service.InterestRateService;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.cms.admin.all.jcanary.common.util.ConstantImport;
import vn.com.unit.cms.admin.all.jcanary.entity.CityLanguage;
import vn.com.unit.cms.admin.all.jcanary.repository.CityRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.LanguageService;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.common.utils.CommonUtil;
//import vn.com.unit.util.ConstantImport;

/**
 * @author sonnt
 *
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class InterestRateServiceImpl implements InterestRateService {

    @Autowired
    InterestRateHistoryService interestRateHistoryService;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    InterestRateRepository interestRateRepository;

    @Autowired
    InterestRateLanguageRepository interestRateLanguageRepository;

    @Autowired
    TermRepository termRepository;

    @Autowired
    TermLanguageRepository termLanguageRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    private LanguageService languageService;

//    @Autowired
//    private ConstantDisplayService constantDisplayService;

    @Autowired
    CmsFileService fileService;
    
    @Autowired
	private JcaConstantService jcaConstantService;

    /**
     * Find all currency for a specific language code
     */
    @Override
    public List<CurrencyDto> findByLangeCode(String languageCode) {
        List<CurrencyDto> currencies = new ArrayList<CurrencyDto>();
        currencies = currencyRepository.findByLangeCode(languageCode);
        return currencies;
    }

    @Override
    public List<InterestRateDto> get(InterestRateSearchDto interestRateSearchDto, Locale locale) {
        return generateInterestRateScreenItem(interestRateRepository.findBySearchCondition(interestRateSearchDto),
                locale);
    }

    /**
     * Prepares the data for rendering Interest rate create/list/edit screen
     * 
     * @param interestRateList
     *            includes all latest interest rate item for a specific city
     * @param locale
     * @return
     */
    private List<InterestRateDto> generateInterestRateScreenItem(List<InterestRate> interestRateList, Locale locale) {
        Map<Long, List<InterestRate>> interestRateMap = generateInterestRateMap(interestRateList, locale);

        List<InterestRateDto> interestRateDtoList = generateInterestRateList(interestRateMap);

        fillTermInfor(interestRateDtoList, locale.toString());

        return interestRateDtoList;
    }

    /**
     * This method create a map which groups interest rate item by term id
     * 
     * @param interestRateList
     *            a list contains all item for a specific city
     * @param locale
     * @return
     */
    private Map<Long, List<InterestRate>> generateInterestRateMap(List<InterestRate> interestRateList, Locale locale) {
        Map<Long, List<InterestRate>> interestRateMap = new HashMap<>();
        List<Term> terms = termRepository.getSortedTermList();
        List<Long> termIdList = createTermList(terms);
        for (InterestRate item : interestRateList) {
            if (termIdList.contains(item.getTermId())) {
                if (!interestRateMap.containsKey(item.getTermId())) {
                    interestRateMap.put(item.getTermId(), new ArrayList<InterestRate>());
                }
                interestRateMap.get(item.getTermId()).add(item);
            }
        }

        for (Term item : terms) {
            if (!interestRateMap.containsKey(item.getId())) {
                interestRateMap.put(item.getId(), new ArrayList<InterestRate>());
            }
        }

        return interestRateMap;
    }

    private List<Long> createTermList(List<Term> terms) {
        List<Long> result = new ArrayList<>();
        if (terms != null) {
            for (Term term : terms) {
                result.add(term.getId());
            }
        }
        return result;
    }

    /**
     * Generate a list of interest rate in which each item corresponds to one
     * row data on screen
     * 
     * @param interestRateMap
     *            a map which groups interest rate item by term id
     * @return
     */
    private List<InterestRateDto> generateInterestRateList(Map<Long, List<InterestRate>> interestRateMap) {
        List<JcaConstantDto> personalCurrencyList = new ArrayList<>();
        List<JcaConstantDto> organzationCurrencyList = new ArrayList<>();
        Map<Long, JcaConstantDto> constantTypeMap = getCurrencyTypeMap();

        setCurrencyConstantList(personalCurrencyList, organzationCurrencyList, constantTypeMap);

        List<InterestRateDto> interestRateDtoList = new ArrayList<>();
        List<Term> sortedTermIdList = termRepository.getSortedTermList();
        Long interestId = 0L;
        Date displayDate = new Date();
        for (Map.Entry<Long, List<InterestRate>> entry : interestRateMap.entrySet()) {
            if (entry.getValue() != null) {
                if (!entry.getValue().isEmpty()) {
                    InterestRate interestRate = entry.getValue().get(0);
                    interestId = interestRate.getInterestId();
                    displayDate = interestRate.getDisplayDate();
                    break;
                }
            }
        }

        for (Term term : sortedTermIdList) {
            List<InterestRate> interestRateList = interestRateMap.get(term.getId());
            if (interestRateList != null) {
                InterestRateDto interestRateDto = new InterestRateDto();
                // if(!interestRateList.isEmpty()){
                interestRateDto.setDisplayDate(displayDate);
                interestRateDto.setTermId(term.getId());
                interestRateDto.setId(interestId);
                // }
                for (InterestRate item : interestRateList) {
                	JcaConstantDto constantDisplay = constantTypeMap.get(item.getCurrencyId());
                    if(null != constantDisplay){
	                    if (constantDisplay.getKind().equals(AdminConstant.PERSONAL_INTEREST_RATE_CURRENCY_KIND))
	                        interestRateDto.getPersonalCurrencyList().put(constantDisplay.getCode(), item.getValue());
	                    else if (constantDisplay.getKind().equals(AdminConstant.ORGANIZATION_INTEREST_RATE_CURRENCY_KIND))
	                        interestRateDto.getOrganisationCurrencyList().put(constantDisplay.getCode(), item.getValue());
                    }
                }

                fillEmptyData(interestRateDto, personalCurrencyList, organzationCurrencyList);

                interestRateDtoList.add(interestRateDto);
            }
        }
        return interestRateDtoList;
    }

    private void fillEmptyData(InterestRateDto interestRateDto, List<JcaConstantDto> personalCurrencyList,
            List<JcaConstantDto> organzationCurrencyList) {

        if (interestRateDto.getPersonalCurrencyList().keySet().size() != personalCurrencyList.size()) {
            for (JcaConstantDto constantDisplay : personalCurrencyList) {
                if (!interestRateDto.getPersonalCurrencyList().containsKey(constantDisplay.getCode())) {
                    interestRateDto.getPersonalCurrencyList().put(constantDisplay.getCode(), null);
                }
            }
        }

        if (interestRateDto.getOrganisationCurrencyList().keySet().size() != organzationCurrencyList.size()) {
            for (JcaConstantDto constantDisplay : organzationCurrencyList) {
                if (!interestRateDto.getOrganisationCurrencyList().containsKey(constantDisplay.getCode())) {
                    interestRateDto.getOrganisationCurrencyList().put(constantDisplay.getCode(), null);
                }
            }
        }

    }

    private void setCurrencyConstantList(List<JcaConstantDto> personalCurrencyTypeList,
            List<JcaConstantDto> organzationCurrencyTypeList, Map<Long, JcaConstantDto> currencyTypeMap) {
        for (Map.Entry<Long, JcaConstantDto> entry : currencyTypeMap.entrySet()) {
        	JcaConstantDto constantDisplay = entry.getValue();
            if (AdminConstant.PERSONAL_INTEREST_RATE_CURRENCY_KIND.equals(constantDisplay.getKind())) {
                personalCurrencyTypeList.add(constantDisplay);
            } else if (AdminConstant.ORGANIZATION_INTEREST_RATE_CURRENCY_KIND.equals(constantDisplay.getKind())) {
                organzationCurrencyTypeList.add(constantDisplay);
            }

        }
    }

    private Map<Long, JcaConstantDto> getCurrencyTypeMap() {

		// ${constantDisplay.cat} => ${constantDisplay.kind}
		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
		// constDispService.findByType("M10");
		// => List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");

		// type => groupCode
		// cat	=> kind
		// code => code
		
		// catOfficialName => name
		
		// ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString());
		// JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);

		// List<ConstantDisplay> listBannerPage = constDispService.findByType(ConstDispType.B01);
    	// List<JcaConstantDto> listBannerPage = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");

//    	List<ConstantDisplay> constants = constantDisplayService.findByType(ConstDispType.M12);
    	
    	List<JcaConstantDto> constants = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M12.toString(), "EN");

        Map<Long, JcaConstantDto> constantTypeMap = new HashMap<>();
        for (JcaConstantDto constant : constants) {
            constantTypeMap.put(Long.valueOf(constant.getKind()), constant);
        }
        return constantTypeMap;
    }

    /**
     * 
     * Fulfill term information on the Interest rate list before returning
     * 
     * @param interestRateDtoList
     * @param languageCode
     */
    private void fillTermInfor(List<InterestRateDto> interestRateDtoList, String languageCode) {
        Map<Long, String> termNameMap = getTermNameMap(languageCode);
        for (InterestRateDto item : interestRateDtoList) {
            item.setTermName(termNameMap.get(item.getTermId()));
            item.setTermCode(termRepository.findOne(item.getTermId()).getCode());
        }

    }

    /**
     * Returns a map which map term id to term name
     * 
     * @param languageCode
     * 
     */
    private Map<Long, String> getTermNameMap(String languageCode) {
        Map<Long, String> termNameMap = new HashMap<>();
        List<TermLanguage> termLanguagesList = termLanguageRepository.findAllByLanguageCode(languageCode);
        for (TermLanguage item : termLanguagesList) {
            if (!termNameMap.containsKey(item.getTermId()))
                termNameMap.put(item.getTermId(), item.getTitle());
        }
        return termNameMap;
    }

    /**
     * Edit currency
     * 
     * @param currencyEditDto
     *            object contains editing information
     */
    @Override
    @Transactional
    public void edit(InterestRateListDto interestRateListDto) {
        List<InterestRateDto> interestRateDtoList = interestRateListDto.getInterestRateDtoList();
        Map<String, Long> personalCurrencyMap = generatePersonalCurrencyMap();
        Map<String, Long> organisationCurrencyMap = generateOrganisationCurrencyMap();
        if (!interestRateDtoList.isEmpty())
            increaseIdIfNeeded(interestRateListDto);

        InterestRate interestRate = new InterestRate();
        interestRate.setInterestId(interestRateListDto.getInterestId());
        interestRate.setCityId(interestRateListDto.getCityId());
        interestRate.setDisplayDate(interestRateListDto.getDisplayDate());

        for (InterestRateDto item : interestRateDtoList) {
            for (Map.Entry<String, String> entry : item.getPersonalCurrencyList().entrySet()) {
                interestRate.setCurrencyId(personalCurrencyMap.get(entry.getKey()));
                interestRate.setTermId(item.getTermId());
                interestRate.setValue(entry.getValue());
                interestRateRepository.insertOrUpdate(interestRate);
            }
            for (Map.Entry<String, String> entry : item.getOrganisationCurrencyList().entrySet()) {
                interestRate.setCurrencyId(organisationCurrencyMap.get(entry.getKey()));
                interestRate.setTermId(item.getTermId());
                interestRate.setValue(entry.getValue());
                interestRateRepository.insertOrUpdate(interestRate);
            }
        }
//        UserProfile userProfile = UserProfileUtils.getUserProfile();

        addOrEditInterestRateLanguage(interestRateListDto, UserProfileUtils.getUserNameLogin(), interestRate.getInterestId());
        interestRateHistoryService.insert(interestRateListDto);
    }

    private void addOrEditInterestRateLanguage(InterestRateListDto interestRateListDto, String userName, Long interestid) {
        List<InterestRateLanguageDto> interestRateLanguageDtoList = interestRateListDto
                .getInterestRateLanguageDtoList();
        for (InterestRateLanguageDto item : interestRateLanguageDtoList) {
            InterestRateLanguage interestRateLanguage = new InterestRateLanguage();

            if (null != item.getId()) {
                interestRateLanguage = interestRateLanguageRepository.findOne(item.getId());

                if (null == interestRateLanguage) {
                    throw new BusinessException("Not found InterestRateLanguage with id=" + item.getId());
                }
                interestRateLanguage.setUpdateDate(new Date());
                interestRateLanguage.setUpdateBy(userName);
            } else {
                interestRateLanguage.setCreateDate(new Date());
                interestRateLanguage.setCreateBy(userName);
            }

            interestRateLanguage.setInterestRateId(interestid);
            interestRateLanguage.setLanguageCode(item.getLanguageCode());
            interestRateLanguage.setDescription(item.getDescription());

            interestRateLanguageRepository.save(interestRateLanguage);
        }
    }

    private void increaseIdIfNeeded(InterestRateListDto interestRateListDto) {
        Date currentDate = getCurrentDate();
        if (!DateUtils.isSameDay(interestRateListDto.getDisplayDate(), currentDate)) {
            interestRateListDto.increaseInterestId();
            interestRateListDto.setDisplayDate(currentDate);
        }
    }

    /**
     * Form a currency map from Currency Enum
     * 
     * @return
     */
    private Map<String, Long> generatePersonalCurrencyMap() {
        Map<String, Long> result = new HashMap<>();
        
		// ${constantDisplay.cat} => ${constantDisplay.kind}
		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
		// constDispService.findByType("M10");
		// => List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");

		// type => groupCode
		// cat	=> kind
		// code => code
		
		// catOfficialName => name
		
		// ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString());
		// JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);

		// List<ConstantDisplay> listBannerPage = constDispService.findByType(ConstDispType.B01);
    	// List<JcaConstantDto> listBannerPage = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");

//        List<ConstantDisplay> constants = constantDisplayService.findByType(ConstDispType.M12);
        
        List<JcaConstantDto> constants = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M12.toString(), "EN");

        for (JcaConstantDto constant : constants) {
            if (AdminConstant.PERSONAL_INTEREST_RATE_CURRENCY_KIND.equals(constant.getKind())) {
                result.put(constant.getCode(), Long.valueOf(constant.getKind()));
            }
        }

        return result;
    }

    /**
     * Form a currency map from Currency Enum
     * 
     * @return
     */
    private Map<String, Long> generateOrganisationCurrencyMap() {
        Map<String, Long> result = new HashMap<>();
        
		// ${constantDisplay.cat} => ${constantDisplay.kind}
		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
		// constDispService.findByType("M10");
		// => List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");

		// type => groupCode
		// cat	=> kind
		// code => code
		
		// catOfficialName => name
		
		// ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString());
		// JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);

		// List<ConstantDisplay> listBannerPage = constDispService.findByType(ConstDispType.B01);
    	// List<JcaConstantDto> listBannerPage = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");
        
//        List<ConstantDisplay> constants = constantDisplayService.findByType(ConstDispType.M12);
        
        List<JcaConstantDto> constants = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M12.toString(), "EN");

        for (JcaConstantDto constant : constants) {
            if (AdminConstant.ORGANIZATION_INTEREST_RATE_CURRENCY_KIND.equals(constant.getKind())) {
                result.put(constant.getCode(), Long.valueOf(constant.getKind()));
            }
        }

        return result;
    }

    /**
     * Initialize language list
     */
    @Override
    public void initLanguageList(ModelAndView modelAndView) {
        List<LanguageDto> languageList = languageService.getLanguageDtoList();

        modelAndView.addObject("languageList", languageList);

    }

    @Override
    public List<SearchKeyDto> getCurrencyList(String languageCode) {
        List<SearchKeyDto> currencyTypeList = new ArrayList<>();
        List<CurrencyDto> currencyList = currencyRepository.findByLangeCode(languageCode);
        for (CurrencyDto item : currencyList) {
            SearchKeyDto currencyType = new SearchKeyDto();
            currencyType.setDispName(item.getName());
            currencyType.setFieldId(String.valueOf(item.getId()));
            currencyTypeList.add(currencyType);
        }
        return currencyTypeList;
    }

    @Override
    public List<String> getPersonalCurrencyList() {
        List<String> result = new ArrayList<>();
        
		// ${constantDisplay.cat} => ${constantDisplay.kind}
		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
		// constDispService.findByType("M10");
		// => List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");

		// type => groupCode
		// cat	=> kind
		// code => code
		
		// catOfficialName => name
		
		// ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString());
		// JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);

		// List<ConstantDisplay> listBannerPage = constDispService.findByType(ConstDispType.B01);
    	// List<JcaConstantDto> listBannerPage = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");
        
//        List<ConstantDisplay> constants = constantDisplayService.findByType(ConstDispType.M12);
        
        List<JcaConstantDto> constants = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M12.toString(), "EN");

        for (JcaConstantDto constant : constants) {
            if (AdminConstant.PERSONAL_INTEREST_RATE_CURRENCY_KIND.equals(constant.getKind())) {
                result.add(constant.getCode());
            }
        }

        return result;
    }

    @Override
    public List<String> getOrganisationCurrencyList() {
        List<String> result = new ArrayList<>();
        
		// ${constantDisplay.cat} => ${constantDisplay.kind}
		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
		// constDispService.findByType("M10");
		// => List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");

		// type => groupCode
		// cat	=> kind
		// code => code
		
		// catOfficialName => name
		
		// ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString());
		// JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);

		// List<ConstantDisplay> listBannerPage = constDispService.findByType(ConstDispType.B01);
    	// List<JcaConstantDto> listBannerPage = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");
        
//        List<ConstantDisplay> constants = constantDisplayService.findByType(ConstDispType.M12);
        
        List<JcaConstantDto> constants = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M12.toString(), "EN");

        for (JcaConstantDto constant : constants) {
            if (AdminConstant.ORGANIZATION_INTEREST_RATE_CURRENCY_KIND.equals(constant.getKind())) {
                result.add(constant.getCode());
            }
        }

        return result;
    }

    /**
     * get a list of city
     */
    @Override
    public List<CityLanguage> getCityList(String languageCode) {
        return cityRepository.findAllCityLanguageByLanguageCode(languageCode);
    }

    /**
     * Enrich necessary information include city id, display date and a list of
     * interest rate
     * 
     * @param interestRateListDto
     *            contains data for rendering
     * @param interestRateSearchDto
     *            includes infor for searching
     * @param cityList
     *            list of city
     * @param locale
     *            language
     */
    @Override
    public void enrichInterestRateDtoListInfo(InterestRateListDto interestRateListDto,
            InterestRateSearchDto interestRateSearchDto, List<CityLanguage> cityList, Locale locale) {
        if (interestRateSearchDto.getCityId() == null) {
            if (cityList != null)
                if (!cityList.isEmpty()) {
                    CityLanguage cityLanguage = cityList.get(0);
                    interestRateSearchDto.setCityId(cityLanguage.getmCityId());
                    interestRateListDto.setCityId(cityLanguage.getmCityId());
                }
        } else {
            interestRateListDto.setCityId(interestRateSearchDto.getCityId());
        }

        // interestRateDtoList
        List<InterestRateDto> interestRateList = get(interestRateSearchDto, locale);

        interestRateListDto.setInterestRateDtoList(interestRateList);

        if (interestRateList != null)
            if (!interestRateList.isEmpty()) {
                InterestRateDto interestRateDto = interestRateList.get(0);
                interestRateListDto.setInterestId(interestRateDto.getId());
                interestRateListDto.setDisplayDate(interestRateDto.getDisplayDate());
            }

        interestRateListDto.setInterestRateLanguageDtoList(getLanguageList(interestRateListDto.getInterestId()));

        interestRateListDto.setRequestToken(CommonUtil.randomStringWithTimeStamp());

    }

    /**
     * Initialize the creating Interest Rate screen by importing data from excel
     * file
     * 
     * @param multipartHttpServletRequest
     * @param locale
     * @param messageList
     * @param messageSource
     * 
     */
    @SuppressWarnings("resource")
	@Override
    public InterestRateListDto importFromExcel(MultipartHttpServletRequest multipartHttpServletRequest, Locale locale,
            MessageList messageList, MessageSource messageSource) throws Exception {
        InterestRateListDto interestRateListDto = new InterestRateListDto();

        Iterator<String> itr = multipartHttpServletRequest.getFileNames();
        MultipartFile multipartFile = multipartHttpServletRequest.getFile(itr.next());

        if (multipartFile == null) {
            throw new Exception("Vui lòng nhập file import");
        }
        String contentType = multipartFile.getContentType();

        Workbook workbook = null;

        if (contentType.equals("application/vnd.ms-excel")) {
            workbook = new HSSFWorkbook(multipartFile.getInputStream());
        } else {
            workbook = new XSSFWorkbook(multipartFile.getInputStream());
        }
        //
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        // get row header
        Row rowHeader = iterator.next();
        // set row header to list
        List<ItemColsExcelDto> cols = new ArrayList<ItemColsExcelDto>();
        ConstantImport.setListColumnExcel(InterestRateImportEnum.class, cols);
        // check file invalid

        if (ConstantImport.isValidFileExcelImport(rowHeader, cols)) {
            // get data
            ConstantImport<InterestRate> excelData = new ConstantImport<InterestRate>();
            excelData.setDataFileExcel(iterator, cols, rowHeader, InterestRate.class);
            if (excelData.getData() == null) {
                messageList.setStatus(Message.ERROR);
                messageList.add(messageSource.getMessage(ConstantCore.MESSAGE_INVALID_COLUMN_EXCEL_NOTFOUND_DATA, null,
                        locale));
            } else {
                // set editing item list
                List<InterestRate> interestRateList = excelData.getData();
                interestRateListDto.setInterestRateDtoList(generateInterestRateScreenItem(interestRateList, locale));
                // set city Id
                if (interestRateList != null) {
                    if (!interestRateList.isEmpty()) {
                        Long cityId = interestRateList.get(0).getCityId();
                        interestRateListDto.setCityId(cityId);
                        // enrich necessary information
                        InterestRate lastInterest = getLastIdByCity(cityId);
                        interestRateListDto.setInterestId(lastInterest.getInterestId());
                        interestRateListDto.setDisplayDate(lastInterest.getDisplayDate());
                    }
                }
                messageList.add(messageSource.getMessage(ConstantCore.MSG_SUCCESS_IMPORT, null, locale));
            }
        } else {
            messageList.setStatus(Message.ERROR);
            messageList.add(messageSource.getMessage(ConstantCore.MESSAGE_INVALID_COLUMN_EXCEL, null, locale));
        }
        return interestRateListDto;
    }

    /**
     * Returns latest created Interest Rate
     * 
     * @param cityId
     * @return
     */
    private InterestRate getLastIdByCity(Long cityId) {
        InterestRateSearchDto interestRateSearchDto = new InterestRateSearchDto();
        interestRateSearchDto.setCityId(cityId);
        List<InterestRate> interestRateList = interestRateRepository.findBySearchCondition(interestRateSearchDto);
        if (interestRateList != null)
            if (!interestRateList.isEmpty())
                return interestRateList.get(0);
        return new InterestRate();
    }

    /**
     * Returns the current day
     * 
     * @return
     */
    private Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public List<InterestRateLanguageDto> getLanguageList(Long interestRateId) {
        List<InterestRateLanguageDto> interestRateLanguageDtoList = new ArrayList<>();

        List<InterestRateLanguage> interestRateLanguageList = interestRateLanguageRepository
                .findByInterestRateId(interestRateId);
        
        if(interestRateLanguageList != null && interestRateLanguageList.size() >0){
        	 for (InterestRateLanguage item : interestRateLanguageList) {
                 InterestRateLanguageDto interestRateLanguageDto = new InterestRateLanguageDto();
                 interestRateLanguageDto.setId(item.getId());
                 interestRateLanguageDto.setInterestRateId(item.getInterestRateId());
                 interestRateLanguageDto.setLanguageCode(item.getLanguageCode());
                 interestRateLanguageDto.setDescription(item.getDescription());
                 interestRateLanguageDtoList.add(interestRateLanguageDto);
             } 	
        }
        else{
        	List<LanguageDto> languageList = languageService.getLanguageDtoList();
        	for(LanguageDto dto : languageList){
        		InterestRateLanguageDto interestRateLanguageDto = new InterestRateLanguageDto();         
                interestRateLanguageDto.setLanguageCode(dto.getCode());
                interestRateLanguageDto.setDescription("");
                interestRateLanguageDtoList.add(interestRateLanguageDto);
        	}
        }
       

        return interestRateLanguageDtoList;
    }

    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response) {
        boolean retVal = false;
        if (fileUrl != null) {
            if (CmsUtils.fileExistedInMain(fileUrl)) {
                fileService.download(fileUrl, request, response);
                retVal = true;
            } else if (CmsUtils.fileExistedInTemp(fileUrl)) {
                fileService.downloadTemp(fileUrl, request, response);
                retVal = true;
            }
        }
        return retVal;
    }
}