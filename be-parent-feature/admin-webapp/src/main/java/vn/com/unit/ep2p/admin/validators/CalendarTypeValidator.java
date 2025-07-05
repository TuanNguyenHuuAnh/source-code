/*******************************************************************************
 * Class        :CalendarTypeValidator
 * Created date :2019/06/25
 * Lasted date  :2019/06/25
 * Author       :HungHT
 * Change log   :2019/06/25:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.sla.service.CalendarTypeAppService;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;

/**
 * CalendarTypeValidator
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Component
public class CalendarTypeValidator implements Validator {

    @Autowired
    CalendarTypeAppService calendarTypeAppService;

	@Override
    public boolean supports(Class<?> clazz) {
        return SlaCalendarTypeDto.class.equals(clazz);
    }

	@Override
    public void validate(Object target, Errors errors) {
	    SlaCalendarTypeDto destination = (SlaCalendarTypeDto) target;
	    SlaCalendarTypeDto source = calendarTypeAppService.findByCodeAndCompanyId(destination.getCode(), destination.getCompanyId());
        if (source != null) {
            if ((destination.getId() != null && source.getId().compareTo(destination.getId()) != 0) || null == destination.getId()) {
                String[] errorArgs = new String[1];
                errorArgs[0] = destination.getCode();
                errors.rejectValue("code", "message.error.calendar.type.code.existed", errorArgs, ConstantCore.EMPTY);
            }
        }
        
        try {
        	Double.parseDouble(destination.getWorkingHours());
		} catch (Exception e) {
			String[] errorArgs = new String[1];
            errorArgs[0] = destination.getWorkingHours();
			errors.rejectValue("workingHours", "message.error.calendar.type.working.hours.wrong", errorArgs, ConstantCore.EMPTY);
		}
        
        
	}
}