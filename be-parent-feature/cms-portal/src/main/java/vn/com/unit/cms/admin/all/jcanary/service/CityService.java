/*******************************************************************************
 * Class        CityService
 * Created date 2017/02/17
 * Lasted date  2017/02/17
 * Author       TranLTH
 * Change log   2017/02/1701-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

//import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.jcanary.dto.CityDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CitySearchDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.CitySearchDto;
//import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;

//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.dto.CityDto;
//import vn.com.unit.jcanary.dto.CitySearchDto;
//import vn.com.unit.jcanary.dto.Select2Dto;

/**
 * CityService
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface CityService {
    /**
     * initScreenCityList
     *
     * @param mav
     * @param language
     * @author TranLTH
     */
    public void initScreenCityList(ModelAndView mav, CityDto cityDto, String language);

    /**
     * addOrEditCity
     *
     * @param cityDto
     * @author TranLTH
     */
    public void addOrEditCity(CityDto cityDto) ;

    /**
     * deleteCity
     *
     * @param cityId
     * @author TranLTH
     */
    public void deleteCity(String cityId);

    /**
     * search
     *
     * @param page
     * @param citySearchDto
     * @return PageWrapper<CityDto>
     * @author TranLTH
     */
    public PageWrapper<CityDto> search(int page, CitySearchDto citySearchDto);

    /**
     * getCityDto
     *
     * @param cityId
     * @return CityDto
     * @author TranLTH
     */
    public CityDto getCityDto(Long cityId);

    /**
     * findAllCityListByCondition
     *
     * @param cityDto
     * @param language
     * @return List<CityDto>
     * @author TranLTH
     */
    public List<CityDto> findAllCityListByCondition (CityDto cityDto, String language);
    
    /**
     * findAllCityList
     *
     * @param language
     * @return List<CityDto>
     * @author hand
     */
    public List<CityDto> findAllCityList(String language);
    
	/**
	 * @param countryCode
	 * @param language
	 * @return
	 * @author longpnt
	 */
//	List<Select2Dto> findAllCityListByCondition(String countryCode, String language);
	
	/**
	 * findAllCityForSelect2
	 * 
	 * @param term
	 * @return List<Select2Dto>
	 * @author longpnt
	 */
//	List<Select2Dto> findAllCityForSelect2(String term);
	
	
	
	/** findAllCity
	 *
	 * @param language
	 * @return
	 * @author hangnkm
	 */
//	List<Select2Dto> findAllCity(String language);
	
	
	List<Select2Dto> findAllCityByRegionId(String language, Long regionId, List<String> ctype);
	
	
	
}
