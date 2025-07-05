/**
 * 
 */
package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.dto.CurrencyAddOrEditDto;
import vn.com.unit.cms.admin.all.dto.CurrencyDto;
import vn.com.unit.cms.admin.all.dto.CurrencyListDto;
import vn.com.unit.cms.admin.all.dto.CurrencySearchDto;
import vn.com.unit.cms.admin.all.entity.Currency;
import vn.com.unit.common.dto.PageWrapper;

/**
 * @author phunghn
 *
 */
public interface CurrencyService {
	
	public PageWrapper<CurrencyListDto> list(int page, CurrencySearchDto currencySearchDto, Locale locale);

	public CurrencyAddOrEditDto get(Long id, String string);
	
	public Currency getById(Long id, String string);

	public CurrencyAddOrEditDto getEdit(Long id);
	
	void initLanguageList(ModelAndView modelAndView);

	public void delete(Long id);

	public void createOrEdit(CurrencyAddOrEditDto currencyAddOrEditDto);
	
	public List<CurrencyDto> findAllActive(String locate);

	/** getMaxCode
    *
    * @author nhutnn
    * @return max code
    */
    String getMaxCode();
}
