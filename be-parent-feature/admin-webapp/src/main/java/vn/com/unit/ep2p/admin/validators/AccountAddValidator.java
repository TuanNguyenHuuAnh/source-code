/*******************************************************************************
 * Class        AccountAddValidator
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.validators;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.JcaAccountAddDto;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.admin.service.BeAdminFileService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.core.utils.FileReaderUtils;

/**
 * AccountAddValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Component
public class AccountAddValidator implements Validator {

	/** AccountService */
	@Autowired
	AccountService accService;

	@Autowired
	private JRepositoryService jRepositoryService;

	@Autowired
	private BeAdminFileService fileService;

	@Override
	public boolean supports(Class<?> clazz) {
		return JcaAccountAddDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		JcaAccountAddDto accModel = (JcaAccountAddDto) target;

		String username = accModel.getUsername();
		String email = accModel.getEmail();
		String code = accModel.getCode();

		// check dup code
		if (StringUtils.isNotBlank(code)) {
			JcaAccount accCheckCode = accService.findByCode(code, accModel.getCompanyId());
			if (accCheckCode != null) {
				String[] errorArgs = new String[1];
				errorArgs[0] = code;
				errors.rejectValue("code", "message.error.account.code.existed", errorArgs, ConstantCore.EMPTY);
			}
		}

		JcaAccount accDB = accService.findByUserName(username, accModel.getCompanyId());
		if (accDB != null) {
			String[] errorArgs = new String[1];
			errorArgs[0] = username;
			errors.rejectValue("username", "message.error.account.username.existed", errorArgs, ConstantCore.EMPTY);
		}

		// check dup email
		if (StringUtils.endsWithIgnoreCase(accModel.getAccountType(), "LDAP")) {
			JcaAccount accCheckEmailDto = accService.findByEmailDto(email);
			if (accCheckEmailDto != null) {
				String[] errorArgs = new String[1];
				errorArgs[0] = email;
				errors.rejectValue("email", "message.error.account.email.existed", errorArgs, ConstantCore.EMPTY);
			}
		}
		else {
			JcaAccount accCheckEmail = accService.findByEmail(email);
			if (accCheckEmail != null) {
				String[] errorArgs = new String[1];
				errorArgs[0] = email;
				errors.rejectValue("email", "message.error.account.email.existed", errorArgs, ConstantCore.EMPTY);

			}
		}

		// check Image
		if (accModel.getAvatarFile() != null) {
			try {
				String fileName = fileService.uploadTemp(accModel.getAvatarFile(), "ACCOUNT", "IMG");

				String tempPath = jRepositoryService.getPathByRepository(SystemConfig.REPO_UPLOADED_TEMP);
				String path = Paths.get(tempPath, "ACCOUNT").toString() + "//" + fileName;
				boolean img = FileReaderUtils.isImgFile(path);
				if (!img) {
					String[] errorArgs = new String[1];
					errorArgs[0] = email;
					errors.rejectValue("avatar", "message.error.account.img", errorArgs, ConstantCore.EMPTY);
				}
			} catch (IOException e) {
				String[] errorArgs = new String[1];
				errorArgs[0] = email;
				errors.rejectValue("avatar", "message.error.account.img", errorArgs, ConstantCore.EMPTY);
			}
		}
	}
}