package vn.com.unit.cms.admin.all.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.controller.common.CmsCommonController;
import vn.com.unit.cms.admin.all.controller.common.CmsCustomerCommonController;
import vn.com.unit.cms.admin.all.dto.BannerEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.cms.admin.all.dto.FaqsCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.HihiEditDto;
import vn.com.unit.cms.admin.all.dto.ProductEditDto;
import vn.com.unit.cms.admin.all.dto.ProductManagementEditDto;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.DocumentCategoryService;
import vn.com.unit.cms.admin.all.service.ProductManagementService;
import vn.com.unit.cms.admin.all.validator.DocumentCategoryValidator;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.product.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.core.module.product.dto.ProductSearchDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

@Controller
@RequestMapping(value = UrlConst.ROOT + "hihi")
public class ProductController extends
CmsCommonController<ProductSearchDto, ProductLanguageSearchDto, ProductManagementEditDto> {

    @Autowired
    private DocumentCategoryValidator categoryValidator;

    @Autowired
    private MessageSource msg;

    @Autowired
    private ProductManagementService documentCategoryService;

    @Autowired
    private Select2DataService select2DataService;

    private static final String VIEW_EDIT = "views/CMS/all/product/product-edit.html";

    private static final String VIEW_LIST_SORT = "views/CMS/all/product/product-list.html";

    private static final String VIEW_TABLE_SORT = "views/CMS/all/product/product-table.html";

    
    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes,
    		ProductSearchDto searchDto, Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

//        redirectAttributes.addAttribute("parentId", searchDto.getParentId());
    }

    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("document.category.list.title", null, locale);
    }

    @Override
    public void initScreenEdit(ModelAndView mav, ProductManagementEditDto editDto, Locale locale) {
        List<Select2Dto> listItem = select2DataService.getListItem(UserProfileUtils.getCompanyId());
        mav.addObject("listItem", listItem);

//        Long rootId = 1L;
//        List<MenuNode> listTree = documentCategoryService.getListTree(locale.toString(), rootId, editDto.getId());
//        String listTreeJson = CommonJsonUtil.convertObjectToJsonString(listTree);
//        mav.addObject("listTreeJson", listTreeJson);

//        List<Select2Dto> listDataType = select2DataService.getConstantData("DOCUMENT", "CATEGORY_DOCUMENT", locale.toString());
//        mav.addObject("listCategoryType", listDataType);

    }

    @Override
    public String getFunctionCode() {
        return "M_PRODUCT";
    }

    @Override
    public String getTableForGenCode() {
        return "M_PRODUCT";
    }

    @Override
    public String getPrefixCode() {
        return CmsPrefixCodeConstant.PREFIX_CODE_DOCUMENT_CATEGORY;
    }

    @Override
    public String viewListSort() {
        return VIEW_LIST_SORT;
    }

    @Override
    public String viewListSortTable() {
        return VIEW_TABLE_SORT;
    }

    @Override
    public String viewEdit() {
        return VIEW_EDIT;
    }


    @Override
    public Class<ProductLanguageSearchDto> getClassSearchResult() {
        return ProductLanguageSearchDto.class;
    }

    @Override
    public boolean hasRoleForList() {
        return UserProfileUtils.hasRole("SCREEN#CMS#PAGE_LIST_DOCUMENT_CATEGORY");
    }

    @Override
    public boolean hasRoleForEdit() {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_DOCUMENT_CATEGORY_EDIT.concat(CoreConstant.COLON_EDIT));
    }

    @Override
    public boolean hasRoleForDetail() {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_DOCUMENT_CATEGORY);
    }

    @Override
    public CmsCommonSearchFillterService<ProductSearchDto, ProductLanguageSearchDto, ProductManagementEditDto> getCmsCommonSearchFillterService() {
        return documentCategoryService;
    }

    @Override
    public void validate(ProductManagementEditDto editDto, BindingResult bindingResult) {
        categoryValidator.validate(editDto, bindingResult);
    }

	@Override
	public String getUrlByAlias() {
		return "hihi";
	}

	
}
