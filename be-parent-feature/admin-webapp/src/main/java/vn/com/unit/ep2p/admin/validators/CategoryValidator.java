/*******************************************************************************
 * Class        :CategoryValidator
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.validators;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.CategoryService;
import vn.com.unit.ep2p.core.efo.dto.EfoCategoryDto;
//import vn.com.unit.ep2p.core.efo.dto.EfoCategoryLangDto;

/**
 * CategoryValidator
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Component
public class CategoryValidator implements Validator {

    @Autowired
    CategoryService categoryService;
    
	@Override
    public boolean supports(Class<?> clazz) {
        return EfoCategoryDto.class.equals(clazz);
    }

	@Override
    public void validate(Object target, Errors errors) {
//	    EfoCategoryDto destination = (EfoCategoryDto) target;
//        
//        List<EfoCategoryLangDto> categoryLanguageDtos = destination.getListCategoryLangDto();
//        
//        for (EfoCategoryLangDto categoryLanguageDto : categoryLanguageDtos) {
//        	List<EfoCategoryLangDto> checkLanguageDto = categoryService.getCategoryLanguageCheck(categoryLanguageDto.getName(), categoryLanguageDto.getLangCode(), categoryLanguageDto.getCategoryId(), destination.getCompanyId());
//        	if(checkLanguageDto.size()>0) {
//        		String[] errorArgs = new String[1];
//                errorArgs[0] = ""+categoryLanguageDtos.indexOf(categoryLanguageDto);
//        		errors.rejectValue("name", "categoryLang.name.existed", errorArgs, ConstantCore.EMPTY);
//        		break;
//        	}		
//		}
    }
}