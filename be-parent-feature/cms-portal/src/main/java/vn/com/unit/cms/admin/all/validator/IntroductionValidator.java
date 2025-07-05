/*******************************************************************************
 * Class        ：IntroductionCategoryValidator
 * Created date ：2016/03/24
 * Lasted date  ：2016/03/24
 * Author       ：ThuyDTN
 * Change log   ：2016/02/20：01-00 ThuyDTN create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.IntroductionDto;
import vn.com.unit.cms.admin.all.dto.IntroductionLanguageDto;
import vn.com.unit.cms.admin.all.service.IntroductionService;

/**
 * IntroductionCategoryValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author ThuyDTN
 */
@Component
public class IntroductionValidator implements Validator {

    @Autowired
    IntroductionService introductionService;

    @Override
    public boolean supports(Class<?> clazz) {
        return IntroductionDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IntroductionDto introductionDto = (IntroductionDto) target;
        validateCode(errors, introductionDto);
        validateAlias(introductionDto, errors);
    }

	private void validateCode(Errors errors, IntroductionDto introductionDto) {
		if (introductionDto.getId() == null) {
            int codeCategoryCount = introductionService
                    .countIntroductionByCode(introductionDto.getCode());
            if (codeCategoryCount > 0) {
                String[] errorArgs = new String[1];
                errorArgs[0] = introductionDto.getCode();
                errors.rejectValue("code",
                        "introduction.code.existed", errorArgs,
                        ConstantCore.EMPTY);
            }
        } else {
        	IntroductionDto validateObj = introductionService.getDetailById(introductionDto.getId());
            if (validateObj != null && validateObj.getCode() != null
                    && !validateObj.getCode()
                            .equals(introductionDto.getCode())) {
                String[] errorArgs = new String[1];
                errorArgs[0] = introductionDto.getCode();
                errors.rejectValue("code",
                        "introduction.code.existed", errorArgs,
                        ConstantCore.EMPTY);
            }
        }
	}
    
    private void validateAlias(IntroductionDto dto, Errors errors) {
        if (CollectionUtils.isNotEmpty(dto.getInfoByLanguages())) {
            for (int i = 0, sz = dto.getInfoByLanguages().size(); i < sz; i++) {
                IntroductionLanguageDto dtoLang = dto.getInfoByLanguages().get(i);

                int countSameAlias = introductionService.countCategoryItemWithAlias(dtoLang.getLanguageCode(), dto.getCategoryId(), dtoLang.getLinkAlias(), dtoLang.getId());
                if (countSameAlias > 0) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = dtoLang.getLinkAlias();
                    errors.rejectValue("infoByLanguages[" + i + "].linkAlias", "error.message.key.seo.existed", errorArgs, ConstantCore.EMPTY);

                    if (dto.getIndexLangActive() == null) {
                        dto.setIndexLangActive(i);
                    }
                }
            }
        }
    }
}