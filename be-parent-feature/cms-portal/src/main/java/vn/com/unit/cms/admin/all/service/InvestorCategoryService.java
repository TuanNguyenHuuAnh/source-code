package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.core.InvestorCategoryNode;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.entity.InvestorCategory;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

public interface InvestorCategoryService extends DocumentWorkflowCommonService<InvestorCategoryDto, InvestorCategoryDto>{

	public void initScreenListSearch(ModelAndView mav, InvestorCategorySearchDto searchDto, Locale locale);

	public PageWrapper<InvestorCategoryDto> doSearch(int page, InvestorCategorySearchDto searchDto, Locale locale);

	public InvestorCategory findById(Long id);

	public boolean deleteInvestorCategory(Long id);

	public InvestorCategoryDto getDataCategory(Long id, Locale locale, String businessCode);

	public List<InvestorCategoryDto> getListSortId(Long id, Long parentId, String lang);

	public void setDataForSearchDto(InvestorCategorySearchDto searchDto, String codeSearch, String nameSearch,
			Integer statusSearch, Integer categorySearch);

	public InvestorCategoryDto checkTypeCategory(Long categoryId, Locale locale);

	public List<InvestorCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String lang);

	public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);

	public void initDataEditPage(ModelAndView mav, InvestorCategoryDto editDto, Locale locale);

	public InvestorCategory getInvestorCategoryByCode(String code);

	public InvestorCategoryLanguageDto getByAliasAndCategoryId(Long categoryId,Long customerId, String linkAlias, String language);

	public void doEdit(InvestorCategoryDto editDto, Locale locale, HttpServletRequest request) throws IOException;

	public InvestorCategoryDto getInvestorCategoryDtoById(Long categoryId, Integer status, Locale locale);

	public List<InvestorCategoryDto> findAllActiveChildrenCategory(Long parentId, Locale locale);

	List<InvestorCategoryDto> findExistOfParentIdById(Long id, Long customerId);

	public List<InvestorCategoryDto> getInvestorCateRoot(List<InvestorCategoryDto> investorList);

	public List<InvestorCategoryDto> sortInvestorCategoryDtoByConstructorTree(List<InvestorCategoryDto> investorList,
			List<InvestorCategoryDto> resultRoot);

	public void exportExcelInvestorCategory(InvestorCategorySearchDto searchDto, HttpServletResponse response,
			Locale locale);

	public void initSortPage(Long parentId, ModelAndView mav, Locale locale);

	public void updateModelsSort(SortPageDto sortPageModel);

	public boolean checkRelationshipChild(Long id);

	/** Get list dependencies of Product categories
     * @author tranlth - 23/05/219
     * @param id
     * 
     * @return List<Map <String, String>>
     */
    public List<Map <String, String>> listDependencies(Long id);
}
