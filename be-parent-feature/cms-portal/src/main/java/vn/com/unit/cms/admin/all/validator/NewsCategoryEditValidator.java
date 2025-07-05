/*******************************************************************************
 * Class        ：NewsCategoryEditValidator
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

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.cms.admin.all.dto.NewsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryLanguageDto;
import vn.com.unit.cms.admin.all.entity.NewsCategory;
import vn.com.unit.cms.admin.all.entity.NewsCategoryLanguage;
//import vn.com.unit.cms.admin.all.entity.NewsType;
import vn.com.unit.cms.admin.all.service.NewsCategoryService;
//import vn.com.unit.cms.admin.all.service.NewsTypeService;
import vn.com.unit.cms.core.utils.CmsUtils;

/**
 * NewsCategoryEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Component
public class NewsCategoryEditValidator implements Validator {

    @Autowired
    private NewsCategoryService newsCategoryService;

    @Autowired
    private MessageSource msg;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewsCategoryEditDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        // NewsCategoryEditDto
        NewsCategoryEditDto dto = (NewsCategoryEditDto) target;

        // check exists code of News
        if (checkExistsNewsCode(dto, errors)) {
            return;
        }

        // check exists alias
        if (checkExistsAlias(dto, errors)) {
            return;
        }
    }

    /**
     * @param errors
     * @author TaiTM
     */
    private boolean checkExistsNewsCode(NewsCategoryEditDto editDto, Errors errors) {
        String code = editDto.getCode();
        MessageList messageList = editDto.getMessageList();

        NewsCategory entity = newsCategoryService.findByCode(code);
        if (entity != null) {
            Long newsId = editDto.getId();
            Long idDb = entity.getId();

            // Check unique bankNo
            if (newsId == null || !newsId.equals(idDb)) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.news.category.code.existed", errorArgs, ConstantCore.EMPTY);

                String error = msg.getMessage("message.error.news.category.code.existed", errorArgs,
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
     */
    private boolean checkExistsAlias(NewsCategoryEditDto editDto, Errors errors) {
        int numberError = 0;
        MessageList messageList = editDto.getMessageList();

        if (CollectionUtils.isNotEmpty(editDto.getCategoryLanguageList())) {
            for (int i = 0, sz = editDto.getCategoryLanguageList().size(); i < sz; i++) {
                NewsCategoryLanguageDto dtoLang = editDto.getCategoryLanguageList().get(i);

                NewsCategoryLanguage entityLanguage = newsCategoryService.findByAliasTypeId(dtoLang.getLinkAlias(),
                        dtoLang.getLanguageCode(), editDto.getCustomerTypeId(), editDto.getMNewsTypeId());
                if (entityLanguage != null && !entityLanguage.getId().equals(dtoLang.getId())) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = dtoLang.getLinkAlias();
                    errors.rejectValue("categoryLanguageList[" + i + "].linkAlias", "error.message.key.seo.existed",
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