/*******************************************************************************
 * Class        ：NewsTypeEditValidator
 * Created date ：2016/06/01
 * Lasted date  ：2016/06/01
 * Author       ：hand
 * Change log   ：2017/03/01：01-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.cms.admin.all.dto.NewsTypeEditDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeLanguageDto;
//import vn.com.unit.cms.admin.all.entity.CustomerType;
import vn.com.unit.cms.admin.all.entity.NewsType;
import vn.com.unit.cms.admin.all.entity.NewsTypeLanguage;
//import vn.com.unit.cms.admin.all.service.CustomerTypeService;
import vn.com.unit.cms.admin.all.service.NewsTypeService;

/**
 * NewsTypeEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Component
public class NewsTypeEditValidator implements Validator {

	/** newsTypeService */
	@Autowired
	NewsTypeService newsTypeService;

//	@Autowired
//	private CustomerTypeService productTypeService;

	@Override
	public boolean supports(Class<?> clazz) {
		return NewsTypeEditDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		// NewsTypeEditDto
		NewsTypeEditDto dto = (NewsTypeEditDto) target;

		// check exists code of NewsType
		if (this.checkExistsCode(dto, errors)) {
			return;
		}

		// check exists alias of NewsType
		if (this.checkExistsAlias(dto, errors)) {
			return;
		}
		
	}

	/**
	 * check Exists CustomerType
	 *
	 * @param typeId
	 * @param errors
	 * @author hand
	 */
//	private boolean checkExistsCustomerType(Long typeId, Errors errors) {
//		if (null == typeId) {
//			return false;
//		}
//		CustomerType productType = productTypeService.findById(typeId);
//		if (null != productType.getDeleteDate()) {
//			String[] errorArgs = new String[1];
//			errorArgs[0] = productType.getName();
//			errors.rejectValue("mCustomerTypeId", "message.error.customer.type.not.exists", errorArgs,
//					ConstantCore.EMPTY);
//			return true;
//		}
//		return false;
//	}

	/**
	 * check exists code of NewsType
	 *
	 * @param dto
	 *            NewsTypeEditDto
	 * @param errors
	 * @author hand
	 */
	private boolean checkExistsCode(NewsTypeEditDto dto, Errors errors) {
		String code = dto.getCode();

		NewsType entity = newsTypeService.findByCode(code);
		if (entity != null) {
			Long newsId = dto.getId();
			Long idDb = entity.getId();

			// Check unique bankNo
			if (newsId == null || !newsId.equals(idDb)) {
				String[] errorArgs = new String[1];
				errorArgs[0] = code;
				errors.rejectValue("code", "message.error.news.type.code.existed", errorArgs, ConstantCore.EMPTY);
				return true;
			}
		}

		return false;
	}

	/**
	 * check exists alias
	 *
	 * @param dto
	 *            NewsTypeEditDto
	 * @param errors
	 * @author hand
	 */
    private boolean checkExistsAlias(NewsTypeEditDto dto, Errors errors) {
        int numberError = 0;
        if (CollectionUtils.isNotEmpty(dto.getTypeLanguageList())) {
            for (int i = 0, sz = dto.getTypeLanguageList().size(); i < sz; i++) {
                NewsTypeLanguageDto dtoLang = dto.getTypeLanguageList().get(i);

                NewsTypeLanguage entityLanguage = newsTypeService.findByAliasAndCustomerId(dtoLang.getLinkAlias(), dtoLang.getLanguageCode(), dto.getCustomerTypeId());
                if (entityLanguage != null && !entityLanguage.getId().equals(dtoLang.getId())) {
                    String[] errorArgs = new String[1];
                    errorArgs[0] = dtoLang.getLinkAlias();
                    errors.rejectValue("typeLanguageList[" + i + "].linkAlias", "error.message.key.seo.existed", errorArgs, ConstantCore.EMPTY);
                    numberError++;
                    
                    if (dto.getIndexLangActive() == null) {
                        dto.setIndexLangActive(i);
                    }
                }
            }
        }
        return numberError > 0;
    }
}