/*******************************************************************************
 * Class        CountryValidator
 * Created date 2017/03/18
 * Lasted date  2017/03/18
 * Author       TranLTH
 * Change log   2017/03/1801-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.jcanary.dto.CountryDto;
import vn.com.unit.cms.admin.all.jcanary.service.CountryService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

//import vn.com.unit.constant.ConstantCore;
//import vn.com.unit.jcanary.dto.CountryDto;
//import vn.com.unit.jcanary.service.CountryService;

/**
 * CountryValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Component
public class CountryValidator implements Validator{
    /** CountryService */
    @Autowired
    CountryService countryService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return CountryDto.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        CountryDto countryDto = (CountryDto) target;        
        if (null == countryDto.getCountryId() ){
            // Check duplicate code country
            checkDuplicateCode(countryDto, errors);            
        }else{
            // Check expired country
            checkExpiredCountry(countryDto.getCountryId(), errors);
        }
        if (null != countryDto.getWebCode()){
            // Check duplicate web code
            checkDuplicateWebCode(countryDto, errors);
        }        
    }
    /**
     * checkDuplicateCode
     *
     * @param countryDto
     * @param errors
     * @author TranLTH
     */
    private void checkDuplicateCode(CountryDto countryDto, Errors errors){
        String countryCode = countryDto.getCountryCode();
        CountryDto searchCountryDto = new CountryDto();
        searchCountryDto.setCountryCode(countryDto.getCountryCode());
        List<CountryDto> checkCountryDtos = countryService.findAllCountryListByCondition(searchCountryDto, "vi");  
        
        if (checkCountryDtos.size() > 0){            
            for (CountryDto checkCountryDto : checkCountryDtos){
                // Check duplicate code
                String checkCountryCode = checkCountryDto.getCountryCode();
                if (countryCode.equals(checkCountryCode)){
                    String[] errorArgs = new String[1];
                    errorArgs[0] = countryCode;
                    errors.rejectValue("countryCode", "message.error.country.code.existed", errorArgs, ConstantCore.EMPTY);
                }               
            }                       
        }
    }
    /**
     * checkDuplicateWebCode
     *
     * @param countryDto
     * @param errors
     * @author TranLTH
     */
    private void checkDuplicateWebCode(CountryDto countryDto, Errors errors){      
        String webCode = countryDto.getWebCode();
        CountryDto searchCountryDto = new CountryDto();
        searchCountryDto.setWebCode(countryDto.getWebCode());        
        List<CountryDto> checkCountryDtos = countryService.findAllCountryListByCondition(searchCountryDto, "vi");     
        
        if (checkCountryDtos.size() > 0){            
            for (CountryDto checkCountryDto : checkCountryDtos){               
                // Check duplicate web code
                String checkCountryCode = checkCountryDto.getCountryCode();
                String checkWebCode = checkCountryDto.getWebCode();
                if (!checkCountryCode.equals(countryDto.getCountryCode()) && webCode.equals(checkWebCode)){
                    String[] errorArgs = new String[1];
                    errorArgs[0] = webCode;
                    errors.rejectValue("webCode", "message.error.web.code.existed", errorArgs, ConstantCore.EMPTY);
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
}
