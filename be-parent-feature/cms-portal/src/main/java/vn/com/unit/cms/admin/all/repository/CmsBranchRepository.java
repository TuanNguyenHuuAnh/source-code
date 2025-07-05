package vn.com.unit.cms.admin.all.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.BranchSearchDto;
import vn.com.unit.cms.admin.all.dto.BranchSearchResultDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryDto;
import vn.com.unit.cms.admin.all.entity.Branch;
import vn.com.unit.db.repository.DbRepository;
import java.util.List;

import vn.com.unit.common.dto.Select2Dto;

public interface CmsBranchRepository extends DbRepository<Branch, Long>{
	
    public Page<BranchSearchResultDto> findBranchList(@Param("searchDto") BranchSearchDto condition,
            Pageable pageable,@Param("lang") String lang);
    public Integer countBranch(@Param("searchDto") BranchSearchDto condition);
    
    public List<Select2Dto> findRegion(@Param("lang") String lang);
    public List<Select2Dto> findCity(@Param("lang") String lang);
    
    Branch findByCode(@Param("code") String code);
    
    List<Select2Dto> findByRegionAndLanguageCode(@Param("lang") String lang);
    
    public List<Select2Dto> findAllDistrict(@Param("lang") String lang);
    public List<Select2Dto> findDistrict(@Param("city") String city,@Param("lang") String lang);

}
