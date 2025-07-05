/*******************************************************************************
 * Class        ：FaqsCategoryController
 * Created date ：2017/03/19
 * Lasted date  ：2017/03/19
 * Author       ：TaiTM
 * Change log   ：2017/03/19：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.controller.common.CmsCustomerCommonController;
import vn.com.unit.cms.admin.all.dto.FaqsCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategorySearchResultDto;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.FaqsCategoryService;
import vn.com.unit.cms.admin.all.validator.FaqsCategoryEditValidator;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

/**
 * FaqsCategoryController
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Controller
@RequestMapping("/{customerAlias}" + UrlConst.FAQS_CATEGORY)
public class FaqsCategoryController
        extends CmsCustomerCommonController<FaqsCategorySearchDto, FaqsCategorySearchResultDto, FaqsCategoryEditDto> {

    @Autowired
    private FaqsCategoryService faqsCategoryService;

    @Autowired
    private FaqsCategoryEditValidator faqsCategoryEditValidator;

    @Autowired
    private Select2DataService select2DataService;
    
    @Autowired
    private MessageSource msg;

    private static final String VIEW_EDIT = "views/CMS/all/faqs-category/faqs-category-edit.html";
    
    private static final String VIEW_LIST_SORT = "views/CMS/all/faqs-category/faqs-category-list-sort.html"; // "hdb.admin.faqs.category.list.sort";

    private static final String VIEW_TABLE_SORT = "views/CMS/all/faqs-category/faqs-category-table-sort.html"; // "hdb.admin.faqs.category.table.sort";

    private static final Logger logger = LoggerFactory.getLogger(FaqsCategoryController.class);

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("faqs.category.list.title", null, locale);
    }
    
    @Override
    public void initScreenListSort(ModelAndView mav, FaqsCategorySearchDto searchDto, Locale locale) {
        Long rootId = 1L;
        List<MenuNode> listTree = faqsCategoryService.getListTree(locale.toString(), rootId);
        String listTreeJson = CommonJsonUtil.convertObjectToJsonString(listTree);
        mav.addObject("listTreeJson", listTreeJson);
    }

    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes,
            FaqsCategorySearchDto searchDto, Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

        redirectAttributes.addAttribute("faqsCategoryParentId", searchDto.getFaqsCategoryParentId());
    }

    @Override
    public void initScreenEdit(ModelAndView mav, FaqsCategoryEditDto editDto, Locale locale) {
        List<Select2Dto> listItem = select2DataService.getListItem(UserProfileUtils.getCompanyId());
        mav.addObject("listItem", listItem);

        Long rootId = 1L;
        List<MenuNode> listTree = faqsCategoryService.getListTree(locale.toString(), rootId);
        String listTreeJson = CommonJsonUtil.convertObjectToJsonString(listTree);
        mav.addObject("listTreeJson", listTreeJson);
    }

    @Override
    public String getFunctionCode() {
        return "M_FAQS_CATEGORY";
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
    public String getUrlByCustomerAlias(String customerAlias) {
        return customerAlias.concat(UrlConst.FAQS_CATEGORY);
    }
    
    @Override
    public Class<FaqsCategorySearchResultDto> getClassSearchResult() {
        return FaqsCategorySearchResultDto.class;
    }

    @Override
    public boolean hasRoleForList(String customerAlias) {
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            // Security for this page.
            if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_FAQ_CATEGORY)) {
                return false;
            }
        } else if (customerAlias.equals(UrlConst.CORPORATE)) {
            return false;
        }

        return true;
    }

    @Override
    @Qualifier("faqsCategoryServiceImpl")
    public CmsCommonSearchFillterService<FaqsCategorySearchDto, FaqsCategorySearchResultDto, FaqsCategoryEditDto> getCmsCommonSearchFillterService() {
        return faqsCategoryService;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return VIEW_EDIT;
    }

    @Override
    public boolean hasRoleForEdit(String customerAlias) {
        if (customerAlias.equals(UrlConst.PERSONAL)) {
            // Security for this page.
            if (!UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_FAQS_CATEGORY_EDIT.concat(CoreConstant.COLON_EDIT))) {
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
            if (!UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_FAQS_CATEGORY)) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public void validate(FaqsCategoryEditDto editDto, BindingResult bindingResult) {
        faqsCategoryEditValidator.validate(editDto, bindingResult);
    }

    @Override
    public String getTableForGenCode() {
        return "M_FAQS_CATEGORY";
    }

    @Override
    public String getPrefixCode() {
        return CmsPrefixCodeConstant.PREFIX_CODE_FAQS_CATEGORY;
    }
}
