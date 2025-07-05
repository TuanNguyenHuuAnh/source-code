package vn.com.unit.ep2p.service;

import java.util.List;

import vn.com.unit.cms.core.dto.SelectDto;
import vn.com.unit.cms.core.module.branch.dto.CmsBranchDto;
import vn.com.unit.cms.core.module.branch.dto.CmsBranchSearchDto;
import vn.com.unit.common.dto.Select2Dto;

public interface ApiBranchService {

	public List<CmsBranchDto> getBranchByCondition(Integer page, Integer size, CmsBranchSearchDto dto, Integer modeView);

	public List<SelectDto> getListRegionByCountry(Long counttryId, String language, Integer modeView);

	public List<SelectDto> getListCityByRegionAndCountry(Long regionId, Long counttryId, String language,
			Integer modeView);

	public int countBranchByCondition(CmsBranchSearchDto dto, Integer modeView);

	public List<SelectDto> getListConstantByKindAndCode(String kind, String code, String language);

	public List<Select2Dto> getListCityByCountry(Long countryId, String language, Integer modeView);

	public List<Select2Dto> getListDistrictByCity(String counttryId, String city, String language, Integer modeView);
	
	public List<Select2Dto> getListWardByDistrict(String district);
}
