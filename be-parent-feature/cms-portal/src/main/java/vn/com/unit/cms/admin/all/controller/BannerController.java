/*******************************************************************************
 * Class        ：BannerController
 * Created date ：2017/02/15
 * Lasted date  ：2017/02/15
 * Author       ：TaiTM
 * Change log   ：2017/02/15：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.controller.common.CmsCommonController;
import vn.com.unit.cms.admin.all.dto.BannerEditDto;
import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.validator.BannerEditValidator;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageSearchDto;
import vn.com.unit.cms.core.module.banner.dto.BannerSearchDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
//import vn.com.unit.binding.DoubleEditor;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.core.config.SystemConfig;
//import vn.com.unit.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
//import vn.com.unit.jcanary.constant.UrlConst;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;

/**
 * 
 * BannerController
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Controller
@RequestMapping(value = UrlConst.ROOT + UrlConst.BANNER)
public class BannerController extends CmsCommonController<BannerSearchDto, BannerLanguageSearchDto, BannerEditDto> {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BannerEditValidator bannerEditValidator;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private Select2DataService select2DataService;
    
    @Autowired
    private JcaConstantService jcaConstantService;

    private static final String VIEW_LIST = "views/CMS/all/banner/banner-list.html";
    private static final String VIEW_TABLE = "views/CMS/all/banner/banner-table.html";
    private static final String VIEW_EDIT = "views/CMS/all/banner/banner-edit.html";

    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        if (null == request.getSession().getAttribute(ConstantCore.FORMAT_DATE)) {
            request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
        }
        // The date format to parse or output your dates
        String patternDate = ConstantCore.FORMAT_DATE_FULL;
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }

    private void inItScreenEdit(ModelAndView mav, BannerEditDto bannerEdit, Locale locale) {
        CmsLanguageUtils.initLanguageList(mav);

        List<Select2Dto> listDataType = select2DataService.getConstantData("M_BANNER", "TYPE", locale.toString());
        mav.addObject("listDataType", listDataType);

        List<Select2Dto> listDataDevice = select2DataService.getConstantData("M_BANNER", "DEVICE", locale.toString());
        mav.addObject("listDataDevice", listDataDevice);

        // check statusCode published
        boolean checkPublishedAndHaveData = false;
        mav.addObject("checkPublishedAndHaveData", checkPublishedAndHaveData);
        
        List<JcaConstantDto> channels = jcaConstantService.getListJcaConstantDisplayByType("CHANNEL");
        mav.addObject("channels", channels);
    }

    @Override
    public void initScreenEdit(ModelAndView mav, BannerEditDto editDto, Locale locale) {
        inItScreenEdit(mav, editDto, locale);
    }

    @Override
    public String getFunctionCode() {
        return "BANNER";
    }

    @Override
    public String getTableForGenCode() {
        return "M_BANNER";
    }

    @Override
    public String getPrefixCode() {
        return CmsPrefixCodeConstant.PREFIX_CODE_BANNER;
    }

    @Override
    public String viewList() {
        return VIEW_LIST;
    }

    @Override
    public String viewListTable() {
        return VIEW_TABLE;
    }

    @Override
    public String viewEdit() {
        return VIEW_EDIT;
    }

    @Override
    public String getUrlByAlias() {
        return UrlConst.BANNER;
    }

    @Override
    public Class<BannerLanguageSearchDto> getClassSearchResult() {
        return BannerLanguageSearchDto.class;
    }

    @Override
    public boolean hasRoleForList() {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_BANNER);
    }

    @Override
    public boolean hasRoleForEdit() {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_BANNER_EDIT.concat(CoreConstant.COLON_EDIT));
    }

    @Override
    public boolean hasRoleForDetail() {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_BANNER);
    }

    @Override
    public CmsCommonSearchFillterService<BannerSearchDto, BannerLanguageSearchDto, BannerEditDto> getCmsCommonSearchFillterService() {
        return bannerService;
    }

    @Override
    public void validate(BannerEditDto editDto, BindingResult bindingResult) {
        bannerEditValidator.validate(editDto, bindingResult);
    }

    @Override
    public String viewListSort() {
        return null;
    }

    @Override
    public String viewListSortTable() {
        return null;
    }
}