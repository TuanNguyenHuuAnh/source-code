/*******************************************************************************
 * Class        ：NewsEditValidator
 * Created date ：2016/06/01
 * Lasted date  ：2016/06/01
 * Author       ：TaiTM
 * Change log   ：2017/03/01：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.validator;

import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.dto.NewsEditDto;
import vn.com.unit.cms.admin.all.dto.NewsLanguageDto;
import vn.com.unit.cms.admin.all.service.NewsService;
import vn.com.unit.cms.core.module.news.dto.NewsSearchResultDto;
import vn.com.unit.cms.core.module.news.entity.News;
import vn.com.unit.cms.core.module.news.entity.NewsLanguage;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.MessageList;

/**
 * NewsEditValidator
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Component
public class NewsEditValidator implements Validator {

	@Autowired
	private NewsService newsService;

	@Autowired
	private MessageSource msg;

	@Override
	public boolean supports(Class<?> clazz) {
		return NewsEditDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// NewsEditDto
		NewsEditDto dto = (NewsEditDto) target;

		// check News exists code
		if (checkExistsCode(dto, errors)) {
			return;
		}

		// check exists alias
		if (checkExistsAlias(dto, errors)) {
			return;
		}

		if (dto.getExpirationDate() != null) {
			if (dto.getExpirationDate().before(dto.getPostedDate())) {
				errors.rejectValue("expirationDate", "message.error.expiration.date", null, ConstantCore.EMPTY);
				return;
			}
		}
		// check hot
		checkExistsHot(dto, errors);
	}

	/**
	 * check News exists code
	 *
	 * @param dto    NewsEditDto
	 * @param errors
	 * @author TaiTM
	 */
	private boolean checkExistsCode(NewsEditDto editDto, Errors errors) {
		String code = editDto.getCode();
		MessageList messageList = editDto.getMessageList();

		News entity = newsService.findByCode(code);
		if (entity != null) {
			Long newsId = editDto.getId();
			Long idDb = entity.getId();

			// Check unique bankNo
			if (newsId == null || !newsId.equals(idDb)) {
				String[] errorArgs = new String[1];
				errorArgs[0] = code;
				errors.rejectValue("code", "message.error.news.code.existed", errorArgs, ConstantCore.EMPTY);

				String error = msg.getMessage("message.error.news.code.existed", errorArgs,
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
	 *
	 * @param dto
	 * @param errors
	 */
	private boolean checkExistsAlias(NewsEditDto editDto, Errors errors) {
		int numberError = 0;
		MessageList messageList = editDto.getMessageList();

		if (CollectionUtils.isNotEmpty(editDto.getNewsLanguageList())) {
			for (int i = 0, sz = editDto.getNewsLanguageList().size(); i < sz; i++) {
				NewsLanguageDto dtoLang = editDto.getNewsLanguageList().get(i);

				NewsLanguage entityLanguage = newsService.findLangByLinkAlias(dtoLang.getLinkAlias(),
						dtoLang.getLanguageCode(), editDto.getCustTypeId(), editDto.getCategoryId(),
						editDto.getTypeId());
				if (entityLanguage != null && !entityLanguage.getId().equals(dtoLang.getId())) {
					String[] errorArgs = new String[1];
					errorArgs[0] = dtoLang.getLinkAlias();
					errors.rejectValue("newsLanguageList[" + i + "].linkAlias", "error.message.key.seo.existed",
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

	/**
	 * check exists hot
	 *
	 * @param dto
	 * @param errors
	 */
	private void checkExistsHot(NewsEditDto editDto, Errors errors) {

		Long categoryId = editDto.getCategoryId();
		Long typeId = editDto.getTypeId();
		Long newsId = editDto.getId();
		boolean hot = editDto.isHot();

		Integer entity = newsService.getCategoryNameCheck(categoryId, typeId, newsId);
			if (entity != 0 && hot == true) {
				errors.rejectValue("hot", "error.message.banner.hot", ConstantCore.EMPTY);
			}


	}
}