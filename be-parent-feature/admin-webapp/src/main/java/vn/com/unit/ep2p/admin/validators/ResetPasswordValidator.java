package vn.com.unit.ep2p.admin.validators;

import java.util.List;

import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.Db2AgentDto;
import vn.com.unit.ep2p.admin.service.ResetPasswordService;
import vn.com.unit.ep2p.dto.ResetPasswordDto;

@Component
public class ResetPasswordValidator implements Validator {

	@Autowired
	private ResetPasswordService resetPasswordService;

	@Override
	public boolean supports(Class<?> clazz) {
		return ResetPasswordDto.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {

		// check agent
		ResetPasswordDto dto = (ResetPasswordDto) target;
		if (StringUtils.isNotBlank(dto.getAgent())) {
			checkAgent(dto, errors);
		}
		if (StringUtils.equalsIgnoreCase(dto.getChooseLogin(), "2")) {
			checkPasswordGad(dto, errors);
		}

	}

	public void checkAgent(ResetPasswordDto dto, Errors errors) {
		String agent = dto.getAgent();

		Db2AgentDto exit = resetPasswordService.checkAgentExit(agent);
		// SR16656 @author lmi.quan
		//         @createdDate 2/7/2024
		//         Lỗi 2
		if (exit == null) {
			dto.setResult("agentAD");
			dto.setIsError("true");
			errors.rejectValue("agent", "message.check.agent", null, "");
		}
		// SR16656 @author lmi.quan
		//         @createdDate 2/7/2024
		//         Lỗi 2
		Db2AgentDto checkCham = resetPasswordService.checkAgent(agent);
		if (exit != null) {
			String status = String.valueOf(exit.getAgentStatusCode());
			if (!StringUtils.equalsIgnoreCase(status, "1")) {
				String[] errorArgs = new String[1];
				errorArgs[0] = agent;
				errors.rejectValue("agent", "message.check.agent", errorArgs, "");
				dto.setResult("agent");
				dto.setIsError("true");
			} else if ( checkCham != null && !StringUtils.equalsIgnoreCase(checkCham.getCount(), "0")) {
				String[] errorArgs = new String[1];
				errorArgs[0] = agent;
				errors.rejectValue("agent", "message.check.agent.exit", errorArgs, "");
				dto.setResult("agentExit");
				dto.setIsError("true");
			}
		}

	}

	public void checkPasswordGad(ResetPasswordDto dto, Errors errors) {
		String agent = dto.getAgent();
		String data = resetPasswordService.checkGad(dto.getAgent());

		boolean forceChangeGadPassword = resetPasswordService.checkAgentIsGad(dto.getAgent());
		if(forceChangeGadPassword) {
			if (StringUtils.isBlank(data)) {
				String[] errorArgs = new String[1];
				errorArgs[0] = agent;
				errors.rejectValue("agent", "message.check.agent", errorArgs, "");
				dto.setResult("passwordGad");
				dto.setIsError("true");

			}
		}else {
			String[] errorArgs = new String[1];
			errorArgs[0] = agent;
			errors.rejectValue("agent", "message.check.agent", errorArgs, "");
			dto.setResult("isGad");
			dto.setIsError("true");
		}
	

	}

}
