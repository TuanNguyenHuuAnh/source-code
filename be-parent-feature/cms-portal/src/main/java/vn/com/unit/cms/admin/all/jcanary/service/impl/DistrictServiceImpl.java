/*******************************************************************************
 * Class        DistrictServiceImpl
 * Created date 2017/02/20
 * Lasted date  2017/02/20
 * Author       TranLTH
 * Change log   2017/02/2001-00 TranLTH create a new
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
import vn.com.unit.cms.admin.all.jcanary.dto.DistrictDto;
import vn.com.unit.cms.admin.all.jcanary.dto.DistrictLanguageDto;
import vn.com.unit.cms.admin.all.jcanary.dto.DistrictSearchDto;
import vn.com.unit.cms.admin.all.jcanary.entity.District;
import vn.com.unit.cms.admin.all.jcanary.entity.DistrictLanguage;
import vn.com.unit.cms.admin.all.jcanary.enumdef.DistrictSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.repository.DistrictLanguageRepository;
import vn.com.unit.cms.admin.all.jcanary.repository.DistrictRepository;
import vn.com.unit.cms.admin.all.jcanary.service.CityService;
import vn.com.unit.cms.admin.all.jcanary.service.DistrictService;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
import vn.com.unit.ep2p.admin.exception.BusinessException;

//import vn.com.unit.exception.BusinessException;
//import vn.com.unit.exception.SystemException;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.dto.CityDto;
//import vn.com.unit.jcanary.dto.DistrictDto;
//import vn.com.unit.jcanary.dto.DistrictLanguageDto;
//import vn.com.unit.jcanary.dto.DistrictSearchDto;
//import vn.com.unit.jcanary.dto.LanguageDto;
//import vn.com.unit.jcanary.dto.Select2Dto;
//import vn.com.unit.jcanary.entity.District;
//import vn.com.unit.jcanary.entity.DistrictLanguage;
//import vn.com.unit.jcanary.enumdef.DatabaseTypeEnum;
//import vn.com.unit.jcanary.enumdef.DistrictSearchEnum;
//import vn.com.unit.jcanary.repository.DistrictLanguageRepository;
//import vn.com.unit.jcanary.repository.DistrictRepository;
//import vn.com.unit.jcanary.service.CityService;
//import vn.com.unit.jcanary.service.ConstantDisplayService;
//import vn.com.unit.jcanary.service.DistrictService;
//import vn.com.unit.jcanary.service.LanguageService;
//import vn.com.unit.jcanary.service.ManualService;

/**
 * DistrictServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private DistrictLanguageRepository districtLanguageRepository;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private CityService cityService;

//    @Autowired
//    ConstantDisplayService constantDisplayService;

//    @Autowired
//    private ManualService manualService;

	@Override
	@Transactional
	public void addOrEditDistrict(DistrictDto districtDto) {
		String userNameLogin = UserProfileUtils.getUserNameLogin();
		Long districtId = districtDto.getDistrictId();
		Long regionId = new Long(0);
		Long countryId = new Long(0);
		Long cityId = new Long(0);
		// Get cityId, regionId, countryId
		if (null != districtDto.getCityRegionCountry()) {
			String[] cityRegionCountry = districtDto.getCityRegionCountry().toString().split("@");
			cityId = Long.parseLong(cityRegionCountry[0]);
			regionId = Long.parseLong(cityRegionCountry[1]);
			countryId = Long.parseLong(cityRegionCountry[2]);
		} else {
			throw new BusinessException(" Please choose City, Region and Country ");
		}
		// update data jca_m_district table
		District updateDistrict = new District();
		if (null != districtId) {
			updateDistrict = districtRepository.findOne(districtId);
			if (null == updateDistrict) {
				throw new BusinessException("Not found District by id= " + districtId);
			}
			updateDistrict.setUpdateBy(userNameLogin);
			updateDistrict.setUpdateDate(new Date());
		} else {
			updateDistrict.setCreateBy(userNameLogin);
			updateDistrict.setCreateDate(new Date());
		}
		updateDistrict.setCode(districtDto.getDistrictCode().toUpperCase().trim());
		updateDistrict.setDescription(districtDto.getDescription().trim());
		updateDistrict.setmCountryId(countryId);
		updateDistrict.setNote(districtDto.getNote().trim());
		updateDistrict.setLatitude(districtDto.getLatitude());
		updateDistrict.setLongtitude(districtDto.getLongtitude());
		updateDistrict.setmCityId(cityId);
		updateDistrict.setmRegionId(regionId);
		updateDistrict.setdType(districtDto.getdType());

		if (StringUtils.isBlank(districtDto.getParentDistrict())) {
			updateDistrict.setParentDistrictId(null);
		} else {
			String[] parentDistrict = StringUtils.split(districtDto.getParentDistrict(), "@");
			updateDistrict.setParentDistrictId(Long.valueOf(parentDistrict[0]));
		}

		// Set enabled
		boolean activeFlag = districtDto.getActiveFlag();
		updateDistrict.setActiveFlag(activeFlag ? "1" : "0");

		try {
			districtRepository.save(updateDistrict);
			if (districtId == null) {
				districtId = updateDistrict.getId();
			}

			districtDto.setDistrictId(updateDistrict.getId());
		} catch (Exception ex) {
			throw new SystemException(ex);
		}

		// update data jca_m_district_language table
		List<DistrictLanguageDto> listDistrictLanguageDto = districtDto.getDistrictLanguageDtos();

		for (DistrictLanguageDto districtLanguageDto : listDistrictLanguageDto) {
			DistrictLanguage districtLanguage = new DistrictLanguage();
			if (null != districtLanguageDto.getmDistrictId()) {
				districtLanguage = districtLanguageRepository.findOne(districtLanguageDto.getDistrictLanguageId());
				if (null == districtLanguage) {
					throw new BusinessException("Not found District Language with id=" + districtId);
				}
				districtLanguage.setUpdateDate(new Date());
				districtLanguage.setUpdateBy(userNameLogin);
			} else {
				districtLanguage.setCreateDate(new Date());
				districtLanguage.setCreateBy(userNameLogin);
			}
			districtLanguage.setmLanguageCode(districtLanguageDto.getmLanguageCode());
			districtLanguage.setmDistrictId(districtId);
			districtLanguage.setName(districtLanguageDto.getDistrictName().trim());

			try {
				districtLanguageRepository.save(districtLanguage);
			} catch (Exception ex) {
				throw new SystemException(ex);
			}
		}
	}

	@Override
	@Transactional
	public void deleteDistrict(String districtId) {
		String usernameLogin = UserProfileUtils.getUserNameLogin();
		District district = new District();
		List<DistrictLanguage> districtLanguages = new ArrayList<DistrictLanguage>();
		if (!StringUtils.isEmpty(districtId)) {
			district = districtRepository.findOne(Long.parseLong(districtId));
			districtLanguages = districtRepository.findDistrictIdLanguage(Long.parseLong(districtId));
		}
		district.setDeleteDate(new Date());
		district.setDeleteBy(usernameLogin);
		try {
			districtRepository.save(district);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
		if (null != districtLanguages) {
			for (DistrictLanguage districtLanguage : districtLanguages) {
				districtLanguage.setDeleteDate(new Date());
				districtLanguage.setDeleteBy(usernameLogin);
				try {
					districtLanguageRepository.save(districtLanguage);
				} catch (Exception ex) {
					throw new SystemException(ex);
				}
			}
		}
	}

    private enum DBType {
        SQLSERVER, MYSQL, ORACLE;
    }

    public int countDistrictByCondition(DistrictSearchDto districtSearchDto) {
        int count = 0 ;
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            count = districtRepository.countDistrictByCondition(districtSearchDto);
            break;
        case MYSQL:
            count = districtRepository.countDistrictByCondition(districtSearchDto);
            break;
        case ORACLE:
            count =  districtRepository.countDistrictByCondition(districtSearchDto);
            break;
        default:
            break;
        }
        return count;
    }
    
    public List<DistrictDto> findDistrictLimitByCondition(int offset,
            int sizeOfPage, DistrictSearchDto districtSearchDto) {
        List<DistrictDto> list = new ArrayList<DistrictDto>();
        DBType dataType = DBType
                .valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            list = districtRepository.findDistrictLimitByConditionSQLServer(
                    offset, sizeOfPage, districtSearchDto);
            break;
        case MYSQL:
            list = districtRepository.findDistrictLimitByConditionMYSQL(offset,
                    sizeOfPage, districtSearchDto);
            break;
        case ORACLE:
            list = districtRepository.findDistrictLimitByConditionSQLServer(
                    offset, sizeOfPage, districtSearchDto);
            break;    
        default:
            break;
        }
        return list;
    }
	
	@Override
	public PageWrapper<DistrictDto> search(int page, DistrictSearchDto districtSearchDto) {
		if (null == districtSearchDto)
			districtSearchDto = new DistrictSearchDto();

		// set SearchParm
		setSearchParm(districtSearchDto);

		int sizeOfPage = districtSearchDto.getPageSize() != null ? districtSearchDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<DistrictDto> pageWrapper = new PageWrapper<DistrictDto>(page, sizeOfPage);

//		int count = manualService.countDistrictByCondition(districtSearchDto);
		int count = countDistrictByCondition(districtSearchDto);
		
		List<DistrictDto> result = new ArrayList<DistrictDto>();
		if (count > 0) {
			// int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			int currentPage = pageWrapper.getCurrentPage();
			int offsetSQL = (currentPage - 1) * sizeOfPage;
			try {

//				result = manualService.findDistrictLimitByCondition(offsetSQL, sizeOfPage, districtSearchDto);
				result = findDistrictLimitByCondition(offsetSQL, sizeOfPage, districtSearchDto);
				
			} catch (Exception ex) {
				throw new SystemException(ex);
			}
		}

		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	@Override
	public DistrictDto getDistrictDto(Long districtId) {
		DistrictDto districtDto = new DistrictDto();
		List<DistrictLanguageDto> districtLanguageDtos = new ArrayList<DistrictLanguageDto>();
		if (null == districtId) {
			return districtDto;
		}
		List<LanguageDto> languageList = languageService.getLanguageDtoList();

		districtDto.setDistrictId(districtId);
		if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
			districtDto = districtRepository.findDistrictDtoById(districtId);
		} else {
			districtDto = districtRepository.findDistrictDtoById(districtId);
		}

		List<DistrictLanguage> districtLanguages = districtRepository.findDistrictIdLanguage(districtId);
		for (LanguageDto languageDto : languageList) {
			for (DistrictLanguage districtLanguage : districtLanguages) {
				DistrictLanguageDto districtLanguageDto = new DistrictLanguageDto();
				if (languageDto.getCode().equals(districtLanguage.getmLanguageCode())) {
					districtLanguageDto.setmLanguageCode(districtLanguage.getmLanguageCode());
					districtLanguageDto.setmDistrictId(districtLanguage.getmDistrictId());
					districtLanguageDto.setDistrictLanguageId(districtLanguage.getId());
					districtLanguageDto.setDistrictName(districtLanguage.getName());
					districtLanguageDtos.add(districtLanguageDto);
				}
			}
		}
		districtDto.setDistrictLanguageDtos(districtLanguageDtos);
		return districtDto;
	}

	@Override
	public void initScreenDistrictList(ModelAndView mav, DistrictDto districtDto, String language) {
		try {
			// Init language
			List<LanguageDto> languageList = languageService.getLanguageDtoList();
			mav.addObject("languageList", languageList);
//            // Init city
//            List<Select2Dto> listCityDto = cityService.findAllCity(language);
//            mav.addObject("listCityDto", listCityDto);
//            // Init DType
//            List<ConstantDisplay> lstDType = constantDisplayService.findByTypeAndKind("CITY_TYPE","DISTRICT");
//            mav.addObject("lstDType", lstDType);
//            //Init Parent District (khi update)
//            List<Select2Dto> lstDistrict = new ArrayList<>();
//            if(StringUtils.isNotBlank(districtDto.getParentDistrict())) {
//                String[] cityRegionCountry = StringUtils.split(districtDto.getCityRegionCountry(), "@");
//                List<String> dtypes = new ArrayList<String>(Arrays.asList("4","6","5"));
//                lstDistrict =  districtRepository.findAllDistrictListByCityIdByType(Long.valueOf(cityRegionCountry[0]), language, dtypes); 
//            }
//            mav.addObject("lstDistrict", lstDistrict);
			CityDto cityDto = new CityDto();
			List<CityDto> listCityDto = cityService.findAllCityListByCondition(cityDto, language);
			mav.addObject("listCityDto", listCityDto);

		} catch (Exception ex) {
			throw new SystemException(ex);
		}

	}

	@Override
	public List<DistrictDto> findAllDistrictListByCondition(DistrictDto districtDto, String language) {
		List<DistrictDto> listDistrictDto = new ArrayList<DistrictDto>();
		if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
			listDistrictDto = districtRepository.findDistrictListByCondition(districtDto, language);
		} else {
			listDistrictDto = districtRepository.findDistrictListByCondition(districtDto, language);
		}
		return listDistrictDto;
	}

	@Override
	public List<Select2Dto> findAllDistrictListByCityId(Long cityId, String language) {
		List<Select2Dto> listDistrict = new ArrayList<>();
		if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
			listDistrict = districtRepository.findAllDistrictListByCityIdOracle(cityId, language);
		} else {
			listDistrict = districtRepository.findAllDistrictListByCityId(cityId, language);
		}
		return listDistrict;
	}

	@Override
	public List<Select2Dto> findAllDistrictListByCityIdByType(Long cityId, String language, List<String> type) {
		return districtRepository.findAllDistrictListByCityIdByType(cityId, language, type);
	}

	@Override
	public List<Select2Dto> findAllDistrictListByConditionForCombobox(DistrictDto districtDto, String language) {
		List<DistrictDto> listDistrict = findAllDistrictListByCondition(districtDto, language);
		List<Select2Dto> listDistrictForCombobox = new ArrayList<>();
		for (DistrictDto district : listDistrict) {
			Select2Dto comboboxElement = new Select2Dto();
			comboboxElement.setId(district.getDistrictId().toString());
			comboboxElement.setName(district.getDistrictName());
			comboboxElement.setText(district.getDistrictName());
			listDistrictForCombobox.add(comboboxElement);
		}
		return listDistrictForCombobox;
	}

	@Override
	public List<Select2Dto> findAllDistrictForSelect2(String term) {
		List<Select2Dto> listDistrict = districtRepository.findAllDistrictForSelect2(term);
		return listDistrict == null ? new ArrayList<>() : listDistrict;
	}

	private void setSearchParm(DistrictSearchDto districtSearchDto) {
		if (null == districtSearchDto.getFieldValues()) {
			districtSearchDto.setFieldValues(new ArrayList<String>());
		}

		if (districtSearchDto.getFieldValues().isEmpty()) {
			districtSearchDto
					.setCode(districtSearchDto.getFieldSearch() != null ? districtSearchDto.getFieldSearch().trim()
							: districtSearchDto.getFieldSearch());
			districtSearchDto
					.setName(districtSearchDto.getFieldSearch() != null ? districtSearchDto.getFieldSearch().trim()
							: districtSearchDto.getFieldSearch());
			districtSearchDto
					.setNote(districtSearchDto.getFieldSearch() != null ? districtSearchDto.getFieldSearch().trim()
							: districtSearchDto.getFieldSearch());
			districtSearchDto
					.setCity(districtSearchDto.getFieldSearch() != null ? districtSearchDto.getFieldSearch().trim()
							: districtSearchDto.getFieldSearch());
			districtSearchDto
					.setRegion(districtSearchDto.getFieldSearch() != null ? districtSearchDto.getFieldSearch().trim()
							: districtSearchDto.getFieldSearch());
			districtSearchDto
					.setCountry(districtSearchDto.getFieldSearch() != null ? districtSearchDto.getFieldSearch().trim()
							: districtSearchDto.getFieldSearch());
		} else {
			for (String field : districtSearchDto.getFieldValues()) {
				if (StringUtils.equals(field, DistrictSearchEnum.CODE.name())) {
					districtSearchDto.setCode(districtSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, DistrictSearchEnum.NAME.name())) {
					districtSearchDto.setName(districtSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, DistrictSearchEnum.NOTE.name())) {
					districtSearchDto.setNote(districtSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, DistrictSearchEnum.REGION.name())) {
					districtSearchDto.setRegion(districtSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, DistrictSearchEnum.CITY.name())) {
					districtSearchDto.setCity(districtSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, DistrictSearchEnum.COUNTRY.name())) {
					districtSearchDto.setCountry(districtSearchDto.getFieldSearch().trim());
					continue;
				}
			}
		}
	}
}
