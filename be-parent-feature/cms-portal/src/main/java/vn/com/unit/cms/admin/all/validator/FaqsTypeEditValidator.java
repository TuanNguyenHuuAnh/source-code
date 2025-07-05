/*******************************************************************************
 * Class        ：FaqsTypeEditValidator
 * Created date ：2016/06/01
 * Lasted date  ：2016/06/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.FaqsTypeEditDto;
import vn.com.unit.cms.admin.all.dto.FaqsTypeLanguageDto;
import vn.com.unit.cms.admin.all.entity.FaqsType;
import vn.com.unit.cms.admin.all.entity.FaqsTypeLanguage;
import vn.com.unit.cms.admin.all.service.FaqsTypeService;

/**
 * FaqsTypeEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Component
public class FaqsTypeEditValidator implements Validator {

    @Autowired
    FaqsTypeService faqsTypeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return FaqsTypeEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        // FaqsEditDto
        FaqsTypeEditDto dto = (FaqsTypeEditDto) target;

        // check exists code
        checkExistsCode(dto, errors);

        // check Product exists alias
        if (checkExistsAlias(dto, errors)) {
            return;
        }
    }

    /**
     * check exists code
     *
     * @param dto
     *            FaqsTypeEditDto
     * @param errors
     * @author hand
     */
    private void checkExistsCode(FaqsTypeEditDto dto, Errors errors) {
        String code = dto.getCode();

        FaqsType entity = faqsTypeService.findFaqsTypeByCode(code);
        if (entity != null) {
            Long faqsId = dto.getId();
            Long idDb = entity.getId();

            if (faqsId == null || !faqsId.equals(idDb)) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.faqs.type.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
    }

    private boolean checkExistsAlias(FaqsTypeEditDto dto, Errors errors) {
        int numberError = 0;
        if (CollectionUtils.isNotEmpty(dto.getTypeFaqsLanguageList())) {
            for (int i = 0, sz = dto.getTypeFaqsLanguageList().size(); i < sz; i++) {
                FaqsTypeLanguageDto dtoLang = dto.getTypeFaqsLanguageList().get(i);
                FaqsTypeLanguage langEntity = faqsTypeService.findFaqsTypeLangByAlias(dtoLang.getLinkAlias(), dtoLang.getLanguageCode(), dto.getCustomerTypeId());
                if (langEntity != null && !langEntity.getId().equals(dtoLang.getId())) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = dtoLang.getLinkAlias();
                    errors.rejectValue("typeFaqsLanguageList[" + i + "].linkAlias", "error.message.key.seo.existed", errorArgs, ConstantCore.EMPTY);
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