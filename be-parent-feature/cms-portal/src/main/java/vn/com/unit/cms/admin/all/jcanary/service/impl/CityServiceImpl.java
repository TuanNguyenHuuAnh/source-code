/*******************************************************************************
 * Class        CityServiceImpl
 * Created date 2017/02/17
 * Lasted date  2017/02/17
 * Author       TranLTH
 * Change log   2017/02/1701-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.service.impl;

import java.util.ArrayList;
import java.util.Date;
//import java.util.Arrays;
//import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.jcanary.dto.CityDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CityLanguageDto;
import vn.com.unit.cms.admin.all.jcanary.dto.CitySearchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.DistrictDto;
import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
import vn.com.unit.cms.admin.all.jcanary.entity.City;
import vn.com.unit.cms.admin.all.jcanary.entity.CityLanguage;
import vn.com.unit.cms.admin.all.jcanary.repository.CityLanguageRepository;
//import vn.com.unit.cms.admin.all.jcanary.dto.CityLanguageDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.CitySearchDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.DistrictDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.RegionDto;
//import vn.com.unit.cms.admin.all.jcanary.entity.City;
//import vn.com.unit.cms.admin.all.jcanary.entity.CityLanguage;
//import vn.com.unit.cms.admin.all.jcanary.repository.CityLanguageRepository;
import vn.com.unit.cms.admin.all.jcanary.repository.CityRepository;
import vn.com.unit.cms.admin.all.jcanary.service.CityService;
import vn.com.unit.cms.admin.all.jcanary.service.DistrictService;
import vn.com.unit.cms.admin.all.jcanary.service.CmsRegionService;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.LanguageService;
//import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.common.dto.Select2Dto;
//import vn.com.unit.common.exception.SystemException;
//import vn.com.unit.core.dto.LanguageDto;
//import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.core.service.JcaConstantService;
//import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.enumdef.CitySearchEnum;
//import vn.com.unit.ep2p.admin.enumdef.CitySearchEnum;
import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
//import vn.com.unit.ep2p.admin.service.ManualService;
//import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.exception.BusinessException;
//import vn.com.unit.exception.SystemException;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.dto.CityDto;
//import vn.com.unit.jcanary.dto.CityLanguageDto;
//import vn.com.unit.jcanary.dto.CitySearchDto;
//import vn.com.unit.jcanary.dto.DistrictDto;
//import vn.com.unit.jcanary.dto.LanguageDto;
//import vn.com.unit.jcanary.dto.RegionDto;
//import vn.com.unit.jcanary.dto.Select2Dto;
//import vn.com.unit.jcanary.entity.City;
//import vn.com.unit.jcanary.entity.CityLanguage;
//import vn.com.unit.jcanary.entity.ConstantDisplay;
//import vn.com.unit.jcanary.enumdef.CitySearchEnum;
//import vn.com.unit.jcanary.enumdef.DatabaseTypeEnum;
//import vn.com.unit.jcanary.repository.CityLanguageRepository;
//import vn.com.unit.jcanary.repository.CityRepository;
//import vn.com.unit.jcanary.service.CityService;
//import vn.com.unit.jcanary.service.ConstantDisplayService;
//import vn.com.unit.jcanary.service.DistrictService;
//import vn.com.unit.jcanary.service.LanguageService;
//import vn.com.unit.jcanary.service.ManualService;
//import vn.com.unit.jcanary.service.RegionService;
import vn.com.unit.ep2p.admin.exception.BusinessException;

/**
 * CityServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CityServiceImpl implements CityService {
	/** CityRepository */
	@Autowired
	private CityRepository cityRepository;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private CityLanguageRepository cityLanguageRepository;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private CmsRegionService regionService;

	@Autowired
	private DistrictService districtService;

//	@Autowired
//    ConstantDisplayService constantDisplayService;

//	@Autowired
//	ManualService manualService;

//    @Autowired
//	private JcaConstantService jcaConstantService;

	private static final Logger logger = LoggerFactory.getLogger(CityService.class);

	@Override
	@Transactional
	public void addOrEditCity(CityDto cityDto) {
		String userNameLogin = UserProfileUtils.getUserNameLogin();
		Long cityId = cityDto.getCityId();
		Long mCountryId = new Long(0);
		Long mRegionId = new Long(0);
		if (null != cityDto.getRegionCountry()) {
			String[] arrayRegionId = cityDto.getRegionCountry().toString().split("@");
			mCountryId = Long.parseLong(arrayRegionId[1]);
			mRegionId = Long.parseLong(arrayRegionId[0]);
		} else {
			throw new BusinessException(" Please choose Region and Country ");
		}

		// update data jca_m_city table
		City updateCity = new City();
		if (null != cityId) {
			updateCity = cityRepository.findOne(cityId);
			if (null == updateCity) {
				throw new BusinessException("Not found City by id= " + cityId);
			}
			updateCity.setUpdateBy(userNameLogin);
			updateCity.setUpdateDate(new Date());
		} else {
			updateCity.setCreateBy(userNameLogin);
			updateCity.setCreateDate(new Date());
		}
		updateCity.setCode(cityDto.getCityCode().toUpperCase().trim());
		updateCity.setDescription(cityDto.getDescription().trim());
		updateCity.setLatitude(cityDto.getLatitude());
		updateCity.setLongtitude(cityDto.getLongtitude());
		updateCity.setmCountryId(mCountryId);
		updateCity.setmRegionId(mRegionId);
		updateCity.setNote(cityDto.getNote().trim());
		updateCity.setcType(cityDto.getcType());
		updateCity.setPhoneCode(cityDto.getPhoneCode().trim());
		updateCity.setCarCode(cityDto.getCarCode().trim());
		updateCity.setZipCode(cityDto.getZipCode().trim());
		updateCity.setShipCode(cityDto.getShipCode().trim());
		
		if(StringUtils.isBlank(cityDto.getParentCity())) {
		    updateCity.setParentCityId(null);
        }else {
            String[] parentDistrict = StringUtils.split(cityDto.getParentCity(),"@");
            updateCity.setParentCityId(Long.valueOf(parentDistrict[0]));    
        }
		
		// Set enabled
        boolean activeFlag = cityDto.getActiveFlag();
        updateCity.setActiveFlag(activeFlag ? "1" : "0");

		try {
			cityRepository.save(updateCity);
			if (cityId == null) {
				cityId = updateCity.getId();
			}
			cityDto.setCityId(cityId);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}

		// update data jca_m_city_language table
		List<CityLanguageDto> listCityLanguageDto = cityDto.getCityLanguageDtos();

		for (CityLanguageDto cityLanguageDto : listCityLanguageDto) {
			CityLanguage cityLanguage = new CityLanguage();
			if (null != cityLanguageDto.getmCityId()) {
				cityLanguage = cityLanguageRepository.findOne(cityLanguageDto.getCityLanguageId());
				if (null == cityLanguage) {
					throw new BusinessException("Not found City Language with id=" + cityId);
				}
				cityLanguage.setUpdateDate(new Date());
				cityLanguage.setUpdateBy(userNameLogin);
			} else {
				cityLanguage.setCreateDate(new Date());
				cityLanguage.setCreateBy(userNameLogin);
			}
			cityLanguage.setmLanguageCode(cityLanguageDto.getmLanguageCode());
			cityLanguage.setmCityId(cityId);
			cityLanguage.setName(cityLanguageDto.getCityName().trim());

			try {
				cityLanguageRepository.save(cityLanguage);
			} catch (Exception ex) {
				throw new SystemException(ex);
			}
		}
	}

	@Override
	@Transactional
	public void deleteCity(String cityId) {
		String usernameLogin = UserProfileUtils.getUserNameLogin();
		City city = new City();
		List<CityLanguage> cityLanguages = new ArrayList<CityLanguage>();
		List<DistrictDto> listDistrictDto = new ArrayList<DistrictDto>();
		DistrictDto districtSearchDto = new DistrictDto();
		districtSearchDto.setmCityId(Long.parseLong(cityId));
		if (!StringUtils.isEmpty(cityId)) {
			city = cityRepository.findOne(Long.parseLong(cityId));
			cityLanguages = cityRepository.findCityIdLanguage(Long.parseLong(cityId));
			listDistrictDto = districtService.findAllDistrictListByCondition(districtSearchDto, "vi");
		}
		city.setDeleteDate(new Date());
		city.setDeleteBy(usernameLogin);
		try {
			cityRepository.save(city);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
		if (null != cityLanguages) {
			for (CityLanguage cityLanguage : cityLanguages) {
				cityLanguage.setDeleteDate(new Date());
				cityLanguage.setDeleteBy(usernameLogin);
				try {
					cityLanguageRepository.save(cityLanguage);
				} catch (Exception ex) {
					throw new SystemException(ex);
				}
			}
		}
		if (null != listDistrictDto) {
			for (DistrictDto districtDto : listDistrictDto) {
				districtService.deleteDistrict(districtDto.getDistrictId().toString());
			}
		}
	}

    private enum DBType {
        SQLSERVER, MYSQL, ORACLE;
    }

    public int countCityByCondition(CitySearchDto citySearchDto) {
        int count = 0 ;
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            count = cityRepository.countCityByCondition(citySearchDto);
            break;
        case MYSQL:
            count = cityRepository.countCityByCondition(citySearchDto);
            break;
        case ORACLE:
            count = cityRepository.countCityByCondition(citySearchDto);
            break;
        default:
            break;
        }
        return count;
    }

    public List<CityDto> findCityLimitByCondition(int offset, int sizeOfPage,
            CitySearchDto citySearchDto) {
        List<CityDto> list = new ArrayList<CityDto>();
        DBType dataType = DBType
                .valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            list = cityRepository.findCityLimitByConditionSQLServer(offset,
                    sizeOfPage, citySearchDto);
            break;
        case MYSQL:
            list = cityRepository.findCityLimitByConditionMYSQL(offset,
                    sizeOfPage, citySearchDto);
            break;
        case ORACLE:
            list = cityRepository.findCityLimitByConditionOracle(offset,
                    sizeOfPage, citySearchDto);
            break;    
        default:
            break;
        }
        return list;
    }

	@Override
	@Transactional
	public PageWrapper<CityDto> search(int page, CitySearchDto citySearchDto) {
		if (null == citySearchDto)
			citySearchDto = new CitySearchDto();
		// set SearchParm
		setSearchParm(citySearchDto);

		int sizeOfPage = citySearchDto.getPageSize() != null ? citySearchDto.getPageSize() : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<CityDto> pageWrapper = new PageWrapper<CityDto>(page, sizeOfPage);

//		int count = manualService.countCityByCondition(citySearchDto);
		int count = countCityByCondition(citySearchDto);

		List<CityDto> result = new ArrayList<CityDto>();
		if (count > 0) {
			int currentPage = pageWrapper.getCurrentPage();
			int startIndex = (currentPage - 1) * sizeOfPage;

//			result = manualService.findCityLimitByCondition(startIndex, sizeOfPage, citySearchDto);
			result = findCityLimitByCondition(startIndex, sizeOfPage, citySearchDto);

		}

		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	@Override
	public CityDto getCityDto(Long cityId) {
		CityDto cityDto = new CityDto();
		List<CityLanguageDto> cityLanguageDtos = new ArrayList<CityLanguageDto>();
		if (null == cityId) {
			return cityDto;
		}
		List<LanguageDto> languageList = languageService.getLanguageDtoList();

		cityDto.setCityId(cityId);
		if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
			cityDto = cityRepository.findCityDtoByIdOracle(cityId);
		} else {
			cityDto = cityRepository.findCityDtoById(cityId);
		}

		List<CityLanguage> cityLanguages = cityRepository.findCityIdLanguage(cityId);
		for (LanguageDto languageDto : languageList) {
			for (CityLanguage cityLanguage : cityLanguages) {
				CityLanguageDto cityLanguageDto = new CityLanguageDto();

				if (languageDto.getCode().equals(cityLanguage.getmLanguageCode())) {
					cityLanguageDto.setmLanguageCode(cityLanguage.getmLanguageCode());
					cityLanguageDto.setCityName(cityLanguage.getName());
					cityLanguageDto.setmCityId(cityLanguage.getmCityId());
					cityLanguageDto.setCityLanguageId(cityLanguage.getId());
					cityLanguageDtos.add(cityLanguageDto);
				}
			}
		}
		cityDto.setCityLanguageDtos(cityLanguageDtos);
		return cityDto;
	}

	@Override
	public void initScreenCityList(ModelAndView mav, CityDto cityDto, String language) {
		try {
		    // Init language
			List<LanguageDto> languageList = languageService.getLanguageDtoList();
			mav.addObject("languageList", languageList);
//			// Init country region
//			List<Select2Dto> listRegionDto = regionService.findAllRegionCountry(language);
//			mav.addObject("listRegionDto", listRegionDto);
//			// Init CType
//			List<ConstantDisplay> lstCType = constantDisplayService.findByTypeAndKind("CITY_TYPE","CITY");
//            mav.addObject("lstCType", lstCType);
//            //Init Parent District (khi update)
//            List<Select2Dto> lstCity = new ArrayList<>();
//            if(StringUtils.isNotBlank(cityDto.getParentCity())) {
//                String[] cityRegionCountry = StringUtils.split(cityDto.getRegionCountry(), "@");
//                List<String> ctypes = new ArrayList<String>(Arrays.asList("1","2"));
//                lstCity =  cityRepository.findAllCityByRegionId(language,Long.valueOf(cityRegionCountry[1]), ctypes); 
//            }
//            mav.addObject("lstCity", lstCity);
			RegionDto regionDto = new RegionDto();
            List<RegionDto> listRegionDto = regionService.findAllRegionListByCondition(regionDto, language);
            mav.addObject("listRegionDto", listRegionDto);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
		
	}

	@Override
	@Transactional
	public List<CityDto> findAllCityListByCondition(CityDto cityDto, String language) {
		List<CityDto> listCity = new ArrayList<>();
		if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
			listCity = cityRepository.findAllCityListByConditionOracle(cityDto, language);
		} else {
			listCity = cityRepository.findAllCityListByCondition(cityDto, language);
		}
		return listCity;
	}

	private void setSearchParm(CitySearchDto citySearchDto) {
		if (null == citySearchDto.getFieldValues()) {
			citySearchDto.setFieldValues(new ArrayList<String>());
		}

		if (citySearchDto.getFieldValues().isEmpty()) {
			citySearchDto.setCode(citySearchDto.getFieldSearch() != null ? citySearchDto.getFieldSearch().trim()
					: citySearchDto.getFieldSearch());
			citySearchDto.setName(citySearchDto.getFieldSearch() != null ? citySearchDto.getFieldSearch().trim()
					: citySearchDto.getFieldSearch());
			citySearchDto.setNote(citySearchDto.getFieldSearch() != null ? citySearchDto.getFieldSearch().trim()
					: citySearchDto.getFieldSearch());
			citySearchDto.setRegion(citySearchDto.getFieldSearch() != null ? citySearchDto.getFieldSearch().trim()
					: citySearchDto.getFieldSearch());
			citySearchDto.setCountry(citySearchDto.getFieldSearch() != null ? citySearchDto.getFieldSearch().trim()
					: citySearchDto.getFieldSearch());
		} else {
			for (String field : citySearchDto.getFieldValues()) {
				if (StringUtils.equals(field, CitySearchEnum.CODE.name())) {
					citySearchDto.setCode(citySearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, CitySearchEnum.NAME.name())) {
					citySearchDto.setName(citySearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, CitySearchEnum.DESCRIPTION.name())) {
					citySearchDto.setDescription(citySearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, CitySearchEnum.REGION.name())) {
					citySearchDto.setRegion(citySearchDto.getFieldSearch().trim());
					continue;
				}
				/*if (StringUtils.equals(field, CitySearchEnum.COUNTRY.name())) {
					citySearchDto.setCountry(citySearchDto.getFieldSearch().trim());
					continue;
				}*/
			}
		}
	}

	/**
	 * findAllCityList
	 *
	 * @param languageCode
	 * @return List<CityDto>
	 * @author hand
	 */
	@Override
	public List<CityDto> findAllCityList(String languageCode) {
		List<CityDto> resultList = new ArrayList<CityDto>();

		try {
			if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(),
					systemConfig.getConfig(SystemConfig.DBTYPE))) {
				resultList = cityRepository.findAllCityListOracle(languageCode);
			} else {
				resultList = cityRepository.findAllCityList(languageCode);
			}

		} catch (Exception e) {
			logger.error(e + ":" + e.getMessage());
		}
		return resultList;
	}

//	@Override
//	@Transactional
//	public List<Select2Dto> findAllCityListByCondition(String countryCode, String language) {
//		List<Select2Dto> listCity = new ArrayList<>();
//		if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(AppSystemConfig.DBTYPE))) {
//			listCity = cityRepository.findCityByConditionForOracle(countryCode, language);
//		} else {
//			listCity = cityRepository.findCityByCondition(countryCode, language);
//		}
//		return listCity;
//	}

//	@Override
//	public List<Select2Dto> findAllCityForSelect2(String term) {
//		List<Select2Dto> listCity = cityRepository.findAllCityForSelect2(term);
//		return listCity == null ? new ArrayList<>() : listCity;
//	}

//    @Override
//    public List<Select2Dto> findAllCity(String language) {
//        return cityRepository.findAllCity(language);
//    }

    @Override
    public List<Select2Dto> findAllCityByRegionId(String language, Long regionId, List<String> ctype) {
        return cityRepository.findAllCityByRegionId(language, regionId, ctype);
    }

}
