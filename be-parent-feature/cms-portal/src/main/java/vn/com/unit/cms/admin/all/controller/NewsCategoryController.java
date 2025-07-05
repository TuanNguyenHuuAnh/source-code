/*******************************************************************************
* Class        ：NewsCategoryController
 * Created date ：2017/02/27
 * Lasted date  ：2017/02/27
 * Author       ：TaiTM
 * Change log   ：2017/02/27：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import vn.com.unit.cms.admin.all.dto.NewsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.NewsCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.NewsCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.NewsTypeDto;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.NewsCategoryService;
import vn.com.unit.cms.admin.all.service.NewsTypeService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.util.HDBankUtil;
import vn.com.unit.cms.admin.all.validator.NewsCategoryEditValidator;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;

/**
 * NewsCategoryController
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Controller
@RequestMapping(value = UrlConst.ROOT + "{customerAlias}" + UrlConst.ROOT + UrlConst.NEWS_CATEGORY)
public class NewsCategoryController
        extends CmsCustomerCommonController<NewsCategorySearchDto, NewsCategorySearchResultDto, NewsCategoryEditDto> {

    @Autowired
    private NewsCategoryService newsCategoryService;

    @Autowired
    private NewsTypeService newsTypeService;
    
    @Autowired
    private JcaConstantService jcaConstantService;

    @Autowired
    private NewsCategoryEditValidator newsCategoryEditValidator;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String VIEW_EDIT = "views/CMS/all/news-category/news-category-edit.html";

    private static final String VIEW_LIST_SORT = "views/CMS/all/news-category/news-category-list-sort.html";

    private static final String VIEW_TABLE_SORT = "views/CMS/all/news-category/news-category-table-sort.html";
    
    private static final String VIEW_TABLE = "views/CMS/all/news-category/news-category-table.html";
    
    private static final String VIEW_LIST = "views/CMS/all/news-category/news-category-list.html";
    
    private static final String TYPE_CHANGE = "type/change";

    private static final String SORT_TYPE_CHANGE = "sort/type/change";

    @Autowired
    private MessageSource msg;
    
    @Autowired
    private Select2DataService select2DataService;

    /**
     * getMaxSortByTypeId
     *
     * @param typeId
     * @return maxSort
     * @author TaiTM
     */
    @RequestMapping(value = TYPE_CHANGE, method = RequestMethod.POST)
    public @ResponseBody String getMaxSortByTypeId(@PathVariable("customerAlias") String customerAlias,
            @RequestParam(value = "typeId", required = false) Long typeId,
            @RequestParam(value = "categoryId", required = false) Long categoryId, Locale locale) {
        if (typeId == null) {
            typeId = -99L;
        }
        return newsCategoryService.getBeforeIdSelectJsonByTypeId(HDBankUtil.getCustomerType(customerAlias), typeId,
                categoryId, locale.toString());
    }

    @RequestMapping(value = SORT_TYPE_CHANGE, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView getListByTypeId(@PathVariable("customerAlias") String customerAlias,
            @ModelAttribute(value = "newsSearch") NewsCategorySearchDto dto, Locale locale) {
        // Security for this page.
        if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_NEWS_CATEGORY_EDIT) && !UserProfileUtils
                .hasRole(CmsRoleConstant.BUTTON_NEWS_CATEGORY_EDIT.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        // ModelAndView
        ModelAndView mav = new ModelAndView(VIEW_TABLE_SORT);

        // languageCode
        String languageCode = locale.toString();
        dto.setLanguageCode(languageCode);

        List<NewsCategorySearchResultDto> sortPageModel = newsCategoryService.getListForSort(dto);

        mav.addObject("sortPageModel", sortPageModel);
        mav.addObject("newsSearch", dto);

        return mav;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("news.category.list.title", null, locale);
    }

    @Override
    public void initScreenListSort(ModelAndView mav, NewsCategorySearchDto searchDto, Locale locale) {
        // newsTypeList
        List<NewsTypeDto> newsTypeList = newsTypeService.findListByCustomerIdAndPromotion(searchDto.getCustomerTypeId(),
                locale.toString(), false);
        // newsTypeId
        Long newsTypeId = newsTypeList.size() > 0 ? newsTypeList.get(0).getId() : -1;

        // setTypeId
        if (searchDto.getNewsTypeId() == null) {
            searchDto.setNewsTypeId(newsTypeId.toString());
        }
        mav.addObject("newsTypeList", newsTypeList);
    }

    @Override
    public void initScreenEdit(ModelAndView mav, NewsCategoryEditDto editDto, Locale locale) {
        CmsLanguageUtils.initLanguageList(mav);

        List<Select2Dto> newsTypeList = newsTypeService.getNewsTypeByCustomerId(editDto.getCustomerTypeId(),
                locale.toString());
        mav.addObject("newsTypeList", newsTypeList);

        List<Select2Dto> listDataType = select2DataService.getConstantData("NEWS_TYPE", "CATEGORY_NEWS", locale.toString());
        mav.addObject("listCategoryType", listDataType);
        
        List<JcaConstantDto> channels = jcaConstantService.getListJcaConstantDisplayByType("CHANNEL");
        mav.addObject("channels", channels);
    }

    @Override
    public String getFunctionCode() {
        return "M_NEWS_CATEGORY";
    }

    @Override
    public String viewListSort(String customerAlias) {
        return VIEW_LIST_SORT;
    }

    @Override
    public String viewListSortTable(String customerAlias) {
        return VIEW_TABLE_SORT;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return VIEW_EDIT;
    }

    @Override
    public String getUrlByCustomerAlias(String customerAlias) {
        return customerAlias + UrlConst.ROOT + UrlConst.NEWS_CATEGORY;
    }

    @Override
    public Class<NewsCategorySearchResultDto> getClassSearchResult() {
        return NewsCategorySearchResultDto.class;
    }

    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes,
            NewsCategorySearchDto searchDto, Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

        redirectAttributes.addAttribute("newsTypeId", searchDto.getNewsTypeId());
    }

    @Override
    public boolean hasRoleForList(String customerAlias) {
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            // Security for this page.
            if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_NEWS_CATEGORY) && !UserProfileUtils
                    .hasRole(CmsRoleConstant.PAGE_LIST_NEWS_CATEGORY.concat(ConstantCore.COLON_EDIT))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasRoleForEdit(String customerAlias) {
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            // Security for this page.
            if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_NEWS_CATEGORY_EDIT) && !UserProfileUtils
                    .hasRole(CmsRoleConstant.BUTTON_NEWS_CATEGORY_EDIT.concat(ConstantCore.COLON_EDIT))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasRoleForDetail(String customerAlias) {
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            // Security for this page.
            if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_NEWS_CATEGORY) && !UserProfileUtils
                    .hasRole(CmsRoleConstant.PAGE_DETAIL_NEWS_CATEGORY.concat(ConstantCore.COLON_EDIT))) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public CmsCommonSearchFillterService<NewsCategorySearchDto, NewsCategorySearchResultDto, NewsCategoryEditDto> getCmsCommonSearchFillterService() {
        return newsCategoryService;
    }

    @Override
    public void validate(NewsCategoryEditDto editDto, BindingResult bindingResult) {
        newsCategoryEditValidator.validate(editDto, bindingResult);
    }

    @Override
    public String getTableForGenCode() {
        return "M_NEWS_CATEGORY";
    }

    @Override
    public String getPrefixCode() {
        return CmsPrefixCodeConstant.PREFIX_CODE_NEWS_CATEGORY;
    }
    @Override
    public String viewListTable(String customerAlias) {
        return VIEW_TABLE;
    }

	@Override
	public String viewList(String customerAlias) {
		return VIEW_LIST;
	}
    

}
