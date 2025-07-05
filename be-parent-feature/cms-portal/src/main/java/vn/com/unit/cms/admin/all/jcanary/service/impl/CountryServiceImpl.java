/*******************************************************************************
 * Class        CountryServiceImpl
 * Created date 2017/02/23
 * Lasted date  2017/02/23
 * Author       TranLTH
 * Change log   2017/02/2301-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.jcanary.dto.CountryDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CountryLanguageDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CountrySearchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
import vn.com.unit.cms.admin.all.jcanary.entity.Country;
import vn.com.unit.cms.admin.all.jcanary.entity.CountryLanguage;
import vn.com.unit.cms.admin.all.jcanary.enumdef.CountrySearchEnum;
import vn.com.unit.cms.admin.all.jcanary.repository.CountryLanguageRepository;
import vn.com.unit.cms.admin.all.jcanary.repository.CountryRepository;
import vn.com.unit.cms.admin.all.jcanary.service.CountryService;
import vn.com.unit.cms.admin.all.jcanary.service.CmsRegionService;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.exception.BusinessException;

//import vn.com.unit.exception.BusinessException;
//import vn.com.unit.exception.SystemException;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.dto.CountryDto;
//import vn.com.unit.jcanary.dto.CountryLanguageDto;
//import vn.com.unit.jcanary.dto.CountrySearchDto;
//import vn.com.unit.jcanary.dto.LanguageDto;
//import vn.com.unit.jcanary.dto.RegionDto;
//import vn.com.unit.jcanary.entity.Country;
//import vn.com.unit.jcanary.entity.CountryLanguage;
//import vn.com.unit.jcanary.enumdef.CountrySearchEnum;
//import vn.com.unit.jcanary.repository.CountryLanguageRepository;
//import vn.com.unit.jcanary.repository.CountryRepository;
//import vn.com.unit.jcanary.service.CountryService;
//import vn.com.unit.jcanary.service.LanguageService;
//import vn.com.unit.jcanary.service.ManualService;
//import vn.com.unit.jcanary.service.RegionService;

/**
 * CountryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private CountryLanguageRepository countryLanguageRepository;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private CmsRegionService regionService;

//	@Autowired
//	private ManualService manualService;

	@Override
	@Transactional
	public void addOrEditCountry(CountryDto countryDto) {
		String userNameLogin = UserProfileUtils.getUserNameLogin();
		Long countryId = countryDto.getCountryId();
		// update data jca_m_country table
		Country updateCountry = new Country();
		if (null != countryId) {
			updateCountry = countryRepository.findOne(countryId);
			if (null == updateCountry) {
				throw new BusinessException("Not found Country by id= " + countryId);
			}
			updateCountry.setUpdateBy(userNameLogin);
			updateCountry.setUpdateDate(new Date());
		} else {
			updateCountry.setCreateBy(userNameLogin);
			updateCountry.setCreateDate(new Date());
		}
		updateCountry.setCode(countryDto.getCountryCode().toUpperCase().trim());
		updateCountry.setDescription(countryDto.getDescription().trim());
		updateCountry.setLatitude(countryDto.getLatitude());
		updateCountry.setLongtitude(countryDto.getLongtitude());
		updateCountry.setNote(countryDto.getNote().trim());
		updateCountry.setPhoneCode(countryDto.getPhoneCode().trim());
		updateCountry.setWebCode(countryDto.getWebCode().trim());
		// Set enabled
		boolean activeFlag = countryDto.getActiveFlag();
		updateCountry.setActiveFlag(activeFlag ? "1" : "0");
		try {
			countryRepository.save(updateCountry);
			if (countryId == null) {
				countryId = updateCountry.getId();
			}
			countryDto.setCountryId(updateCountry.getId());
		} catch (Exception ex) {
			throw new SystemException(ex);
		}

		// update data jca_m_country_language table
		List<CountryLanguageDto> listCountryLanguageDto = countryDto.getCountryLanguageDtos();

		for (CountryLanguageDto countryLanguageDto : listCountryLanguageDto) {
			CountryLanguage countryLanguage = new CountryLanguage();
			if (null != countryLanguageDto.getmCountryId()) {
				countryLanguage = countryLanguageRepository.findOne(countryLanguageDto.getCountryLanguageId());
				if (null == countryLanguage) {
					throw new BusinessException("Not found Country Language with id=" + countryId);
				}
				countryLanguage.setUpdateDate(new Date());
				countryLanguage.setUpdateBy(userNameLogin);
			} else {
				countryLanguage.setCreateDate(new Date());
				countryLanguage.setCreateBy(userNameLogin);
			}
			countryLanguage.setCountryName(countryLanguageDto.getCountryName().trim());
			countryLanguage.setLocalName(countryLanguageDto.getLocalName().trim());
			countryLanguage.setmCountryId(countryId);
			countryLanguage.setmLanguageCode(countryLanguageDto.getmLanguageCode());

			try {
				countryLanguageRepository.save(countryLanguage);
			} catch (Exception ex) {
				throw new SystemException(ex);
			}
		}
	}

	@Override
	@Transactional
	public void deleteCountry(String countryId) {
		String usernameLogin = UserProfileUtils.getUserNameLogin();
		Country country = new Country();
		List<CountryLanguage> countryLanguages = new ArrayList<CountryLanguage>();
		List<RegionDto> listRegionDto = new ArrayList<RegionDto>();
		RegionDto regionSearchDto = new RegionDto();
		regionSearchDto.setmCountryId(Long.parseLong(countryId));
		if (!StringUtils.isEmpty(countryId)) {
			country = countryRepository.findOne(Long.parseLong(countryId));
			countryLanguages = countryRepository.findCountryIdLanguage(Long.parseLong(countryId));
			listRegionDto = regionService.findAllRegionListByCondition(regionSearchDto, "vi");
		}
		country.setDeleteDate(new Date());
		country.setDeleteBy(usernameLogin);
		try {
			countryRepository.save(country);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
		if (null != countryLanguages) {
			for (CountryLanguage countryLanguage : countryLanguages) {
				countryLanguage.setDeleteDate(new Date());
				countryLanguage.setDeleteBy(usernameLogin);
				try {
					countryLanguageRepository.save(countryLanguage);
				} catch (Exception ex) {
					throw new SystemException(ex);
				}
			}
		}
		if (null != listRegionDto) {
			for (RegionDto regionDto : listRegionDto) {
				regionService.deleteRegion(regionDto.getRegionId().toString());
			}
		}
	}

    private enum DBType {
        SQLSERVER, MYSQL, ORACLE;
    }

    public int countCountryByCondition(CountrySearchDto countrySearchDto) {
        int count = 0 ;
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            count = countryRepository.countCountryByCondition(countrySearchDto);
            break;
        case MYSQL:
            count = countryRepository.countCountryByCondition(countrySearchDto);
            break;
        case ORACLE:
            count =  countryRepository.countCountryByConditionForOracle(countrySearchDto);
            break;
        default:
            break;
        }
        return count;
    }

    public List<CountryDto> findCountryLimitByCondition(int offset,
            int sizeOfPage, CountrySearchDto countrySearchDto) {
        List<CountryDto> list = new ArrayList<CountryDto>();
        DBType dataType = DBType
                .valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            list = countryRepository.findCountryLimitByConditionSQLServer(
                    offset, sizeOfPage, countrySearchDto);
            break;
        case MYSQL:
            list = countryRepository.findCountryLimitByConditionMYSQL(offset,
                    sizeOfPage, countrySearchDto);
            break;
        case ORACLE:
            list = countryRepository.findCountryLimitByConditionOracle(offset,
                    sizeOfPage, countrySearchDto);
            break;    
        default:
            break;
        }
        return list;
    }

	@Override
	public PageWrapper<CountryDto> search(int page, CountrySearchDto countrySearchDto) {
		if (null == countrySearchDto)
			countrySearchDto = new CountrySearchDto();
		// set SearchParm
		setSearchParm(countrySearchDto);

		int sizeOfPage = countrySearchDto.getPageSize() != null ? countrySearchDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<CountryDto> pageWrapper = new PageWrapper<CountryDto>(page, sizeOfPage);

//		int count = manualService.countCountryByCondition(countrySearchDto);
		int count = countCountryByCondition(countrySearchDto);

		List<CountryDto> result = new ArrayList<CountryDto>();
		if (count > 0) {
			int currentPage = pageWrapper.getCurrentPage();
			int startIndex = (currentPage - 1) * sizeOfPage;

//			result = manualService.findCountryLimitByCondition(startIndex, sizeOfPage, countrySearchDto);
			result = findCountryLimitByCondition(startIndex, sizeOfPage, countrySearchDto);

		}

		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	@Override
	public CountryDto getCountryDto(Long countryId) {
		CountryDto countryDto = new CountryDto();
		List<CountryLanguageDto> countryLanguageDtos = new ArrayList<CountryLanguageDto>();
		if (null == countryId) {
			return countryDto;
		}
		countryDto.setCountryId(countryId);
		countryDto = countryRepository.findCountryDtoById(countryId);
		// Get Country language
		List<CountryLanguage> countryLanguages = countryRepository.findCountryIdLanguage(countryId);
		// Get language
		List<LanguageDto> languageList = languageService.getLanguageDtoList();
		for (LanguageDto languageDto : languageList) {
			for (CountryLanguage countryLanguage : countryLanguages) {
				CountryLanguageDto countryLanguageDto = new CountryLanguageDto();
				if (languageDto.getCode().equals(countryLanguage.getmLanguageCode())) {
					countryLanguageDto.setCountryLanguageId(countryLanguage.getId());
					countryLanguageDto.setCountryName(countryLanguage.getCountryName());
					countryLanguageDto.setLocalName(countryLanguage.getLocalName());
					countryLanguageDto.setmCountryId(countryLanguage.getmCountryId());
					countryLanguageDto.setmLanguageCode(countryLanguage.getmLanguageCode());
					countryLanguageDtos.add(countryLanguageDto);
				}
			}
		}
		countryDto.setCountryLanguageDtos(countryLanguageDtos);
		return countryDto;
	}

	@Override
	public void initScreenCountryList(ModelAndView mav) {
		// Init language
		try {
			List<LanguageDto> languageList = languageService.getLanguageDtoList();
			mav.addObject("languageList", languageList);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
	}

	@Override
	public List<CountryDto> findAllCountryListByCondition(CountryDto countryDto, String language) {
		return countryRepository.findAllCountryListByCondition(countryDto, language);
	}

	private void setSearchParm(CountrySearchDto countrySearchDto) {
		if (null == countrySearchDto.getFieldValues()) {
			countrySearchDto.setFieldValues(new ArrayList<String>());
		}

		if (countrySearchDto.getFieldValues().isEmpty()) {
			countrySearchDto
					.setCode(countrySearchDto.getFieldSearch() != null ? countrySearchDto.getFieldSearch().trim()
							: countrySearchDto.getFieldSearch());
			countrySearchDto
					.setName(countrySearchDto.getFieldSearch() != null ? countrySearchDto.getFieldSearch().trim()
							: countrySearchDto.getFieldSearch());
			countrySearchDto
					.setDescription(countrySearchDto.getFieldSearch() != null ? countrySearchDto.getFieldSearch().trim()
							: countrySearchDto.getFieldSearch());
			countrySearchDto
					.setWebCode(countrySearchDto.getFieldSearch() != null ? countrySearchDto.getFieldSearch().trim()
							: countrySearchDto.getFieldSearch());
			countrySearchDto
					.setPhoneCode(countrySearchDto.getFieldSearch() != null ? countrySearchDto.getFieldSearch().trim()
							: countrySearchDto.getFieldSearch());
		} else {
			for (String field : countrySearchDto.getFieldValues()) {
				if (StringUtils.equals(field, CountrySearchEnum.CODE.name())) {
					countrySearchDto.setCode(countrySearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, CountrySearchEnum.NAME.name())) {
					countrySearchDto.setName(countrySearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, CountrySearchEnum.DESCRIPTION.name())) {
					countrySearchDto.setDescription(countrySearchDto.getFieldSearch().trim());
					continue;
				}

				if (StringUtils.equals(field, CountrySearchEnum.PHONECODE.name())) {
					countrySearchDto.setPhoneCode(countrySearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, CountrySearchEnum.WEBCODE.name())) {
					countrySearchDto.setWebCode(countrySearchDto.getFieldSearch().trim());
					continue;
				}
			}
		}
	}
}
