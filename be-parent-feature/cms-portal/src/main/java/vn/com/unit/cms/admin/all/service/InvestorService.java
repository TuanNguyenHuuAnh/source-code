package vn.com.unit.cms.admin.all.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.core.InvestorCategoryNode;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryDto;
import vn.com.unit.cms.admin.all.dto.InvestorCategoryLanguageDto;
import vn.com.unit.cms.admin.all.dto.InvestorEditDto;
import vn.com.unit.cms.admin.all.dto.InvestorLanguageDto;
import vn.com.unit.cms.admin.all.dto.InvestorLanguageEditDto;
import vn.com.unit.cms.admin.all.dto.InvestorSearchDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.entity.Investor;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

public interface InvestorService extends DocumentWorkflowCommonService<InvestorEditDto, InvestorEditDto> {

    public void initSrceenSearch(ModelAndView mav, String businessCode, Locale locale);

    PageWrapper<InvestorLanguageDto> doSearch(int page, InvestorSearchDto searchDto, Locale locale);

    public void initSrceenEdit(ModelAndView mav, InvestorEditDto editDto, Locale locale);

    public InvestorEditDto getInvestorEditDto(Long id, Locale locale);

    public Investor getInvestorByCode(String code);

    public void doEdit(InvestorEditDto editDto, Locale locale, HttpServletRequest request) throws IOException;

    public void deleteInvestor(Investor editDto, Locale locale);

    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response);

    public Investor getInvestorById(Long id);

    public void exportExcel(InvestorSearchDto searchDto, HttpServletResponse res, Locale locale);

    public void initSortPage(Long categoryId, ModelAndView mav, Locale locale);

    public void updateModelsSort(SortPageDto sortPageModel);

    public List<InvestorCategoryLanguageDto> getAllInvestorCategoryLanguageDto(Locale locale);

    public List<InvestorCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String lang);

    public List<InvestorCategoryDto> getAllIntroductionCategoryActive(String languageCode, Integer status);

    public InvestorCategoryDto getInvestorCategoryDtoById(Long categoryId, Integer status, Locale locale);

    public List<InvestorCategoryDto> findAllActiveChildrenCategory(Long parentId, Locale locale);

    public InvestorCategoryDto checkTypeCategory(Long categoryId, Locale locale);

    public InvestorLanguageEditDto getByAliasAndCategoryId(Long categoryId, String linkAlias, String language);
}
