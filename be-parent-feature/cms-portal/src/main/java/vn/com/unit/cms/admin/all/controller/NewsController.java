/*******************************************************************************
* Class        ：NewsController
 * Created date ：2017/02/23
 * Lasted date  ：2017/02/23
 * Author       ：TaiTM
 * Change log   ：2017/02/23：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.controller.common.CmsCustomerCommonController;
import vn.com.unit.cms.admin.all.dto.NewsCategoryDto;
import vn.com.unit.cms.admin.all.dto.NewsEditDto;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.NewsCategoryService;
import vn.com.unit.cms.admin.all.service.NewsService;
import vn.com.unit.cms.admin.all.service.NewsTypeService;
import vn.com.unit.cms.admin.all.util.HDBankUtil;
import vn.com.unit.cms.admin.all.validator.NewsEditValidator;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.news.dto.NewsSearchDto;
import vn.com.unit.cms.core.module.news.dto.NewsSearchResultDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * NewsController
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Controller
@RequestMapping(value = { UrlConst.ROOT + "{customerAlias}" + UrlConst.ROOT + UrlConst.NEWS,
        UrlConst.ROOT + "{customerAlias}" + UrlConst.ROOT + UrlConst.PROMOTION })
public class NewsController extends CmsCustomerCommonController<NewsSearchDto, NewsSearchResultDto, NewsEditDto> {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsEditValidator newsEditValidator;

    @Autowired
    NewsCategoryService newsCategoryService;

    @Autowired
    private NewsTypeService newsTypeService;
    
    @Autowired
    private MessageSource msg;

    private static final String VIEW_EDIT = "views/CMS/all/news/news-edit.html";
    
    private static final String VIEW_LIST_SORT = "views/CMS/all/news/news-list-sort.html";

    private static final String VIEW_LIST_SORT_TABLE = "views/CMS/all/news/news-table-sort.html";
    
    private static final String TYPE_CHANGE = "type/change";
    
    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("news.list.title", null, locale);
    }
    
    @Override
    public void customDateFormat(ModelAndView mav) {
        super.customDateFormat(mav);
        mav.addObject("dateFormat", "FULL_DATE");
    }
    
    @RequestMapping(value = TYPE_CHANGE, method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String ajaxGetCategorySelect(@PathVariable("customerAlias") String customerAlias,
            @RequestParam(value = "typeId", required = true) Long typeId, Locale locale) {
        List<NewsCategoryDto> listData = newsCategoryService.findByTypeIdAndLanguageCode(typeId, locale.toString());
        return CommonJsonUtil.convertObjectToJsonString(listData);
    }

    @RequestMapping(value = "/search/list/sort", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getListByTypeId(@PathVariable String customerAlias,
            @ModelAttribute(value = "newsSearch") NewsSearchDto searchDto, Locale locale) {

        // ModelAndView
        ModelAndView mav = new ModelAndView(VIEW_LIST_SORT_TABLE);

        // languageCode
        String languageCode = locale.toString();
        searchDto.setLanguageCode(languageCode);

        List<NewsSearchResultDto> sortPageModel = new ArrayList<NewsSearchResultDto>();
        Long customerId = HDBankUtil.getCustomerType(customerAlias);
        searchDto.setCustTypeId(customerId);

        sortPageModel = newsService.getNewsListByProductForSorting(searchDto, locale.toString());
        mav.addObject("sortPageModel", sortPageModel);
        mav.addObject("newsSearch", searchDto);

        return mav;
    }
    @Override
    public String mavObjectEditName() {
        return "newsEditDto";
    }
    
    private boolean hasRoleAccess(String customerAlias) {
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            // Security for this page.
            if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_NEWS)) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    private boolean hasRoleEdit(String customerAlias) {
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            // Security for this page.
            if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_NEWS_EDIT.concat(ConstantCore.COLON_EDIT))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void initScreenListSort(ModelAndView mav, NewsSearchDto searchDto, Locale locale) {
        List<Select2Dto> newsTypeList = newsTypeService.getNewsTypeByCustomerId(searchDto.getCustTypeId(),
                locale.toString());
        mav.addObject("newsTypeList", newsTypeList);

        List<NewsCategoryDto> lstNewsTypeCategory = new ArrayList<>();
        if (StringUtils.isNotBlank(searchDto.getNewsTypeId())) {
            lstNewsTypeCategory = newsCategoryService
                    .findByTypeIdAndLanguageCode(Long.parseLong(searchDto.getNewsTypeId()), locale.toString());
        }
        mav.addObject("lstNewsTypeCategory", lstNewsTypeCategory);
    }

    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes, NewsSearchDto searchDto,
            Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

        redirectAttributes.addAttribute("newsTypeId", searchDto.getNewsTypeId());
        redirectAttributes.addAttribute("categoryId", searchDto.getCategoryId());
    }

    @Override
    public void initScreenEdit(ModelAndView mav, NewsEditDto editDto, Locale locale) {
        newsService.initNewsEdit(mav, editDto, locale);
    }

    @Override
    public String getFunctionCode() {
        return "M_NEWS";
    }
    
    @Override
    public String viewListSort(String customerAlias) {
        return VIEW_LIST_SORT;
    }

    @Override
    public String viewListSortTable(String customerAlias) {
        return VIEW_LIST_SORT_TABLE;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return VIEW_EDIT;
    }

    @Override
    public String getUrlByCustomerAlias(String customerAlias) {
        return customerAlias + UrlConst.ROOT + UrlConst.NEWS;
    }
    
    @Override
    public Class<NewsSearchResultDto> getClassSearchResult() {
        return NewsSearchResultDto.class;
    }

    @Override
    public boolean hasRoleForList(String customerAlias) {
        return hasRoleAccess(customerAlias);
    }

    @Override
    public boolean hasRoleForEdit(String customerAlias) {
        return hasRoleEdit(customerAlias);
    }

    @Override
    public boolean hasRoleForDetail(String customerAlias) {
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            // Security for this page.
            if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_NEWS)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public CmsCommonSearchFillterService<NewsSearchDto, NewsSearchResultDto, NewsEditDto> getCmsCommonSearchFillterService() {
        return newsService;
    }

    @Override
    public void validate(NewsEditDto editDto, BindingResult bindingResult) {
        newsEditValidator.validate(editDto, bindingResult);
    }

    @Override
    public String getTableForGenCode() {
        return "M_NEWS";
    }

    @Override
    public String getPrefixCode() {
        return CmsPrefixCodeConstant.PREFIX_CODE_NEWS;
    }
}