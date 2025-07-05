package vn.com.unit.cms.admin.all.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryLanguageDto;
import vn.com.unit.cms.admin.all.entity.InvestorCategory;
import vn.com.unit.cms.admin.all.service.InvestorCategoryService;

@Component
public class InvestoryCategoryValidator implements Validator {

	@Autowired
	InvestorCategoryService investorCategorService;
	
	
	@Override
    public boolean supports(Class<?> clazz) {
		return InvestorCategoryDto.class.equals(clazz);
	}
	
	@Override
    public void validate(Object target, Errors errors) {
        // InvestorCategoryDto
		InvestorCategoryDto dto = (InvestorCategoryDto) target;
		
		  // check investor exists code
        if (checkExistsCode(dto, errors)) {
            return;
        }

        // check investor exists alias
        if (checkExistsAlias(dto, errors)) {
            return;
        }
	}

	private boolean checkExistsCode(InvestorCategoryDto dto, Errors errors) {
		 String code = dto.getCode();
		 InvestorCategory entity = investorCategorService.getInvestorCategoryByCode(code);
		 if (entity != null) {
	            Long investorId = dto.getId();
	            Long idDb = entity.getId();

	            // Check unique bankNo
	            if (investorId == null || !investorId.equals(idDb)) {
	                String[] errorArgs = new String[1];
	                errorArgs[0] = code;
	                errors.rejectValue("code", "message.error.investor.code.existed", errorArgs, ConstantCore.EMPTY);
	                return true;
	            }
	        }
		 return false;
	 }
	 
	 private boolean checkExistsAlias(InvestorCategoryDto dto, Errors errors) {
		 int numberError = 0;
	        if (CollectionUtils.isNotEmpty(dto.getInfoByLanguages())) {
	            for (int i = 0, sz = dto.getInfoByLanguages().size(); i < sz; i++) {
	            	InvestorCategoryLanguageDto dtoLang = dto.getInfoByLanguages().get(i);
	            	InvestorCategoryLanguageDto investorLanguage = investorCategorService.getByAliasAndCategoryId(dto.getId(),dto.getCustomerTypeId(), dtoLang.getLinkAlias(), dtoLang.getLanguageCode());
	                if (investorLanguage != null && !investorLanguage.getId().equals(dtoLang.getId())) {
	                    String[] errorArgs = new String[1];
	                    errorArgs[0] = dtoLang.getLinkAlias();
	                    errors.rejectValue("infoByLanguages[" + i + "].linkAlias", "error.message.key.seo.existed", errorArgs, ConstantCore.EMPTY);
	                    numberError++;
	                    if (dto.getIndexLangActive() == null) {
	                        dto.setIndexLangActive(i);
	                    }
	                }
	            }
	        }
	        return numberError > 0;
	 }
	
}
