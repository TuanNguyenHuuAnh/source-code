/*******************************************************************************
 * Class        RegionServiceImpl
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       TranLTH
 * Change log   2017/02/1401-00 TranLTH create a new
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

import vn.com.unit.cms.admin.all.jcanary.dto.CityDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CountryDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionLanguageDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionSearchDto;
import vn.com.unit.cms.admin.all.jcanary.entity.Region;
import vn.com.unit.cms.admin.all.jcanary.entity.RegionLanguage;
import vn.com.unit.cms.admin.all.jcanary.enumdef.RegionSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.repository.RegionLanguageRepository;
import vn.com.unit.cms.admin.all.jcanary.repository.CmsRegionRepository;
import vn.com.unit.cms.admin.all.jcanary.service.CityService;
import vn.com.unit.cms.admin.all.jcanary.service.CountryService;
import vn.com.unit.cms.admin.all.jcanary.service.CmsRegionService;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
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
//import vn.com.unit.jcanary.dto.CityDto;
//import vn.com.unit.jcanary.dto.CountryDto;
//import vn.com.unit.jcanary.dto.LanguageDto;
//import vn.com.unit.jcanary.dto.RegionDto;
//import vn.com.unit.jcanary.dto.RegionLanguageDto;
//import vn.com.unit.jcanary.dto.RegionSearchDto;
//import vn.com.unit.jcanary.dto.Select2Dto;
//import vn.com.unit.jcanary.entity.Region;
//import vn.com.unit.jcanary.entity.RegionLanguage;
//import vn.com.unit.jcanary.enumdef.RegionSearchEnum;
//import vn.com.unit.jcanary.repository.RegionLanguageRepository;
//import vn.com.unit.jcanary.repository.RegionRepository;
//import vn.com.unit.jcanary.service.CityService;
//import vn.com.unit.jcanary.service.CountryService;
//import vn.com.unit.jcanary.service.LanguageService;
//import vn.com.unit.jcanary.service.ManualService;
//import vn.com.unit.jcanary.service.RegionService;

/**
 * RegionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tranlth
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsRegionServiceImpl implements CmsRegionService {

	@Autowired
	private CmsRegionRepository regionRepository;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private RegionLanguageRepository regionLanguageRepository;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private CityService cityService;

//    @Autowired
//    ManualService manualService;

	@Override
	@Transactional
	public void addOrEditRegion(RegionDto regionDto) {
		String userNameLogin = UserProfileUtils.getUserNameLogin();
		Long regionId = regionDto.getRegionId();
		// update data jca_m_region table
		Region updateRegion = new Region();
		if (null != regionId) {
			updateRegion = regionRepository.findOne(regionId);
			if (null == updateRegion) {
				throw new BusinessException("Not found Region by id= " + regionId);
			}
			updateRegion.setUpdateBy(userNameLogin);
			updateRegion.setUpdateDate(new Date());
		} else {
			updateRegion.setCreateBy(userNameLogin);
			updateRegion.setCreateDate(new Date());
		}
		updateRegion.setCode(regionDto.getRegionCode().toUpperCase());
		updateRegion.setDescription(regionDto.getDescription());
		updateRegion.setmCountryId(regionDto.getmCountryId());
		updateRegion.setNote(regionDto.getNote());

		// Set enabled
		boolean activeFlag = regionDto.getActiveFlag();
		updateRegion.setActiveFlag(activeFlag ? "1" : "0");

		try {
			regionRepository.save(updateRegion);
			if (regionId == null) {
				regionId = updateRegion.getId();
			}

			regionDto.setRegionId(updateRegion.getId());
		} catch (Exception ex) {
			throw new SystemException(ex);
		}

		// update data jca_m_region_language table
		List<RegionLanguageDto> listRegionLanguageDto = regionDto.getRegionLanguageDtos();

		for (RegionLanguageDto regionLanguageDto : listRegionLanguageDto) {
			RegionLanguage regionLanguage = new RegionLanguage();
			if (null != regionLanguageDto.getmRegionId()) {
				regionLanguage = regionLanguageRepository.findOne(regionLanguageDto.getRegionLanguageId());
				if (null == regionLanguage) {
					throw new BusinessException("Not found Region Language with id=" + regionId);
				}
				regionLanguage.setUpdateDate(new Date());
				regionLanguage.setUpdateBy(userNameLogin);
			} else {
				regionLanguage.setCreateDate(new Date());
				regionLanguage.setCreateBy(userNameLogin);
			}
			regionLanguage.setmLanguageCode(regionLanguageDto.getmLanguageCode());
			regionLanguage.setmRegionId(regionId);
			regionLanguage.setName(regionLanguageDto.getRegionName());

			try {
				regionLanguageRepository.save(regionLanguage);
			} catch (Exception ex) {
				throw new SystemException(ex);
			}
		}
	}

	@Override
	@Transactional
	public void deleteRegion(String regionId) {
		String usernameLogin = UserProfileUtils.getUserNameLogin();
		Region region = new Region();
		List<RegionLanguage> regionLanguages = new ArrayList<RegionLanguage>();
		List<CityDto> listCityDto = new ArrayList<CityDto>();
		CityDto citySearchDto = new CityDto();
		citySearchDto.setmRegionId(Long.parseLong(regionId));
		if (!StringUtils.isEmpty(regionId)) {
			region = regionRepository.findOne(Long.parseLong(regionId));
			regionLanguages = regionRepository.findRegionIdLanguage(Long.parseLong(regionId));
			listCityDto = cityService.findAllCityListByCondition(citySearchDto, "vi");
		}
		region.setDeleteDate(new Date());
		region.setDeleteBy(usernameLogin);
		try {
			regionRepository.save(region);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
		if (null != regionLanguages) {
			for (RegionLanguage regionLanguage : regionLanguages) {
				regionLanguage.setDeleteDate(new Date());
				regionLanguage.setDeleteBy(usernameLogin);
				try {
					regionLanguageRepository.save(regionLanguage);
				} catch (Exception ex) {
					throw new SystemException(ex);
				}
			}
		}
		if (null != listCityDto) {
			for (CityDto cityDto : listCityDto) {
				cityService.deleteCity(cityDto.getCityId().toString());
			}
		}
	}

	private enum DBType {
		SQLSERVER, MYSQL, ORACLE;
	}

	public int countRegionByCondition(RegionSearchDto regionSearchDto) {
		int count = 0;
		DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
		switch (dataType) {
		case SQLSERVER:
			count = regionRepository.countRegionByCondition(regionSearchDto);
			break;
		case MYSQL:
			count = regionRepository.countRegionByCondition(regionSearchDto);
			break;
		case ORACLE:
			count = regionRepository.countRegionByConditionForOracle(regionSearchDto);
			break;
		default:
			break;
		}
		return count;
	}

	public List<RegionDto> findRegionLimitByCondition(int offset, int sizeOfPage, RegionSearchDto regionSearchDto) {
		List<RegionDto> list = new ArrayList<RegionDto>();
		DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
		switch (dataType) {
		case SQLSERVER:
			list = regionRepository.findRegionLimitByConditionSQLServer(offset, sizeOfPage, regionSearchDto);
			break;
		case MYSQL:
			list = regionRepository.findRegionLimitByConditionMYSQL(offset, sizeOfPage, regionSearchDto);
			break;
		case ORACLE:
			list = regionRepository.findRegionLimitByConditionOracle(offset, sizeOfPage, regionSearchDto);
			break;
		default:
			break;
		}
		return list;
	}

	@Override
	@Transactional
	public PageWrapper<RegionDto> search(int page, RegionSearchDto regionSearchDto) {
		if (null == regionSearchDto)
			regionSearchDto = new RegionSearchDto();
		// set SearchParm
		setSearchParm(regionSearchDto);

		int sizeOfPage = regionSearchDto.getPageSize() != null ? regionSearchDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<RegionDto> pageWrapper = new PageWrapper<RegionDto>(page, sizeOfPage);

//        int count = manualService.countRegionByCondition(regionSearchDto);
		int count = countRegionByCondition(regionSearchDto);

		List<RegionDto> result = new ArrayList<RegionDto>();
		if (count > 0) {
			int currentPage = pageWrapper.getCurrentPage();
			int startIndex = (currentPage - 1) * sizeOfPage;

//			result = manualService.findRegionLimitByCondition(startIndex, sizeOfPage, regionSearchDto);
			result = findRegionLimitByCondition(startIndex, sizeOfPage, regionSearchDto);

		}

		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	@Override
	public RegionDto getRegionDto(Long regionId) {
		RegionDto regionDto = new RegionDto();
		List<RegionLanguageDto> regionLanguageDtos = new ArrayList<RegionLanguageDto>();
		if (null == regionId) {
			return regionDto;
		}
		List<LanguageDto> languageList = languageService.getLanguageDtoList();

		regionDto.setRegionId(regionId);
		regionDto = regionRepository.findRegionDtoById(regionId);

		List<RegionLanguage> regionLanguages = regionRepository.findRegionIdLanguage(regionId);
		for (LanguageDto languageDto : languageList) {
			for (RegionLanguage regionLanguage : regionLanguages) {
				RegionLanguageDto regionLanguageDto = new RegionLanguageDto();
				if (languageDto.getCode().equals(regionLanguage.getmLanguageCode())) {
					regionLanguageDto.setmLanguageCode(regionLanguage.getmLanguageCode());
					regionLanguageDto.setmRegionId(regionLanguage.getmRegionId());
					regionLanguageDto.setRegionLanguageId(regionLanguage.getId());
					regionLanguageDto.setRegionName(regionLanguage.getName());
					regionLanguageDtos.add(regionLanguageDto);
				}
			}
		}

		regionDto.setRegionLanguageDtos(regionLanguageDtos);
		return regionDto;
	}

	@Override
	public void initScreenRegionList(ModelAndView mav, String language) {
		if (!language.isEmpty()) {
			language = language.toUpperCase();
		}
		// Init language
		try {
			List<LanguageDto> languageList = languageService.getLanguageDtoList();
			mav.addObject("languageList", languageList);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
		// Init country
		try {
			CountryDto countryDto = new CountryDto();
			List<CountryDto> listCountryDto = countryService.findAllCountryListByCondition(countryDto, language);
			mav.addObject("listCountryDto", listCountryDto);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
	}

	@Override
	public List<RegionDto> findAllRegionListByCondition(RegionDto regionDto, String language) {
		return regionRepository.findAllRegionListByCondition(regionDto.getmCountryId(), language);
	}

	private void setSearchParm(RegionSearchDto regionSearchDto) {
		if (null == regionSearchDto.getFieldValues()) {
			regionSearchDto.setFieldValues(new ArrayList<String>());
		}

		if (regionSearchDto.getFieldValues().isEmpty()) {
			regionSearchDto.setCode(regionSearchDto.getFieldSearch() != null ? regionSearchDto.getFieldSearch().trim()
					: regionSearchDto.getFieldSearch());
			regionSearchDto.setName(regionSearchDto.getFieldSearch() != null ? regionSearchDto.getFieldSearch().trim()
					: regionSearchDto.getFieldSearch());
			regionSearchDto.setNote(regionSearchDto.getFieldSearch() != null ? regionSearchDto.getFieldSearch().trim()
					: regionSearchDto.getFieldSearch());
			regionSearchDto
					.setCountry(regionSearchDto.getFieldSearch() != null ? regionSearchDto.getFieldSearch().trim()
							: regionSearchDto.getFieldSearch());
			regionSearchDto
					.setDescription(regionSearchDto.getFieldSearch() != null ? regionSearchDto.getFieldSearch().trim()
							: regionSearchDto.getFieldSearch());
		} else {
			for (String field : regionSearchDto.getFieldValues()) {
				if (StringUtils.equals(field, RegionSearchEnum.CODE.name())) {
					regionSearchDto.setCode(regionSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, RegionSearchEnum.NAME.name())) {
					regionSearchDto.setName(regionSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, RegionSearchEnum.DESCRIPTION.name())) {
					regionSearchDto.setDescription(regionSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, RegionSearchEnum.COUNTRY.name())) {
					regionSearchDto.setCountry(regionSearchDto.getFieldSearch().trim());
					continue;
				}
			}
		}
	}

	@Override
	public List<Select2Dto> findAllRegionCountry(String language) {
		return regionRepository.findAllRegionCountry(language);
	}

}
