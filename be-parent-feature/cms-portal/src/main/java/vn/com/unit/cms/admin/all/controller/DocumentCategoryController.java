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

import vn.com.unit.cms.admin.all.controller.common.CmsCustomerCommonController;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchDto;
import vn.com.unit.cms.admin.all.dto.DocumentCategorySearchResultDto;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.DocumentCategoryService;
import vn.com.unit.cms.admin.all.validator.DocumentCategoryValidator;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.common.dto.PartnerDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.treebuilder.utils.MenuNode;

@Controller
@RequestMapping(value = "/{customerAlias}" + "/document-category")
public class DocumentCategoryController extends
        CmsCustomerCommonController<DocumentCategorySearchDto, DocumentCategorySearchResultDto, DocumentCategoryEditDto> {

    @Autowired
    private DocumentCategoryValidator categoryValidator;

    @Autowired
    private MessageSource msg;

    @Autowired
    private DocumentCategoryService documentCategoryService;

    @Autowired
    private Select2DataService select2DataService;
    
    @Autowired
    private Db2ApiService db2ApiService;

    private static final String VIEW_EDIT = "views/CMS/all/document-category/document-category-edit.html";

    private static final String VIEW_LIST_SORT = "views/CMS/all/document-category/document-category-list-sort.html";

    private static final String VIEW_TABLE_SORT = "views/CMS/all/document-category/document-category-table-sort.html";

    @Override
    public void initScreenListSort(ModelAndView mav, DocumentCategorySearchDto searchDto, Locale locale) {
        Long rootId = 1L;
        List<MenuNode> listTree = documentCategoryService.getListTree(locale.toString(), rootId, null);
        String listTreeJson = CommonJsonUtil.convertObjectToJsonString(listTree);
        mav.addObject("listTreeJson", listTreeJson);
        
        
    }
    
    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes,
            DocumentCategorySearchDto searchDto, Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

        redirectAttributes.addAttribute("parentId", searchDto.getParentId());
    }

    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("document.category.list.title", null, locale);
    }

    @Override
    public void initScreenEdit(ModelAndView mav, DocumentCategoryEditDto editDto, Locale locale) {
        List<Select2Dto> listItem = select2DataService.getListItem(UserProfileUtils.getCompanyId());
        mav.addObject("listItem", listItem);

        Long rootId = 1L;
        List<MenuNode> listTree = documentCategoryService.getListTree(locale.toString(), rootId, editDto.getId());
        String listTreeJson = CommonJsonUtil.convertObjectToJsonString(listTree);
        mav.addObject("listTreeJson", listTreeJson);

        String kind = "CATEGORY_DOCUMENT";
        if ("AD".equals(UserProfileUtils.getChannel())) {
        	kind = "AD_CATEGORY_DOCUMENT";
        	//Add partner list
            List<PartnerDto> partners = db2ApiService.getListPartnerByChannel("AD");
            mav.addObject("listPartner", partners);
        }
        List<Select2Dto> listDataType = select2DataService.getConstantData("DOCUMENT", kind, locale.toString());
        mav.addObject("listCategoryType", listDataType);
    }

    @Override
    public String getFunctionCode() {
        return "M_DOCUMENT_CATEGORY";
    }

    @Override
    public String getTableForGenCode() {
        return "M_DOCUMENT_CATEGORY";
    }

    @Override
    public String getPrefixCode() {
        return CmsPrefixCodeConstant.PREFIX_CODE_DOCUMENT_CATEGORY;
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
        return customerAlias + "/document-category";
    }

    @Override
    public Class<DocumentCategorySearchResultDto> getClassSearchResult() {
        return DocumentCategorySearchResultDto.class;
    }

    @Override
    public boolean hasRoleForList(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_DOCUMENT_CATEGORY);
    }

    @Override
    public boolean hasRoleForEdit(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_DOCUMENT_CATEGORY_EDIT.concat(CoreConstant.COLON_EDIT));
    }

    @Override
    public boolean hasRoleForDetail(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_DOCUMENT_CATEGORY);
    }

    @Override
    public CmsCommonSearchFillterService<DocumentCategorySearchDto, DocumentCategorySearchResultDto, DocumentCategoryEditDto> getCmsCommonSearchFillterService() {
        return documentCategoryService;
    }

    @Override
    public void validate(DocumentCategoryEditDto editDto, BindingResult bindingResult) {
        categoryValidator.validate(editDto, bindingResult);
    }
}
