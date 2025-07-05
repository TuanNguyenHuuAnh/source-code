package vn.com.unit.process.admin.validators;

//import java.util.ArrayList;
//import java.util.List;

//import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//import vn.com.unit.ep2p.admin.constant.ConstantCore;
//import vn.com.unit.ep2p.admin.sla.dto.SlaSettingDto;
import vn.com.unit.ep2p.admin.sla.dto.SlaStepDto;

@Component
public class SlaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SlaStepDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

//		SlaStepDto objectDto = (SlaStepDto) target;
//		List<SlaSettingDto> reminders = objectDto.getReminderList();
//		List<SlaSettingDto> escalates = objectDto.getEscalateList();
//		Long time = objectDto.getSlaDueTime();

//		if ((reminders != null && !CollectionUtils.isEmpty(reminders)) || (escalates != null && !CollectionUtils.isEmpty(escalates))) {
//			String type = objectDto.getSlaTimeType();
//			if(time == null) {
//				errors.rejectValue("workTime", "NotNull", null, ConstantCore.EMPTY);
//			} else if(time == 0) {
//				List<String> values = new ArrayList<>();
//				String value = Integer.toString(ConstantCore.NUMBER_ZERO);
//				values.add(value);
//				Object[] result = values.toArray();
//				errors.rejectValue("workTime", "data.validate.min", result, ConstantCore.EMPTY);
//			}
//			if(StringUtils.isEmpty(type)) {
//				errors.rejectValue("timeType", "NotNull", null, ConstantCore.EMPTY);
//			}
//		}
	}

}
