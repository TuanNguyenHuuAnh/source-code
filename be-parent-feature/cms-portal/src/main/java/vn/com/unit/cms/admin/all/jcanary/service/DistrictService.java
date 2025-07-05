/*******************************************************************************
 * Class        DistrictService
 * Created date 2017/02/20
 * Lasted date  2017/02/20
 * Author       TranLTH
 * Change log   2017/02/2001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.jcanary.dto.DistrictDto;
import vn.com.unit.cms.admin.all.jcanary.dto.DistrictSearchDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;

//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.dto.DistrictDto;
//import vn.com.unit.jcanary.dto.DistrictSearchDto;
//import vn.com.unit.jcanary.dto.Select2Dto;

/**
 * DistrictService
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface DistrictService {
    /**
     * initScreenDistrictList
     *
     * @param mav
     * @param language
     * @author TranLTH
     */
    public void initScreenDistrictList(ModelAndView mav, DistrictDto districtDto,  String language);
    /**
     * addOrEditDistrict
     *
     * @param districtDto
     * @author TranLTH
     */
    public void addOrEditDistrict(DistrictDto districtDto) ;
    /**
     * deleteDistrict
     *
     * @param districtId
     * @author TranLTH
     */
    public void deleteDistrict(String districtId);
   /**
    * search
    *
    * @param page
    * @param districtSearchDto
    * @return
    * @author TranLTH
    */
    public PageWrapper<DistrictDto> search(int page, DistrictSearchDto districtSearchDto);
    /**
     * getDistrictDto
     *
     * @param districtId
     * @return DistrictDto
     * @author TranLTH
     */
    public DistrictDto getDistrictDto(Long districtId);
    /**
     * findAllDistrictListByCondition
     *
     * @param districtDto
     * @param language
     * @return List<DistrictDto>
     * @author TranLTH
     */
    public List<DistrictDto> findAllDistrictListByCondition (DistrictDto districtDto, String language);
	/**
	 * @param cityId
	 * @param language
	 * @return List<Select2Dto>
	 * @author LongPNT
	 */
	public List<Select2Dto> findAllDistrictListByCityId(Long cityId, String language);
	
	
	/** findAllDistrictListByCityIdByType
	 *
	 * @param cityId
	 * @param language
	 * @param type
	 * @return
	 * @author hangnkm
	 */
	public List<Select2Dto> findAllDistrictListByCityIdByType(Long cityId, String language, List<String> type);
	
	public List<Select2Dto> findAllDistrictListByConditionForCombobox (DistrictDto districtDto, String language);
	
	/**
	 * findAllDistrictForSelect2
	 * 
	 * @param term
	 * @return List<Select2Dto>
	 * @author longpnt
	 */
	public List<Select2Dto> findAllDistrictForSelect2(String term);
}
