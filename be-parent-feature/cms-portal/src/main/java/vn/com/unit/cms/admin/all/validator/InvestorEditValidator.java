/*******************************************************************************
 * Class        ：investorEditValidator
 * Created date ：2017/05/04
 * Lasted date  ：2017/05/04
 * Author       ：hand
 * Change log   ：2017/05/04：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.InvestorEditDto;
import vn.com.unit.cms.admin.all.dto.InvestorLanguageEditDto;
import vn.com.unit.cms.admin.all.entity.Investor;
import vn.com.unit.cms.admin.all.entity.InvestorCategory;
import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.admin.all.service.InvestorCategoryService;
import vn.com.unit.cms.admin.all.service.InvestorService;

/**
 * investorEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Component
public class InvestorEditValidator implements Validator {

    /** investorService */
    @Autowired
    InvestorService investorService;

    @Autowired
    CustomerTypeService customerTypeService;
    
    @Autowired
	InvestorCategoryService investorCategorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return InvestorEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // investorEditDto
        InvestorEditDto dto = (InvestorEditDto) target;

        // check investor exists code
        if (checkExistsCode(dto, errors)) {
            return;
        }

        // check investor exists alias
        if (checkExistsAlias(dto, errors)) {
            return;
        }

		if (checkExistsInvestorCategoryType(dto, errors)) {
			return;
		}
    }

    /**
     * check investor exists code
     *
     * @param dto
     *            investorEditDto
     * @param errors
     * @author hand
     */
    private boolean checkExistsCode(InvestorEditDto dto, Errors errors) {
        String code = dto.getCode();
        Investor entity = investorService.getInvestorByCode(code);
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

    /**
     * check exists alias
     *
     * @param dto
     *            investorEditDto
     * @param errors
     * @author hand
     */
    private boolean checkExistsAlias(InvestorEditDto dto, Errors errors) {
        int numberError = 0;
        if (CollectionUtils.isNotEmpty(dto.getInvestorLanguageList())) {
            for (int i = 0, sz = dto.getInvestorLanguageList().size(); i < sz; i++) {
                InvestorLanguageEditDto dtoLang = dto.getInvestorLanguageList().get(i);
                InvestorLanguageEditDto investorLanguage = investorService.getByAliasAndCategoryId(dto.getCategoryId(), dtoLang.getLinkAlias(), dtoLang.getLanguageCode());
                if (investorLanguage != null && !investorLanguage.getId().equals(dto.getId())) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = dtoLang.getLinkAlias();
                    errors.rejectValue("investorLanguageList[" + i + "].linkAlias", "error.message.key.seo.existed", errorArgs, ConstantCore.EMPTY);
                    numberError++;
                    if (dto.getIndexLangActive() == null) {
                        dto.setIndexLangActive(i);
                    }
                }
            }

        }
        return numberError > 0;
    }
       
	private boolean checkExistsInvestorCategoryType(InvestorEditDto dto, Errors errors) {
		Long categoryId = dto.getCategoryId();
		if (null != categoryId) {
			InvestorCategory investorCategory = investorCategorService.findById(categoryId);
			if (null != investorCategory) {
				if (investorCategory.getInvestorCategoryType() == null) {
					String[] errorArgs = new String[1];
					errorArgs[0] = investorCategory.getName();
					errors.rejectValue("categoryId", "error.message.category.type", errorArgs, ConstantCore.EMPTY);
					return true;
				}
			}
		}
		return false;
	}
}
