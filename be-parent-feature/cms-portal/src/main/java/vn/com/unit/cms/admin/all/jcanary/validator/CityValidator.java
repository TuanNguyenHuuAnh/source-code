/*******************************************************************************
 * Class        CityValidator
 * Created date 2017/03/21
 * Lasted date  2017/03/21
 * Author       TranLTH
 * Change log   2017/03/2101-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.jcanary.dto.CityDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CountryDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
import vn.com.unit.cms.admin.all.jcanary.service.CityService;
import vn.com.unit.cms.admin.all.jcanary.service.CountryService;
import vn.com.unit.cms.admin.all.jcanary.service.CmsRegionService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.jcanary.dto.CityDto;
//import vn.com.unit.jcanary.dto.CountryDto;
//import vn.com.unit.jcanary.dto.RegionDto;
//import vn.com.unit.jcanary.service.CityService;
//import vn.com.unit.jcanary.service.CountryService;
//import vn.com.unit.jcanary.service.RegionService;

/**
 * CityValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Component
public class CityValidator implements Validator{
    /** CountryService */
    @Autowired
    CountryService countryService;
    
    /** RegionService */
    @Autowired
    CmsRegionService regionService;
    
    /** CityService */
    @Autowired
    CityService cityService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return CityDto.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        CityDto cityDto = (CityDto) target;        
        if (null == cityDto.getCityId()){
            checkDuplicateCode(cityDto, errors);
        }else{
            checkExpiredCity(cityDto.getCityId(), errors);
        }
        if (null != cityDto.getRegionCountry()){
            String[] arrayRegionId = cityDto.getRegionCountry().toString().split("@");
            Long mCountryId = Long.parseLong(arrayRegionId[1]);
            Long mRegionId = Long.parseLong(arrayRegionId[0]);
            if (null != mCountryId ){
                checkExpiredCountry(mCountryId,errors);
            }
            if (null != mRegionId ){
                checkExpiredRegion(mRegionId,errors);
            }  
        }             
    }
    /**
     * checkDuplicateCode
     *
     * @param cityDto
     * @param errors
     * @author TranLTH
     */
    private void checkDuplicateCode(CityDto cityDto, Errors errors){
        String cityCode = cityDto.getCityCode();
        CityDto searchCityDto = new CityDto();
        searchCityDto.setCityCode(cityDto.getCityCode());
        List<CityDto> checkCityDtos = cityService.findAllCityListByCondition(searchCityDto, "vi");
        
        if (null != checkCityDtos){
            for (CityDto checkCityDto : checkCityDtos){
                String checkCityCode = checkCityDto.getCityCode();
                if (cityCode.equals(checkCityCode)){
                    String[] errorArgs = new String[1];
                    errorArgs[0] = cityCode;
                    errors.rejectValue("cityCode", "message.error.city.code.existed", errorArgs, ConstantCore.EMPTY);
                }
            }                                
        }
    }
    /**
     * checkExpiredCountry
     *
     * @param countryId
     * @param errors
     * @author TranLTH
     */
    private void checkExpiredCountry (Long countryId, Errors errors){
        CountryDto listCountryDto = countryService.getCountryDto(countryId);
        if (null == listCountryDto){
            String[] errorArgs = new String[1];
            errorArgs[0] = countryId + "";
            errors.rejectValue("regionCountry", "message.error.country.id.delete", errorArgs, ConstantCore.EMPTY);
        }
    }
    /**
     * checkExpiredRegion
     *
     * @param regionId
     * @param errors
     * @author TranLTH
     */
    private void checkExpiredRegion (Long regionId, Errors errors){
        RegionDto listRegionDto = regionService.getRegionDto(regionId);
        if (null == listRegionDto){
            String[] errorArgs = new String[1];
            errorArgs[0] = regionId + "";
            errors.rejectValue("regionCountry", "message.error.region.id.delete", errorArgs, ConstantCore.EMPTY);
        }
    }
    /**
     * checkExpiredCity
     *
     * @param cityId
     * @param errors
     * @author TranLTH
     */
    private void checkExpiredCity (Long cityId, Errors errors){
        CityDto listCityDto = cityService.getCityDto(cityId);
        if (null == listCityDto){
            String[] errorArgs = new String[1];
            errorArgs[0] = cityId + "";
            errors.rejectValue("cityId", "message.error.city.id.delete", errorArgs, ConstantCore.EMPTY);
        }
    }
}
