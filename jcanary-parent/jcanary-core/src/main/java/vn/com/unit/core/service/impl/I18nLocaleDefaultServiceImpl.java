/*******************************************************************************
* Class        I18nLocaleDefaultServiceImpl
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
import vn.com.unit.core.dto.I18nLocaleDefaultDto;
import vn.com.unit.core.entity.I18nLocaleDefault;
import vn.com.unit.core.repository.I18nLocaleDefaultRepository;
import vn.com.unit.core.service.I18nLocaleDefaultService;

/**
 * I18nLocaleDefaultServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class I18nLocaleDefaultServiceImpl implements I18nLocaleDefaultService{

	@Autowired
	private I18nLocaleDefaultRepository i18nLocaleDefaultRepository;

	// Object mapper
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public I18nLocaleDefaultDto getI18nLocaleDefaultDtoById(Long id){
		I18nLocaleDefaultDto i18nLocaleDefaultDto = new I18nLocaleDefaultDto();
		if (null != id){
			I18nLocaleDefault i18nLocaleDefault = i18nLocaleDefaultRepository.findOne(id);
			if (null != i18nLocaleDefault){
			i18nLocaleDefaultDto = objectMapper.convertValue(i18nLocaleDefault, I18nLocaleDefaultDto.class);
			}
		}
		return i18nLocaleDefaultDto;
	}

	@Override
	public boolean deleteById(Long id){
		boolean res = false;
		Long userId = 0L; // TODO Add user
		Date sysDate = CommonDateUtil.getSystemDateTime();
		if (null != id){
			I18nLocaleDefault i18nLocaleDefault = i18nLocaleDefaultRepository.findOne(id);
			if (null != i18nLocaleDefault){
				i18nLocaleDefault.setDeletedId(userId);
				i18nLocaleDefault.setDeletedDate(sysDate);
				i18nLocaleDefaultRepository.update(i18nLocaleDefault);
				res = true;
			}
		}
		return res;
	}

	@Override
	public I18nLocaleDefault saveI18nLocaleDefault(I18nLocaleDefault i18nLocaleDefault){
		Long userId = 0L; // TODO Add user
		Date sysDate = CommonDateUtil.getSystemDateTime();
		Long id = i18nLocaleDefault.getId();
		if (null != id){
			I18nLocaleDefault oldI18nLocaleDefault = i18nLocaleDefaultRepository.findOne(id);
			if (null != oldI18nLocaleDefault){
				i18nLocaleDefault.setCreatedId(oldI18nLocaleDefault.getCreatedId());
				i18nLocaleDefault.setCreatedDate(oldI18nLocaleDefault.getCreatedDate());
				i18nLocaleDefault.setUpdatedId(userId);
				i18nLocaleDefault.setUpdatedDate(sysDate);
				i18nLocaleDefaultRepository.update(i18nLocaleDefault);
			}
		} else {
			i18nLocaleDefault.setCreatedId(userId);
			i18nLocaleDefault.setCreatedDate(sysDate);
			i18nLocaleDefaultRepository.create(i18nLocaleDefault);
		}
		return i18nLocaleDefault;
	}

	@Override
	public I18nLocaleDefault saveI18nLocaleDefaultDto(I18nLocaleDefaultDto i18nLocaleDefaultDto){
		I18nLocaleDefault i18nLocaleDefault = objectMapper.convertValue(i18nLocaleDefaultDto, I18nLocaleDefault.class);
		return this.saveI18nLocaleDefault(i18nLocaleDefault);
	}
	
	@Override
	public List<I18nLocaleDefault> findByCompanyIdAndLocale(long companyId, String locale) {
		return i18nLocaleDefaultRepository.findByCompanyIdAndLocale(companyId, locale);
	}

}