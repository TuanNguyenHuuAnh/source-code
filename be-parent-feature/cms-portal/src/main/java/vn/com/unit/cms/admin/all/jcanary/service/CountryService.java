/*******************************************************************************
 * Class        CountryService
 * Created date 2017/02/23
 * Lasted date  2017/02/23
 * Author       TranLTH
 * Change log   2017/02/2301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.jcanary.dto.CountryDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CountrySearchDto;
import vn.com.unit.common.dto.PageWrapper;

//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.dto.CountryDto;
//import vn.com.unit.jcanary.dto.CountrySearchDto;

/**
 * CountryService
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface CountryService {
    /**
     * initScreenCountryList
     *
     * @param mav
     * @author TranLTH
     */
    public void initScreenCountryList(ModelAndView mav);
    /**
     * addOrEditCountry
     *
     * @param countryDto
     * @throws Exception
     * @author TranLTH
     */
    public void addOrEditCountry(CountryDto countryDto) ;
    /**
     * deleteCountry
     *
     * @param countryId
     * @author TranLTH
     */
    public void deleteCountry(String countryId);   
    /**
     * search
     *
     * @param page
     * @param countrySearchDto
     * @return PageWrapper<CountryDto>
     * @author TranLTH
     */
    public PageWrapper<CountryDto> search(int page, CountrySearchDto countrySearchDto);
    /**
     * getCountryDto
     *
     * @param countryId
     * @return CountryDto
     * @author TranLTH
     */
    public CountryDto getCountryDto(Long countryId); 
    /**
     * findAllCountryListByCondition
     *
     * @param countryDto
     * @param language
     * @return
     * @author TranLTH
     */
    public List<CountryDto> findAllCountryListByCondition (CountryDto countryDto, String language);
}
