package vn.com.unit.cms.core.module.news.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.dto.SelectDto;
import vn.com.unit.cms.core.module.branch.dto.CmsBranchDto;
import vn.com.unit.cms.core.module.branch.dto.CmsBranchSearchDto;
import vn.com.unit.cms.core.module.branch.entity.Branch;
import vn.com.unit.cms.core.module.news.dto.NewsByTypeSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsSearchResultDto;
import vn.com.unit.db.repository.DbRepository;

public interface BranchCoreRepository extends DbRepository<Branch, Long>{

    public List<NewsSearchResultDto> searchNewsByType(
            @Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("searchDto")NewsByTypeSearchDto searchDto, @Param("language")String language, @Param("modeView")Integer modeView);
    public List<SelectDto> getListRegionByCountry(@Param("counttryId")Long counttryId, @Param("language") String language, @Param("modeView")Integer modeView);

    public List<CmsBranchDto> getBranchByCondition(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage
    		, @Param("dto") CmsBranchSearchDto dto, @Param("modeView")Integer modeView);
    public List<SelectDto> getListCityByRegionAndCountry(@Param("regionId")Long regionId, @Param("counttryId")Long counttryId, @Param("language")String language,
            @Param("modeView")Integer modeView);
	public int countBranchByCondition(@Param("dto") CmsBranchSearchDto dto, @Param("modeView")Integer modeView);
	public List<SelectDto> getListConstantByKindAndCode(@Param("kind")String kind, @Param("code")String code, @Param("language")String language);
	public List<SelectDto> getListCityByCountry(@Param("counttryId")Long counttryId, @Param("language") String language, @Param("modeView")Integer modeView);
	public List<SelectDto> getListDistrictByCity(@Param("counttryId")Long counttryId, @Param("city")Long city, @Param("language")String language, @Param("modeView")Integer modeView);

}
