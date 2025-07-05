/*******************************************************************************
* Class        I18nLocaleServiceImpl
* Created date 2021/01/06
* Lasted date  2021/01/06
* Author       NhanNV
* Change log   2021/01/06 01-00 NhanNV create a new
******************************************************************************/

package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonObjectMapperUtil;
import vn.com.unit.core.dto.I18nLocaleDto;
import vn.com.unit.core.entity.I18nLocale;
import vn.com.unit.core.entity.I18nLocaleDefault;
import vn.com.unit.core.repository.I18nLocaleRepository;
import vn.com.unit.core.service.I18nLocaleDefaultService;
import vn.com.unit.core.service.I18nLocaleService;

/**
 * I18nLocaleServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class I18nLocaleServiceImpl implements I18nLocaleService{

	@Autowired
	private I18nLocaleRepository i18nLocaleRepository;
	
	@Autowired
	private I18nLocaleDefaultService i18nLocaleDefaultService;

	// Object mapper
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public I18nLocaleDto getI18nLocaleDtoById(Long id){
		I18nLocaleDto i18nLocaleDto = new I18nLocaleDto();
		if (null != id){
			I18nLocale i18nLocale = i18nLocaleRepository.findOne(id);
			if (null != i18nLocale){
			i18nLocaleDto = objectMapper.convertValue(i18nLocale, I18nLocaleDto.class);
			}
		}
		return i18nLocaleDto;
	}

	@Override
	public boolean deleteById(Long id){
		boolean res = false;
		Long userId = 0L; // TODO Add user
		Date sysDate = CommonDateUtil.getSystemDateTime();
		if (null != id){
			I18nLocale i18nLocale = i18nLocaleRepository.findOne(id);
			if (null != i18nLocale){
				i18nLocale.setDeletedId(userId);
				i18nLocale.setDeletedDate(sysDate);
				i18nLocaleRepository.update(i18nLocale);
				res = true;
			}
		}
		return res;
	}

	@Override
	public I18nLocale saveI18nLocale(I18nLocale i18nLocale){
		Long userId = 0L; // TODO Add user
		Date sysDate = CommonDateUtil.getSystemDateTime();
		Long id = i18nLocale.getId();
		if (null != id){
			I18nLocale oldI18nLocale = i18nLocaleRepository.findOne(id);
			if (null != oldI18nLocale){
				i18nLocale.setCreatedId(oldI18nLocale.getCreatedId());
				i18nLocale.setCreatedDate(oldI18nLocale.getCreatedDate());
				i18nLocale.setUpdatedId(userId);
				i18nLocale.setUpdatedDate(sysDate);
				i18nLocaleRepository.update(i18nLocale);
			}
		} else {
			i18nLocale.setCreatedId(userId);
			i18nLocale.setCreatedDate(sysDate);
			i18nLocaleRepository.create(i18nLocale);
		}
		return i18nLocale;
	}

	@Override
	public I18nLocale saveI18nLocaleDto(I18nLocaleDto i18nLocaleDto){
		I18nLocale i18nLocale = objectMapper.convertValue(i18nLocaleDto, I18nLocale.class);
		return this.saveI18nLocale(i18nLocale);
	}

	@Override
	public List<I18nLocale> findByCompanyIdAndLocale(long companyId, String locale) {
		return i18nLocaleRepository.findByCompanyIdAndLocale(companyId, locale);
	}

	@Override
	public boolean cloneTranslation(long companyId, String newLocale) {
		boolean result = true;
		
		List<I18nLocaleDefault> i18nLocaleDefaults = i18nLocaleDefaultService.findByCompanyIdAndLocale(/*companyId*/ 0, /*locale*/ "EN");
		int i18nSize = i18nLocaleDefaults.size();
		if(i18nSize > 0) {
			List<I18nLocale> i18nLocales = CommonObjectMapperUtil.mapAll(i18nLocaleDefaults, I18nLocale.class);
			for(I18nLocale i18nLocale : i18nLocales) {
				i18nLocale.setId(null);
				i18nLocale.setCompanyId(companyId);
				i18nLocale.setLocale(newLocale.toUpperCase());
				
				//saving
				saveI18nLocale(i18nLocale);
			}
		}else {
			result = false;
		}
		
		return result;
	}

	@Override
	public List<I18nLocaleDto> findI18nLocaleDtoByCompanyIdAndLocale(long companyId, String locale) {
		return CommonObjectMapperUtil.mapAll(findByCompanyIdAndLocale(companyId, locale), I18nLocaleDto.class);
	}

}