/*******************************************************************************
 * Class        ：ShareHolderManagementService
 * Created date ：2017/02/14
 * Lasted date  ：2017/02/14
 * Author       ：thuydtn
 * Change log   ：2017/02/14：01-00 thuydtn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.core.IntroductionCategoryNode;
import vn.com.unit.cms.admin.all.dto.IntroductionCategoryDto;
import vn.com.unit.cms.admin.all.dto.IntroductionDto;
import vn.com.unit.cms.admin.all.dto.IntroductionLanguageDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;

public interface IntroductionService extends DocumentWorkflowCommonService<IntroductionDto, IntroductionDto> {
    /**
     * @param id
     * @return
     */
    public IntroductionDto getDetailById(Long id);

    /**
     * @param updateDto
     * @param status
     * @return
     * @throws IOException
     */
	public IntroductionDto doEdit(IntroductionDto updateDto, Locale locale, HttpServletRequest request)
			throws IOException;
    
    /**
     * 
     * @param id
     * @return
     */
    public IntroductionDto getIntroductionUpdateDto(Long id, Locale locale, String businessCode);

    /**
     * 
     * @param id
     * @return
     */
    public IntroductionDto getIntroductionViewDto(Long id);

    /**
     * 
     * @param page
     * @param sizeOfPage
     * @return
     */
    public PageWrapper<IntroductionDto> getAllActiveIntroduction(int page, int sizeOfPage, Locale locale);

    /**
     * 
     * @return
     */
    public List<IntroductionDto> getAllActiveIntroduction(String language);

    /**
     * 
     * @param page
     * @param sizeOfPage
     * @param searchDto
     * @return
     */
    public PageWrapper<IntroductionLanguageDto> getActiveIntroductionByCondition(int page, int sizeOfPage,
            CommonSearchDto searchDto, Locale locale);

    /**
     * 
     * @param id
     * @return
     */
    public boolean deleteIntroById(Long id);

    /**
     * requestEditorDownload
     * 
     * @return
     */
    public IntroductionDto initIntroductionDto();

    /**
     * 
     * @param locale
     * @return
     */
    public List<SearchKeyDto> genIntroductionSearchKeyList(Locale locale);

    /**
     * @param code
     * @return
     */
    public int countIntroductionByCode(String code);
    
    /**
     * @param id
     * @return
     */
    public IntroductionDto getIntroductionObject(Long id);

    /**
     * @param imageUrl
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public boolean requestDownloadImage(String imageUrl, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * @param fileUrl
     * @param request
     * @param response
     * @return
     */
    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);

    /**
     * 
     * @param categoryId
     * @return
     */
    public Long getMaxIntroductionSort(Long categoryId);

    /**
     * 
     * @param sortPageModel
     */
    public void updateModelsSort(SortPageDto sortPageModel);

    /**
     * 
     * @param model
     * @param mav
     */
    public void initSortPage(Long categoryId, ModelAndView mav, Locale locale);

    /**
     * countCategoryItemWithAlias
     * 
     * @param linkAlias
     * @param id
     * @return
     */
    public int countCategoryItemWithAlias(String languageCode, Long categoryId, String linkAlias, Long exceptedId);

    /**
     * getMaxCodeIntroduction
     *
     * @author nhutnn
     * @return max code
     */
    String getMaxCodeIntroduction();
    
    /**
     * 
     * @param exceptCategoryId
     * @return
     */
    public List<IntroductionCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String lang);
    
    /**
     * 
     * @param exceptCategoryId
     * @return
     */
    public List<IntroductionCategoryDto> getCategoriesForSelection(Long exceptCategoryId, String lang);
    
    /**
     * 
     * @return
     */
    public Long getMaxCategorySort(Long parentId);
    
    List<Select2Dto> getListForSort(String language, Long customerId, Long categoryId, Long id);
    
    void setDataForSearchDto(CommonSearchDto searchDto, String codeSearch, String nameSearch, Integer statusSearch, Integer enabledSearch,
			Long categoryIdSearch);
    
    public void exportExcel(CommonSearchDto searchDto, HttpServletResponse res, Locale locale);
}