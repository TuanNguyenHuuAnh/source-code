package vn.com.unit.cms.admin.all.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.controller.common.CmsCommonsSearchFilterProcessController;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.service.EmulateService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.validator.EmulateValidator;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.emulate.dto.EmulateEditDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchDto;
import vn.com.unit.cms.core.module.emulate.dto.EmulateSearchResultDto;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

@Controller
@RequestMapping("/{customerAlias}" + UrlConst.EMULATE)
public class EmulateController
        extends CmsCommonsSearchFilterProcessController<EmulateSearchDto, EmulateSearchResultDto, EmulateEditDto> {

    private static final String VIEW_LIST = "views/CMS/all/emulate/emulate-list.html";
    
    private static final String VIEW_TABLE = "views/CMS/all/emulate/emulate-table.html";


    private static final String VIEW_EDIT = "views/CMS/all/emulate/emulate-edit.html";

    private static final String VIEW_LIST_SORT = "views/CMS/all/emulate/emulate-list-sort.html";

    private static final String VIEW_LIST_SORT_TABLE = "views/CMS/all/emulate/emulate-table-sort.html";

    private static final String FUNCTION_CODE = "M_EMULATE";

    private static final String FIRST_TASK = "taskSubmit";

    private static final String BUSINESS_CODE = CmsCommonConstant.BUSINESS_CMS;

    @Autowired
    private JcaConstantService jcaConstantService;

    @Autowired
    private EmulateValidator emulateValidator;
    
    @Autowired
    private MessageSource msg;

    @Override
    public void customeBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        super.customeBinder(binder, request, locale);

        SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantCore.FORMAT_DATE_FULL);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);
    }

    @Autowired
    private EmulateService emulateService;

    @Override
    public String getPermisionItem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initScreenList(ModelAndView mav, EmulateSearchDto searchDto, Locale locale, String customerAlias) {
        // TODO Auto-generated method stub

    }
    
    @Override
    public String getTitlePageList(Locale locale) {
        return msg.getMessage("emulate.list.title", null, locale);
    }

    @Override
    public void setParamSearchForListSort(EmulateSearchDto searchDto, Locale locale) {
        super.setParamSearchForListSort(searchDto, locale);
        searchDto.setUsername(UserProfileUtils.getUserNameLogin());
    }

    @Override
    public void initScreenListSort(ModelAndView mav, EmulateSearchDto searchDto, Locale locale) {
        List<EmulateSearchDto> listContestType = emulateService.getType();
        mav.addObject("listContestType", listContestType);
    }
    @Override
    public String getFunctionCode() {
        return FUNCTION_CODE;
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
    public String getUrlByCustomerAlias(String customerAlias) {
        return customerAlias + UrlConst.EMULATE;
    }

    @Override
    public Class<EmulateSearchResultDto> getClassSearchResult() {
        return EmulateSearchResultDto.class;
    }
	public boolean showButtonImport(String customerAlias) {
		return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_EMULATE_IMPORT.concat(ConstantCore.COLON_EDIT));
	}

    @Override
    public boolean hasRoleForList(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_EMULATE);
    }

    @Override
    public boolean hasRoleForEdit(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_EMULATE_EDIT.concat(ConstantCore.COLON_EDIT));
    }
    
    @Override
    public boolean hasRoleSpecialForEdit(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.CMS_ROLE_ADMIN.concat(ConstantCore.COLON_EDIT));
    }

    @Override
    public boolean hasRoleForDetail(String customerAlias) {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_EMULATE);
    }

    @Override
    public CmsCommonSearchFillterService<EmulateSearchDto, EmulateSearchResultDto, EmulateEditDto> getCmsCommonSearchFillterService() {
        return emulateService;
    }

    @Override
    public void validate(EmulateEditDto editDto, BindingResult bindingResult) {
        emulateValidator.validate(editDto, bindingResult);
    }

    @Override
    public DocumentWorkflowCommonService<EmulateEditDto, EmulateEditDto> getService() {
        return emulateService;
    }

    @Override
    public String getControllerURL(String customerAlias) {
        return customerAlias + UrlConst.EMULATE;
    }

    @Override
    public String getBusinessCode(String customerAlias) {
        return BUSINESS_CODE;
    }

    @Override
    public String viewEdit(String customerAlias) {
        return VIEW_EDIT;
    }

    @Override
    public void sendEmailAction(EmulateEditDto editDto, Long buttonId) {

    }

    @Override
    public void sendEmailEdit(EmulateEditDto editDto, Long userUpdated) {

    }

    @Override
    public String viewList(String customerAlias) {
        return VIEW_LIST;
    }
    

    @Override
	public String viewListTable(String customerAlias) {
		return VIEW_TABLE;
	}

	@Override
    public String firstStepInProcess(String customerAlias) {
        return FIRST_TASK;
    }

    @Override
    public String roleForAttachment(String customerAlias) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initDataEdit(ModelAndView mav, String customerAlias, EmulateEditDto editDto, boolean isDetail,
            Locale locale) {
        CmsLanguageUtils.initLanguageList(mav);

        String requestToken = CommonUtil.randomStringWithTimeStamp();
        editDto.setRequestToken(requestToken);
    }

    @Override
    public void customRedirectAttributesForSaveListSort(RedirectAttributes redirectAttributes,
            EmulateSearchDto searchDto, Locale locale) {
        super.customRedirectAttributesForSaveListSort(redirectAttributes, searchDto, locale);

        redirectAttributes.addAttribute("contestType", searchDto.getContestType());
    }
}
