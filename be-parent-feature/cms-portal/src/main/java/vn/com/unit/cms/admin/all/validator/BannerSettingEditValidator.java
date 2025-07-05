package vn.com.unit.cms.admin.all.validator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.com.unit.cms.admin.all.service.BannerSettingService;
import vn.com.unit.cms.core.module.banner.dto.setting.BannerSettingEditDto;
import vn.com.unit.cms.core.module.banner.entity.HomePageSetting;
import vn.com.unit.ep2p.admin.constant.ConstantCore;

@Component
public class BannerSettingEditValidator implements Validator {

	@Autowired
	private BannerSettingService bannerSettingService;

	@Override
	public boolean supports(Class<?> clazz) {
		return BannerSettingEditValidator.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		BannerSettingEditDto dto = (BannerSettingEditDto) target;

		checkExistsDate(dto, errors);

	}

	private void checkExistsDate(BannerSettingEditDto dto, Errors errors) {
		SimpleDateFormat formatFull = new SimpleDateFormat("dd/MM/yyyy HH");
		try {
			Long id = dto.getId();
			Date startDate = dto.getStartDate();
			// Date endDate = dto.getEndDate();
			String bannerPage = dto.getBannerPageName();
			String bannerType = dto.getBannerType();
			HomePageSetting entity = bannerSettingService.findDateInto(bannerPage, bannerType, id, dto.getChannel());

			if (entity.getEndDate() != null) {
				if (entity.getEndDate().getTime() > startDate.getTime()) {
					errors.rejectValue("date", "error.message.banner.date", ConstantCore.EMPTY);
				}
			} else {
				errors.rejectValue("date", "error.message.banner.end.date", ConstantCore.EMPTY);
			}

		} catch (Exception e) {

		}
	}

}
