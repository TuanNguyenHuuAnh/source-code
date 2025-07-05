/*******************************************************************************
 * Class        RegionService
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       TranLTH
 * Change log   2017/02/1401-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionSearchDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;

//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.dto.RegionDto;
//import vn.com.unit.jcanary.dto.RegionSearchDto;
//import vn.com.unit.jcanary.dto.Select2Dto;

/**
 * RegionService
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public interface CmsRegionService {
    /**
     * initScreenRegionList
     *
     * @param mav
     * @author TranLTH
     */
    public void initScreenRegionList(ModelAndView mav, String language);
    /**
     * addOrEditRegion
     *
     * @param regionDto
     * @author TranLTH
     */
    public void addOrEditRegion(RegionDto regionDto) ;
    /**
     * deleteRegion
     *
     * @param regionId
     * @author TranLTH
     */
    public void deleteRegion(String regionId);
    /**
     * search
     *
     * @param page
     * @param regionDto
     * @param language
     * @return PageWrapper<RegionDto>
     * @author TranLTH
     */
    public PageWrapper<RegionDto> search(int page, RegionSearchDto regionSearchDto);
    /**
     * getRegionDto
     *
     * @param regionId
     * @return RegionDto
     * @author TranLTH
     */
    public RegionDto getRegionDto(Long regionId);
    /**
     * findAllRegionListByCondition
     *
     * @param regionDto
     * @param language
     * @return List<RegionDto>
     * @author TranLTH
     */
    public List<RegionDto> findAllRegionListByCondition (RegionDto regionDto, String language);
    
    
    
    /** findAllRegionCountry
     *
     * @param countryId
     * @param language
     * @return
     * @author hangnkm
     */
    public List<Select2Dto> findAllRegionCountry(String language);
}