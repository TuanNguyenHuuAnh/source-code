package vn.com.unit.cms.admin.all.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.controller.common.CmsCommonController;
import vn.com.unit.cms.admin.all.db2.service.Db2Service;
import vn.com.unit.cms.admin.all.dto.BranchEditDto;
import vn.com.unit.cms.admin.all.dto.BranchSearchDto;
import vn.com.unit.cms.admin.all.dto.BranchSearchResultDto;
import vn.com.unit.cms.admin.all.service.CmsBranchService;
import vn.com.unit.cms.admin.all.service.CmsCommonSearchFillterService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.validator.BranchEditValidator;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonJsonUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;

@Controller
@RequestMapping(UrlConst.ROOT + UrlConst.BRANCH)
public class CmsBranchController extends CmsCommonController<BranchSearchDto, BranchSearchResultDto, BranchEditDto> {

    @Autowired
    private CmsBranchService branchService;

    @Autowired
    private Select2DataService select2DataService;

    @Autowired
    private BranchEditValidator branchEditValidator;

    @Autowired
    private Db2Service db2Service;

    @Autowired
    private MessageSource msg;

    private static final String VIEW_EDIT = "views/CMS/all/branch/branch-edit.html";

    private static final String REGION_CHANGE = "region/change";

    private static final Logger logger = LoggerFactory.getLogger(FaqsCategoryController.class);

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void initScreenListSort(ModelAndView mav, BranchSearchDto searchDto, Locale locale) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initScreenEdit(ModelAndView mav, BranchEditDto editDto, Locale locale) {
        CmsLanguageUtils.initLanguageList(mav);

        List<Select2Dto> listDataType = select2DataService.getConstantData("BRANCH_TYPE", "BRANCH_TYPE", locale.toString());
        mav.addObject("listDataType", listDataType);

//        List<Select2Dto> listDataRegion = branchService.getAllRegion(locale.toString());
//        mav.addObject("listDataRegion", listDataRegion);

            //List<Select2Dto> listDataCity = branchService.findByRegionAndLanguageCode( locale.toString());
            List<Select2Dto> listDataCity = db2Service.getCity();
            mav.addObject("listDataCity", listDataCity);

            //List<Select2Dto> listDataDistrict = branchService.getAlldistrict(editDto.getCity(), locale.toString());
            List<Select2Dto> listDataDistrict = db2Service.getDistrictByCity(editDto.getCity(),null);
            mav.addObject("listDataDistrict", listDataDistrict);



    }

    @Override
    public String getFunctionCode() {
        return "M_BRANCH";
    }

    @Override
    public String getTableForGenCode() {
        // TODO Auto-generated method stub
        return "JCA_M_BRANCH";
    }

    @Override
    public String getPrefixCode() {
        return CmsPrefixCodeConstant.PREFIX_CODE_BRANCH;
    }

    @Override
    public String viewListSort() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String viewListSortTable() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String viewEdit() {

        return VIEW_EDIT;
    }

    @Override
    public String getUrlByAlias() {
        return UrlConst.BRANCH;
    }

    @Override
    public boolean hasRoleForList() {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_LIST_BRANCH);
    }

    @Override
    public boolean hasRoleForEdit() {
        return UserProfileUtils.hasRole(CmsRoleConstant.BUTTON_BRANCH_EDIT.concat(CoreConstant.COLON_EDIT));
    }

    @Override
    public boolean hasRoleForDetail() {
        return UserProfileUtils.hasRole(CmsRoleConstant.PAGE_DETAIL_BRANCH.concat(CoreConstant.COLON_EDIT));
    }

    @Override
    public CmsCommonSearchFillterService<BranchSearchDto, BranchSearchResultDto, BranchEditDto> getCmsCommonSearchFillterService() {
        return branchService;
    }

    @Override
    public void validate(BranchEditDto editDto, BindingResult bindingResult) {
        branchEditValidator.validate(editDto, bindingResult);

    }

    @Override
    public Class<BranchSearchResultDto> getClassSearchResult() {
        return BranchSearchResultDto.class;
    }

    @Override
    public boolean hasRoleForListSort() {

        return false;
    }

    @RequestMapping(value = REGION_CHANGE, method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String ajaxGetCategorySelect(@RequestParam(value = "city", required = true) String city, Locale locale) {
        //List<Select2Dto> listData = branchService.findByRegionAndLanguageCode(locale.toString());
        //List<Select2Dto> listDataDistrict = branchService.getAlldistrict(city);
        List<Select2Dto> listDataDistrict = db2Service.getDistrictByCity(city,null);

        return CommonJsonUtil.convertObjectToJsonString(listDataDistrict);
    }

    @Override
    public String getTitlePageList(Locale locale) {

        return msg.getMessage("branch.title", null, locale);
    }


}