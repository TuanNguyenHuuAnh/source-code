package vn.com.unit.cms.admin.all.validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.core.module.emulate.dto.EmulateEditDto;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.MessageList;

@Component
public class EmulateValidator implements Validator {

    @Autowired
    private MessageSource msg;

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmulateEditDto editDto = (EmulateEditDto) target;

        MessageList messageList = editDto.getMessageList();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date todayFull = new Date();
        Date today = new Date();

        if (editDto.getStartDate().compareTo(today) < 0
                && !format.format(today).equals(format.format(editDto.getStartDate()))) {
            String[] errorArgs = new String[1];
            errorArgs[0] = format.format(editDto.getStartDate());
            errors.rejectValue("startDate", "emulate.error.start.date", errorArgs, ConstantCore.EMPTY);

            String error = msg.getMessage("emulate.error.start.date", null, new Locale(editDto.getLanguageCode()));
            messageList = CmsUtils.setErrorForValidate(error, messageList);
        }

        if (editDto.getEndDate().compareTo(editDto.getStartDate()) < 0
                && !format.format(today).equals(format.format(editDto.getEndDate()))) {
            String[] errorArgs = new String[1];
            errorArgs[0] = format.format(editDto.getEndDate());
            errors.rejectValue("endDate", "emulate.error.end.date", errorArgs, ConstantCore.EMPTY);

            String error = msg.getMessage("emulate.error.end.date", null, new Locale(editDto.getLanguageCode()));
            messageList = CmsUtils.setErrorForValidate(error, messageList);
        }

        SimpleDateFormat formatFull = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        if (editDto.getPostedDate().before(todayFull)) {
            String[] errorArgs = new String[1];
            errorArgs[0] = formatFull.format(editDto.getPostedDate());
            errors.rejectValue("postedDate", "emulate.error.posted.date", errorArgs, ConstantCore.EMPTY);

            String error = msg.getMessage("emulate.error.posted.date", null, new Locale(editDto.getLanguageCode()));
            messageList = CmsUtils.setErrorForValidate(error, messageList);
        }

        if (editDto.getExpirationDate() != null && editDto.getExpirationDate().before(editDto.getPostedDate())) {
            String[] errorArgs = new String[1];
            errorArgs[0] = formatFull.format(editDto.getExpirationDate());
            errors.rejectValue("expirationDate", "emulate.error.expiration.date", errorArgs, ConstantCore.EMPTY);

            String error = msg.getMessage("emulate.error.expiration.date", null, new Locale(editDto.getLanguageCode()));
            messageList = CmsUtils.setErrorForValidate(error, messageList);
        }

        if (messageList != null) {
            editDto.setMessageList(messageList);
        }
    }
}
