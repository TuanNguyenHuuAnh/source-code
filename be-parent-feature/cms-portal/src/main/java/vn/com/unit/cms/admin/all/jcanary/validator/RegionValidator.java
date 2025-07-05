/*******************************************************************************
 * Class        RegionValidator
 * Created date 2017/03/20
 * Lasted date  2017/03/20
 * Author       TranLTH
 * Change log   2017/03/2001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.jcanary.dto.CountryDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
import vn.com.unit.cms.admin.all.jcanary.service.CountryService;
import vn.com.unit.cms.admin.all.jcanary.service.CmsRegionService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.jcanary.dto.CountryDto;
//import vn.com.unit.jcanary.dto.RegionDto;
//import vn.com.unit.jcanary.service.CountryService;
//import vn.com.unit.jcanary.service.RegionService;

/**
 * RegionValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Component
public class RegionValidator implements Validator{

	@Autowired
    CmsRegionService regionService;

    @Autowired
    CountryService countryService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return RegionDto.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        RegionDto regionDto = (RegionDto) target;        
        if (null == regionDto.getRegionId()){
            checkDuplicateCode(regionDto, errors);
        }  else{
            checkExpiredRegion(regionDto.getRegionId(), errors);
        }
        if (null != regionDto.getmCountryId() ){
            checkExpiredCountry(regionDto.getmCountryId(),errors);
        }
    }
    /**
     * checkDuplicateCode
     *
     * @param regionDto
     * @param errors
     * @author TranLTH
     */
    private void checkDuplicateCode(RegionDto regionDto, Errors errors){
        String regionCode = regionDto.getRegionCode();
        RegionDto searchRegionDto = new RegionDto();
        searchRegionDto.setRegionCode(regionDto.getRegionCode());
        List<RegionDto> checkRegionDtos = regionService.findAllRegionListByCondition(searchRegionDto, "vi");  
        
        if (checkRegionDtos.size() > 0){            
            for (RegionDto checkRegionDto : checkRegionDtos){
                // Check duplicate code
                String checkRegionCode = checkRegionDto.getRegionCode();
                if (regionCode.equals(checkRegionCode)){
                    String[] errorArgs = new String[1];
                    errorArgs[0] = regionCode;
                    errors.rejectValue("regionCode", "message.error.region.code.existed", errorArgs, ConstantCore.EMPTY);
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
}
