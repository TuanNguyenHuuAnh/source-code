package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.cms.admin.all.dto.BranchEditDto;
import vn.com.unit.cms.admin.all.dto.BranchSearchDto;
import vn.com.unit.cms.admin.all.dto.BranchSearchResultDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryDto;
import vn.com.unit.cms.admin.all.dto.NewsCategoryEditDto;
import vn.com.unit.cms.admin.all.entity.Branch;
import vn.com.unit.cms.core.module.banner.entity.Banner;
import vn.com.unit.common.dto.Select2Dto;

public interface CmsBranchService  extends CmsCommonSearchFillterService<BranchSearchDto, BranchSearchResultDto, BranchEditDto>{
	
	public List<Select2Dto> getAllRegion(String lang);
	
    public BranchEditDto getBranchEdit(Long bannerId, Locale locale);
    
    public Branch findByCode(String code);
    
    public void addOrEdit(BranchEditDto branchEditDto);
    
    public void deleteBranch(Branch branch);
    
    public List<Select2Dto> findByRegionAndLanguageCode( String languageCode);
    
	public List<Select2Dto> getAlldistrict(String city,String lang);

}
