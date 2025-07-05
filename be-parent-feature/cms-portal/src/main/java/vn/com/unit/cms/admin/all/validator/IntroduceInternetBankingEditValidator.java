/*******************************************************************************
 * Class        ：IntroduceInternetBankingEditValidator
 * Created date ：2017/08/23
 * Lasted date  ：2017/08/23
 * Author       ：hoangnp
 * Change log   ：2017/08/23：01-00 ：hoangnp create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.IntroduceInternetBankingEditDto;
import vn.com.unit.cms.admin.all.entity.IntroduceInternetBanking;
import vn.com.unit.cms.admin.all.service.IntroduceInternetBankingService;

/**
 * IntroduceInternetBankingEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author hoangnp
 */
@Component
public class IntroduceInternetBankingEditValidator implements Validator {
	
	@Autowired
	IntroduceInternetBankingService introduceInternetBankingService;

	@Override
	public boolean supports(Class<?> clazz) {
		return IntroduceInternetBankingEditDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		//Introduce Internet Banking Dto
		IntroduceInternetBankingEditDto dto = (IntroduceInternetBankingEditDto) target;
		
		//check IntroduceInternetBanking exists code
		checkExistsCode(dto, errors);
	}

	/**
	 * checkExistsCode
	 *
	 * @param dto
	 * @param errors
	 * @return boolean
	 * @author hoangnp
	 */
	private boolean checkExistsCode(IntroduceInternetBankingEditDto dto, Errors errors) {
		String code = dto.getCode();
		IntroduceInternetBanking entity = introduceInternetBankingService.findByCode(code);
		if(entity!=null){
			Long introduceInternetBankingId = dto.getId();
			Long idDb = entity.getId();
			
			if(introduceInternetBankingId == null || !introduceInternetBankingId.equals(idDb)){
				String[] errorArgs = new String[1];
				errorArgs[0] = code;
				errors.rejectValue("code", "message.error.introduce.internet.banking.code.existed", errorArgs, ConstantCore.EMPTY);
				return true;
			}
		}
		return false;
		
		
	}

}
