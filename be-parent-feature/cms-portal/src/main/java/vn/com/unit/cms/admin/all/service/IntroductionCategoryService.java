package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.admin.all.core.IntroductionCategoryNode;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryDto;
import vn.com.unit.cms.admin.all.dto.IntroductionLanguageDto;
import vn.com.unit.cms.admin.all.entity.IntroductionCategory;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.ep2p.admin.dto.SortOrderDto;

public interface IntroductionCategoryService extends DocumentWorkflowCommonService<IntroductionCategoryDto, IntroductionCategoryDto>{

    public PageWrapper<IntroductionCategoryDto> getActiveCategoryByCondition(int page, int sizeOfPage,
            CommonSearchDto searchDto, Locale locale);

    public IntroductionCategoryDto getIntroductionById(Long id, String lang);

    /**
     * 
     * @param id
     * @return
     */
    public IntroductionCategoryDto getCategory(Long id, Locale locale, String businessCode);

    /**
     * 
     * @param id
     * @return
     */
    public IntroductionCategoryDto getCategoryViewDto(Long id);

    /**
     * 
     * @param updateDto
     * @return
     * @throws IOException
     */
    public IntroductionCategoryDto saveUpdateCategory(IntroductionCategoryDto updateDto, Locale locale,
            HttpServletRequest request) throws IOException;

    /**
     * 
     * @param updateDto
     * @return
     * @throws IOException
     */
    public IntroductionCategoryDto saveNewCategory(IntroductionCategoryDto updateDto) throws IOException;

    /**
     * 
     * @param page
     * @param sizeOfPage
     * @return
     */
    public PageWrapper<IntroductionCategoryDto> getAllActiveCategory(int page, int sizeOfPage, String lang);

    /**
     * 
     * @param id
     * @return
     */
    public boolean deleteCateById(Long id);

    /**
     * 
     * @param locale
     * @return
     */
    public List<SearchKeyDto> genCategorySearchKeyList(Locale locale);

    /**
     * 
     * @param exceptCategoryId
     * @return
     */
    public List<IntroductionCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String lang);

    /**
     * 
     * @param code
     * @return
     */
    public int countCategoryByCode(String code);

    /**
     * 
     * @return
     */
    public Long getMaxCategorySort(Long parentId);

    /**
     * getAllActiveCategoryForSelection
     * 
     * @return
     */
    public List<IntroductionCategoryDto> getAllActiveCategoryForSelection();

    /**
     * 
     * @param exceptCategoryId
     * @return
     */
    public List<IntroductionCategoryDto> getCategoriesForSelection(Long exceptCategoryId, String lang);

    /**
     * getCategoryCount
     * 
     * @param linkAlias
     * @param exceptedId
     * @param parentId
     * @return
     */
    public int getCategoryCount(String languageCode, String linkAlias, Long exceptedId, Long parentId);

    /**
     * getMaxCodeIntroduction
     *
     * @author nhutnn
     * @return max code
     */
    String getMaxCodeIntroductionCategory();

    public List<IntroductionCategoryDto> getListSortRemovedId(Long id, Long parentId, String lang);

    public List<IntroductionCategoryDto> getAllActive(String lang, Integer status);
    
    /**
     * @param fileUrl
     * @param request
     * @param response
     * @return
     */
    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);
    
    public IntroductionCategory getIntroductionByType(Long customerId, Integer typeOfmain, Integer pictureIntroduction);
    
    public List<IntroductionLanguageDto> getListForSort(Long customerId, String language);
    
	public void updateSortAll(List<SortOrderDto> sortOderList);
	
	public void setDataForSearchDto(CommonSearchDto searchDto, String codeSearch, String nameSearch,
			Integer statusSearch, Integer enabledSearch, Integer typeOfMainSearch, Integer pictureIntroductionSearch);
	
	public void exportExcel(CommonSearchDto searchDto, HttpServletResponse res, Locale locale);
}
