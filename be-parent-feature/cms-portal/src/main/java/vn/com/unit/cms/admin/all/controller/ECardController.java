package vn.com.unit.cms.admin.all.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.controller.common.CmsCustomerCommonController;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.ECardService;
import vn.com.unit.cms.core.constant.CmsFunctionCodeConstant;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.constant.CmsTableConstant;
import vn.com.unit.cms.core.module.ecard.dto.ECardEditDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardSearchDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardSearchResultDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;

@Controller
@RequestMapping(value = "/{customerAlias}" + "/e-card")
public class ECardController extends CmsCustomerCommonController<ECardSearchDto, ECardSearchResultDto, ECardEditDto> {

    private static final String VIEW_EDIT = "views/CMS/all/e-card/e-card-edit.html";
    @Autowired
    private MessageSource msg;
    @Autowired
    private Select2DataService select2DataService;

    @Autowired
    private ECardService eCardService;

    @Override
    public void initScreenListSort(ModelAndView mav, ECardSearchDto searchDto, Locale locale) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initScreenEdit(ModelAndView mav, ECardEditDto editDto, Locale locale) {
        List<Select2Dto> ecardTypeList = null;
        if ("AD".equals(UserProfileUtils.getChannel())) {
        	ecardTypeList = select2DataService.getConstantData("E-CARD-AD", "TEMPLATE", locale.toString());
        } else {
        	ecardTypeList = select2DataService.getConstantData("E-CARD", "TEMPLATE", locale.toString());
        }
        mav.addObject("ecardTypeList", ecardTypeList);
    }

    @Override
    public String getFunctionCode() {
        return CmsFunctionCodeConstant.FUNCTION_CODE_ECARD;
    }

    @Override
    public String getTableForGenCode() {
        return CmsTableConstant.TABLE_ECARD;
    }

    @Override
    public String getPrefixCode() {
        return CmsPrefixCodeConstant.PREFIX_CODE_ECARD;
    }

    @Override
    public String viewListSort(String customerAlias) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String viewListSortTable(String customerAlias) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return VIEW_EDIT;
    }

    @Override
    public String getUrlByCustomerAlias(String customerAlias) {
        return customerAlias + "/e-card";
    }

    @Override
    public Class<ECardSearchResultDto> getClassSearchResult() {
        return ECardSearchResultDto.class;
    }

    @Override
    public boolean hasRoleForList(String customerAlias) {
        if (!"personal".equals(customerAlias)) {
            return false;
        }

        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_ECARD);
    }

    @Override
    public boolean hasRoleForEdit(String customerAlias) {
        if (!"personal".equals(customerAlias)) {
            return false;
        }

        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_ECARD_EDIT.concat(CoreConstant.COLON_EDIT));
    }

    @Override
    public boolean hasRoleForListSort(String customerAlias) {
        return false;
    }
    @Override
    public boolean hasRoleForDetail(String customerAlias) {
        if (!"personal".equals(customerAlias)) {
            return false;
        }

        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_ECARD);
    }

    @Override
    public CmsCommonSearchFillterService<ECardSearchDto, ECardSearchResultDto, ECardEditDto> getCmsCommonSearchFillterService() {
        return eCardService;
    }

    @Override
    public void validate(ECardEditDto editDto, BindingResult bindingResult) {
        // TODO Auto-generated method stub

    }
    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("ecard.list.title", null, locale);
    }

}
