/*******************************************************************************
 * Class        PositionController
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/0801-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.PositionSearchDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.enumdef.PositionSearchEnum;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.PositionService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.admin.validators.PositionValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.export.util.SearchUtil;
import vn.com.unit.ep2p.utils.ConditionSearchUtils;

/**
 * PositionController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Controller
@RequestMapping(UrlConst.POSITION)
public class PositionController {
	/** positionService */
    @Autowired
    private PositionService positionService;
    
    @Autowired
    private MessageSource msg;
    
    /** PositionValidator */
    @Autowired
    PositionValidator positionValidator;
  
    /** systemLogsService */
    @Autowired
    private SystemLogsService systemLogsService;
    
    @Autowired
	SystemConfig systemConfig;
    
    @Autowired
    CompanyService companyService;
    
    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.POSITION;
    
    /**
     * getDetail
     *
     * @param id
     * @param locale
     * @return
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView getDetail(@RequestParam(value = "id", required = true) Long id, Locale locale) {
        ModelAndView mav = new ModelAndView("/views/position/position-detail.html");        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        JcaPositionDto positionDto = positionService.getPositionDtoById(id);
        
        // Security for data
//        if (null == positionDto || (positionDto.getCompanyId() != null && !UserProfileUtils.hasRoleForCompany(positionDto.getCompanyId()))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
        // set url ajax
        String url = UrlConst.POSITION.concat(UrlConst.DETAIL);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        url = url.substring(1);
        positionDto.setUrl(url); 

        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        mav.addObject("positionDto", positionDto);
        return mav;
    }  
    /**
     *  getEdit
     *
     * @param id
     * @param locale
     * @return ModelAndView
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(@RequestParam(value = "id", required = false) Long id, Locale locale) {
        ModelAndView mav = new ModelAndView("/views/position/position-edit.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        JcaPositionDto positionDto = new JcaPositionDto();
        
        // set url ajax
        String url = UrlConst.POSITION.concat(UrlConst.EDIT);
        if (null != id) {
            positionDto = positionService.getPositionDtoById(id);
            // Security for data
//            if (null == positionDto || !UserProfileUtils.hasRoleForCompany(positionDto.getCompanyId())) {
//                return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//            }
            url = url.concat("?id=").concat(id.toString());
        } else {
        	positionDto.setActived(true);
        	positionDto.setCompanyId(UserProfileUtils.getCompanyId());
        }
        url = url.substring(1);
        positionDto.setUrl(url);
        
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        mav.addObject("positionDto", positionDto);
        return mav;
    }

    /**
     * postEdit
     *
     * @param positionDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return ModelAndView
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "positionDto") JcaPositionDto positionDto, BindingResult bindingResult,
            Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        
    	Long id = positionDto.getPositionId();
    	String positionCode = positionDto.getCode().toUpperCase();
    	positionDto.setCode(positionCode);
        // Write system logs
        if (null == id) {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add Position",
                    "Save Position(code: " + positionCode + ")", request);
        } else {
            systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Edit Position",
                    "Save Position(code: " + positionCode + ")", request);
        }
        
        ModelAndView mav = new ModelAndView("/views/position/position-edit.html"); 
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        //Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        positionValidator.validate(positionDto, bindingResult);        
        
        //Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_FAIL_SAVE, null, locale);
            messageList.add(msgError);

            // Add company list
            List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
            mav.addObject("companyList", companyList);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("positionDto", positionDto);
            return mav;
        }      
        // Update position
        positionService.savePositionDto(positionDto);
        
        String msgInfo = "";        
        if (null != id){
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_UPDATE, null, locale);
        }else{
            msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_CREATE, null, locale);
        }
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        
        String viewName = UrlConst.REDIRECT.concat(UrlConst.POSITION).concat(UrlConst.EDIT); 
        id = positionDto.getPositionId();
        redirectAttributes.addAttribute("id", id);
        mav.setViewName(viewName);        
        
        return mav;
    }

    /**
     * postDelete
     *
     * @param positionId
     * @param locale
     * @param redirectAttributes
     * @param request
     * @return ModelAndView
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(
            @RequestParam(value = "positionId", required = true) Long positionId, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        
        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Delete Position", "Delete Position(id: " + positionId + ")", request);
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        positionService.deletePositionById(positionId);
       
       //Init message list
       MessageList messageList = new MessageList(Message.SUCCESS);
       String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
       messageList.add(msgInfo);
       redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
               
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(UrlConst.POSITION).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);                                    
        return mav;
    }
    
    /**
     * getPostList
     *
     * @param searchDto
     * @param pageSizeParam
     * @param page
     * @param locale
     * @return
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = "searchDto") PositionSearchDto searchDto
    		, @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale, HttpServletRequest request)  {
        ModelAndView mav = new ModelAndView("/views/position/position-list.html" );
        // set url ajax
        String url = UrlConst.POSITION.concat(UrlConst.LIST);
        url = url.substring(1);
        searchDto.setUrl(url);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(PositionSearchEnum.class, mav);
        
        // Add company list
        
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        
        // init page size
        int pageSize = pageSizeParam
                .orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE, UserProfileUtils.getCompanyId()));
        
        // Session Search
        ConditionSearchUtils<PositionSearchDto> searchUtil = new ConditionSearchUtils<PositionSearchDto>();
        String[] urlContains = new String[] { "position/add", "position/edit", "position/detail", "position/list" };
        searchDto = searchUtil.getConditionSearch(this.getClass(), searchDto, urlContains, request, page, pageSize);
        pageSize = Optional.ofNullable(searchDto.getPageSize()).orElse(pageSize);
        page = Optional.ofNullable(searchDto.getPage()).orElse(page);
        
        PageWrapper<JcaPositionDto> pageWrapper = positionService.searchByCondition(page, searchDto, pageSize);
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        mav.addObject("pageUrl", url);
		
        if (StringUtils.isNoneBlank(searchDto.getSearchKeyIds())) {
            List<String> list = new ArrayList<>();
            CollectionUtils.addAll(list, searchDto.getSearchKeyIds().split(","));
            searchDto.setFieldValues(list);
        }
        return mav;
    } 
    /**
     * ajaxList
     *
     * @param searchDto
     * @param page
     * @param pageSizeParam
     * @param locale
     * @return
     * @author KhoaNA
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "searchDto") PositionSearchDto searchDto
    		, @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale, HttpServletRequest request)  {
        ModelAndView mav = new ModelAndView("/views/position/position-table.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        // init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        
        // Init PageWrapper
        PageWrapper<JcaPositionDto> pageWrapper = positionService.searchByCondition(page, searchDto, pageSize);
        
        ConditionSearchUtils<PositionSearchDto> searchUtil = new ConditionSearchUtils<PositionSearchDto>();
        searchUtil.setConditionSearch(request, this.getClass(), searchDto, page, pageSize);
        
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        return mav;
    }
    
    @PostMapping(value = "/get-position-by-company")
    @ResponseBody
    public Object getPositionSelect2(@RequestParam(required = false) String keySearch, @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) boolean isPaging) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = positionService.getSelect2DtoListByTermAndCompanyId(keySearch, companyId, false, isPaging);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
}