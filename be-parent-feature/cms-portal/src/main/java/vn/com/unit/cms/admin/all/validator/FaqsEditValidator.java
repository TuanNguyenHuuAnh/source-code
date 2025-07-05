/*******************************************************************************
 * Class        ：FaqsEditValidator
 * Created date ：2016/06/01
 * Lasted date  ：2016/06/01
 * Author       ：TaiTM
 * Change log   ：2017/03/01：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.service.FaqsCategoryService;
import vn.com.unit.cms.admin.all.service.FaqsService;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.module.faqs.dto.FaqsEditDto;
import vn.com.unit.cms.core.module.faqs.entity.Faqs;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.ep2p.admin.constant.MessageList;

/**
 * FaqsEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Component
public class FaqsEditValidator implements Validator {

    @Autowired
    FaqsService faqsService;

    @Autowired
    FaqsCategoryService faqsCategoryService;

    @Autowired
    private MessageSource msg;

    @Override
    public boolean supports(Class<?> clazz) {
        return FaqsEditDto.class.equals(clazz);
    }
	private static final Logger logger = LoggerFactory.getLogger(FaqsEditValidator.class);

    @Override
    public void validate(Object target, Errors errors) {

        // FaqsEditDto
        FaqsEditDto editDto = (FaqsEditDto) target;

        MessageList messageList = editDto.getMessageList();

        // check exists code
        messageList = checkExistsCode(editDto, messageList, errors);

        Date todayFull = new Date();
        SimpleDateFormat formatFull = new SimpleDateFormat("dd/MM/yyyy HH");
        try {
			Date today = (Date)formatFull.parse(new Date().toString());
	        if (editDto.getPostedDate().getTime() <= today.getTime()) {
	        	
	            String[] errorArgs = new String[1];
	            errorArgs[0] = formatFull.format(editDto.getPostedDate());
	            errors.rejectValue("postedDate", "messsag.error.posted.date", errorArgs, ConstantCore.EMPTY);

	            String error = msg.getMessage("messsag.error.posted.date", null, new Locale(editDto.getLanguageCode()));
	            messageList = CmsUtils.setErrorForValidate(error, messageList);
	        }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("Exception ", e);
		} 

        if (editDto.getExpirationDate() != null && editDto.getExpirationDate().before(editDto.getPostedDate())) {
            String[] errorArgs = new String[1];
            errorArgs[0] = formatFull.format(editDto.getExpirationDate());
            errors.rejectValue("expirationDate", "message.error.expiration.date", errorArgs, ConstantCore.EMPTY);

            String error = msg.getMessage("message.error.expiration.date", null, new Locale(editDto.getLanguageCode()));
            messageList = CmsUtils.setErrorForValidate(error, messageList);
        }

        if (messageList != null) {
            editDto.setMessageList(messageList);
        }
    }

    /**
     * check exists code
     *
     * @param dto    FaqsEditDto
     * @param errors
     * @author TaiTM
     */
    private MessageList checkExistsCode(FaqsEditDto editDto, MessageList messageList, Errors errors) {
        String code = editDto.getCode();

        Faqs entity = faqsService.findFaqsByCode(code);
        if (entity != null) {
            Long faqsId = editDto.getId();
            Long idDb = entity.getId();

            if (faqsId == null || !faqsId.equals(idDb)) {
                String[] errorArgs = new String[1];
                errorArgs[0] = code;
                errors.rejectValue("code", "message.error.faqs.code.existed", errorArgs, ConstantCore.EMPTY);

                String error = msg.getMessage("message.error.faqs.code.existed", null,
                        new Locale(editDto.getLanguageCode()));
                messageList = CmsUtils.setErrorForValidate(error, messageList);
            }
        }

        return messageList;
    }
}