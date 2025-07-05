package vn.com.unit.cms.admin.all.controller;

import java.util.List;
import java.util.Locale;
import java.time.Year;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.controller.common.CmsCommonController;
import vn.com.unit.cms.admin.all.dto.ProductManagementEditDto;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.ProductManagementService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.product.dto.ProductLanguageSearchDto;
import vn.com.unit.cms.core.module.product.dto.ProductSearchDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;

@Controller
@RequestMapping(value = UrlConst.ROOT + "product")
public class ProductManagmentController extends
CmsCommonController<ProductSearchDto, ProductLanguageSearchDto, ProductManagementEditDto> {

    @Autowired
    private MessageSource msg;

    @Autowired
    private ProductManagementService productManagementService;

    @Autowired
    private Select2DataService select2DataService;

    private static final String VIEW_EDIT = "views/CMS/all/product/product-edit.html";

    private static final String VIEW_LIST_SORT = "views/CMS/all/product/product-list.html";

    private static final String VIEW_TABLE_SORT = "views/CMS/all/product/product-table.html";

    @Override
    public void initScreenList(ModelAndView mav, ProductSearchDto documentSearchDto, Locale locale) {
        mav.addObject("hasRoleMaker", false);
    }
    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes,
    		ProductSearchDto searchDto, Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);
    }

    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("product.list.title", null, locale);
    }

    @Override
    public void initScreenEdit(ModelAndView mav, ProductManagementEditDto editDto, Locale locale) {
    	 List<Select2Dto> listProductType = select2DataService.getConstantData("M_PRODUCT", "TYPE", locale.toString());
    	 int nextYear = Year.now().getValue() + 1;
    	 String nextYearStr = String.valueOf(nextYear);
    	 List<Select2Dto> filteredList = listProductType.stream()
    	        .filter(dto -> nextYearStr.equals(dto.getId()))
    	        .collect(Collectors.toList());
    	 mav.addObject("listProductType", filteredList);
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
        return "PRD";
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
        return UserProfileUtils.hasRole("SCREEN#CMS#PAGE_LIST_PRODUCT");
    }

    @Override
    public boolean hasRoleForEdit() {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_PRODUCT_EDIT.concat(CoreConstant.COLON_EDIT));
    }
    @Override
    public boolean hasRoleForDelete() {
        return false;
    }

    @Override
    public boolean hasRoleForDetail() {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_PRODUCT);
    }

    @Override
    public CmsCommonSearchFillterService<ProductSearchDto, ProductLanguageSearchDto, ProductManagementEditDto> getCmsCommonSearchFillterService() {
        return productManagementService;
    }

    @Override
    public void validate(ProductManagementEditDto editDto, BindingResult bindingResult) {

    }

	@Override
	public String getUrlByAlias() {
		return "product";
	}

}
