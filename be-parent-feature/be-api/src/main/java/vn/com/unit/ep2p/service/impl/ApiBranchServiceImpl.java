package vn.com.unit.ep2p.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.dto.SelectDto;
import vn.com.unit.cms.core.module.branch.dto.CmsBranchDto;
import vn.com.unit.cms.core.module.branch.dto.CmsBranchSearchDto;
import vn.com.unit.cms.core.module.news.repository.BranchCoreRepository;
import vn.com.unit.cms.core.module.news.repository.NewsRepository;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.utils.Utility;
import vn.com.unit.ep2p.service.ApiBranchService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiBranchServiceImpl implements ApiBranchService {
	private static final Logger logger = LoggerFactory.getLogger(ApiBranchServiceImpl.class);

	@Autowired
	private BranchCoreRepository branchRepository;

	@Autowired
	private Db2ApiService db2Service;

	@Autowired
	private NewsRepository newsRepository;
	
	@Override
	public List<CmsBranchDto> getBranchByCondition(Integer page, Integer sizeOfPage, CmsBranchSearchDto dto, Integer modeView) {
		try {
			int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			List<CmsBranchDto> data = branchRepository.getBranchByCondition(offsetSQL, sizeOfPage, dto, modeView);
			return data;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public List<SelectDto> getListRegionByCountry(Long counttryId, String language, Integer modeView) {
		try {
			List<SelectDto> data = branchRepository.getListRegionByCountry(counttryId, language, modeView);
			return data;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public List<Select2Dto> getListCityByCountry(Long counttryId, String language,
			Integer modeView) {
		try {
			//List<SelectDto> data = branchRepository.getListCityByCountry(counttryId, language, modeView);
			List<Select2Dto> data = db2Service.getCity();
			return data;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public int countBranchByCondition(CmsBranchSearchDto dto, Integer modeView) {
		return branchRepository.countBranchByCondition(dto, modeView);
	}

	@Override
	public List<SelectDto> getListConstantByKindAndCode(String kind, String code, String language) {
		try {
			List<SelectDto> data = branchRepository.getListConstantByKindAndCode(kind, code, language);
			return data;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public List<SelectDto> getListCityByRegionAndCountry(Long regionId, Long counttryId, String language,
			Integer modeView) {
		try {
			List<SelectDto> data = branchRepository.getListCityByRegionAndCountry(regionId, counttryId, language, modeView);
			return data;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	@Override
	public List<Select2Dto> getListDistrictByCity(String counttryId, String city, String language, Integer modeView) {
		try {
			//List<SelectDto> data = branchRepository.getListDistrictByCity(counttryId, city, language, modeView);
			List<Select2Dto> data = db2Service.getDistrictByCity(city, null);

			return data;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	@Override
	public List<Select2Dto> getListWardByDistrict(String district) {
		try {
			List<Select2Dto> data = db2Service.getWardByDistrict(district);

			return data;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

}
