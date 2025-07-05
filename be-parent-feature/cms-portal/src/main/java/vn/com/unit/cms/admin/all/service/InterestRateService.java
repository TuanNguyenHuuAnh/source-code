/**
 * 
 */
package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

//import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.cms.admin.all.dto.CurrencyDto;
import vn.com.unit.cms.admin.all.dto.InterestRateDto;
import vn.com.unit.cms.admin.all.dto.InterestRateLanguageDto;
import vn.com.unit.cms.admin.all.dto.InterestRateListDto;
import vn.com.unit.cms.admin.all.dto.InterestRateSearchDto;
import vn.com.unit.cms.admin.all.jcanary.entity.CityLanguage;
import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.jcanary.entity.CityLanguage;
import vn.com.unit.ep2p.admin.constant.MessageList;

/**
 * @author sonnt
 *
 */
public interface InterestRateService {
	public List<CurrencyDto> findByLangeCode(String languageCode);
	
	public List<InterestRateDto> get(InterestRateSearchDto interestRateSearchDto, Locale locale);

	public void edit(InterestRateListDto interestRateListDto);

	void initLanguageList(ModelAndView modelAndView);

	public List<SearchKeyDto> getCurrencyList(String languageCode);

	public List<String> getPersonalCurrencyList();

	public List<String> getOrganisationCurrencyList();

	public List<CityLanguage> getCityList(String languageCode);

	public void enrichInterestRateDtoListInfo(InterestRateListDto interestRateListDto, InterestRateSearchDto interestRateSearchDto, List<CityLanguage> cityList, Locale locale);


	public InterestRateListDto importFromExcel(MultipartHttpServletRequest multipartHttpServletRequest, Locale locale, MessageList messageList, MessageSource messageSource) throws Exception;

	public boolean requestEditorDownload(String url, HttpServletRequest request, HttpServletResponse response);
	
	public  List<InterestRateLanguageDto> getLanguageList(Long interestRateId);
}
