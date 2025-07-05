package vn.com.unit.ep2p.admin.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.QrtzMScheduleWebappService;
import vn.com.unit.quartz.job.dto.QrtzMScheduleDto;
import vn.com.unit.quartz.job.entity.QrtzMSchedule;

@Component
public class ScheduleValidator implements Validator {

	@Autowired
	QrtzMScheduleWebappService scheduleService;

	@Override
	public boolean supports(Class<?> clazz) {
		return QrtzMScheduleDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		QrtzMScheduleDto scheduleDto = (QrtzMScheduleDto) target;
		checkCronExpression(scheduleDto.getCronExpression(), errors);
		if (scheduleDto.getId() != null) {
			checkScheduleInUse(scheduleDto.getId(), scheduleDto.getSchedCode(), errors);
		}
	}

	private void checkCronExpression(String cron, Errors errors) {
		if (!scheduleService.cronCheck(cron)) {
			String[] errorArgs = new String[1];
			errorArgs[0] = cron;
			errors.rejectValue("cronExpression", "quartz.error.cron.format", errorArgs, ConstantCore.EMPTY);
		}
	}

	private void checkScheduleInUse(Long id, String schedCode,Errors errors) {

		QrtzMSchedule scheduleEntity = scheduleService.getById(id);
		//String schedCodeDB = scheduleEntity.getSchedCode();
		Long companyId = scheduleEntity.getCompanyId();
		if (scheduleService.isScheduleInUse(id, companyId)) {
			String[] errorArgs = new String[1];
			errorArgs[0] = schedCode;
			errors.reject("quartz.error.schedule.in.use", errorArgs, ConstantCore.EMPTY);
		}
	}

}
