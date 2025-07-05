/*******************************************************************************
 * Class        DistrictValidator
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
import vn.com.unit.cms.admin.all.jcanary.dto.DistrictDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
import vn.com.unit.cms.admin.all.jcanary.service.CityService;
import vn.com.unit.cms.admin.all.jcanary.service.CountryService;
import vn.com.unit.cms.admin.all.jcanary.service.DistrictService;
import vn.com.unit.cms.admin.all.jcanary.service.CmsRegionService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.jcanary.dto.CityDto;
//import vn.com.unit.jcanary.dto.CountryDto;
//import vn.com.unit.jcanary.dto.DistrictDto;
//import vn.com.unit.jcanary.dto.RegionDto;
//import vn.com.unit.jcanary.service.CityService;
//import vn.com.unit.jcanary.service.CountryService;
//import vn.com.unit.jcanary.service.DistrictService;
//import vn.com.unit.jcanary.service.RegionService;

/**
 * DistrictValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Component
public class DistrictValidator implements Validator{

	@Autowired
    DistrictService districtService;
    
    @Autowired
    CountryService countryService;
    
    @Autowired
    CmsRegionService regionService;

    @Autowired
    CityService cityService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return DistrictDto.class.equals(clazz);
    }
    
    @Override
    public void validate(Object target, Errors errors) {
        DistrictDto districtDto = (DistrictDto) target;        
        if (null == districtDto.getDistrictId()){
            checkDuplicateCode(districtDto, errors);
        } else{
            checkDistrictIdDeleted(districtDto, errors);
        }
        //Get cityId, regionId, countryId
        String[] cityRegionCountry = districtDto.getCityRegionCountry().toString().split("@");
        Long cityId = Long.parseLong(cityRegionCountry[0]);
        Long regionId =Long.parseLong(cityRegionCountry[1]);
        Long countryId =Long.parseLong(cityRegionCountry[2]);
        if (null != districtDto.getCityRegionCountry()){
            if (null != countryId ){
                checkExpiredCountry(countryId, errors);
            }
            if (null != regionId ){
                checkExpiredRegion(regionId, errors);
            }            
            if (null != cityId ){
                checkExpiredCity(cityId, errors);
            }
        }        
    }
    /**
     * checkDuplicateCode
     *
     * @param districtDto
     * @param errors
     * @author TranLTH
     */
    private void checkDuplicateCode(DistrictDto districtDto, Errors errors){
        String districtCode = districtDto.getDistrictCode();
        DistrictDto districtSearchCodeDto = new DistrictDto();
        districtSearchCodeDto.setDistrictCode(districtDto.getDistrictCode());
        List<DistrictDto> checkDistrictDtos = districtService.findAllDistrictListByCondition(districtSearchCodeDto, "vi");         
        if (null != checkDistrictDtos){
            for (DistrictDto checkDistrictDto : checkDistrictDtos){
                String checkDistrictCode = checkDistrictDto.getDistrictCode();
                if (districtCode.equals(checkDistrictCode)){
                    String[] errorArgs = new String[1];
                    errorArgs[0] = districtCode;
                    errors.rejectValue("districtCode", "message.error.district.code.existed", errorArgs, ConstantCore.EMPTY);
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
            errors.rejectValue("countryId", "message.error.country.id.delete", errorArgs, ConstantCore.EMPTY);
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
            errors.rejectValue("regionId", "message.error.region.id.delete", errorArgs, ConstantCore.EMPTY);
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
    /**
     * checkDistrictIdDeleted
     *
     * @param districtDto
     * @param errors
     * @author TranLTH
     */
    private void checkDistrictIdDeleted(DistrictDto districtDto, Errors errors){
        
        DistrictDto listDistrictDto = districtService.getDistrictDto(districtDto.getDistrictId());
             
        if (null == listDistrictDto){
            String[] errorArgs = new String[1];
            errorArgs[0] = districtDto.getDistrictId() + "";
            errors.rejectValue("districtId", "message.error.district.id.delete", errorArgs, ConstantCore.EMPTY);          
        }
    }
}
