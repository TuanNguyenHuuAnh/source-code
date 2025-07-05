/*******************************************************************************
 * Class        ：FaqsCategoryEditValidator
 * Created date ：2016/06/01
 * Lasted date  ：2016/06/01
 * Author       ：TaiTM
 * Change log   ：2017/03/01：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.dto.FaqsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategoryLanguageDto;
import vn.com.unit.cms.admin.all.entity.FaqsCategory;
import vn.com.unit.cms.admin.all.entity.FaqsCategoryLanguage;
import vn.com.unit.cms.admin.all.service.FaqsCategoryService;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.MessageList;

/**
 * FaqsCategoryEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Component
public class FaqsCategoryEditValidator implements Validator {

    @Autowired
    private FaqsCategoryService faqsCategoryService;

    @Autowired
    private MessageSource msg;

    @Override
    public boolean supports(Class<?> clazz) {
        return FaqsCategoryEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FaqsCategoryEditDto dto = (FaqsCategoryEditDto) target;

        // check exists code
        if (checkExistsFaqsCode(dto, errors)) {
            return;
        }

        // check exists alias
        if (checkExistsAlias(dto, errors)) {
            return;
        }
    }

    /**
     * @author TaiTM
     */
    private boolean checkExistsFaqsCode(FaqsCategoryEditDto editDto, Errors errors) {
        MessageList messageList = editDto.getMessageList();
        String code = editDto.getCode();
        FaqsCategory entity = faqsCategoryService.findByCode(code);
        if (entity != null) {
            Long faqsId = editDto.getId();
            Long idDb = entity.getId();

            // Check unique bankNo
            if (faqsId == null || !faqsId.equals(idDb)) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.faqs.category.code.existed", errorArgs, ConstantCore.EMPTY);

                String error = msg.getMessage("message.error.faqs.category.code.existed", errorArgs,
                        new Locale(editDto.getLanguageCode()));
                messageList = CmsUtils.setErrorForValidate(error, messageList);
                editDto.setMessageList(messageList);

                return true;
            }
        }
        return false;
    }

    /**
     * check exists alias
     *
     * @param dto
     * @param errors
     */
    private boolean checkExistsAlias(FaqsCategoryEditDto editDto, Errors errors) {
        int numberError = 0;

        MessageList messageList = editDto.getMessageList();

        if (CollectionUtils.isNotEmpty(editDto.getCategoryLanguageList())) {
            for (int i = 0, sz = editDto.getCategoryLanguageList().size(); i < sz; i++) {
                FaqsCategoryLanguageDto dtoLang = editDto.getCategoryLanguageList().get(i);
                FaqsCategoryLanguage entityLanguage = faqsCategoryService.findByAliasCustomerId(
                        dtoLang.getKeywordsSeo(), dtoLang.getLanguageCode(), editDto.getCustomerTypeId());
                if (entityLanguage != null && !entityLanguage.getId().equals(dtoLang.getId())) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = dtoLang.getKeywordsSeo();
                    errors.rejectValue("categoryLanguageList[" + i + "].keywordsSeo", "error.message.key.seo.existed",
                            errorArgs, ConstantCore.EMPTY);
                    numberError++;

                    if (editDto.getIndexLangActive() == null) {
                        editDto.setIndexLangActive(i);
                    }

                    String error = msg.getMessage("error.message.key.seo.existed", errorArgs,
                            new Locale(editDto.getLanguageCode()));
                    messageList = CmsUtils.setErrorForValidate(error, messageList);
                }
            }
        }
        editDto.setMessageList(messageList);

        return numberError > 0;
    }
}