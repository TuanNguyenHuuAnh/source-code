/*******************************************************************************
 * Class        PositionAuthorityController
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaPositionAuthorityDto;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.dto.JcaRoleForPositionDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.PositionService;
import vn.com.unit.ep2p.admin.service.RoleForPositionService;
import vn.com.unit.ep2p.admin.service.SystemLogsService;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * PositionAuthorityController
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Controller
@RequestMapping(UrlConst.POSITION_AUTHORITY)
public class PositionAuthorityController {
	
	/** PositionService */
    @Autowired
    private PositionService positionService;
    
    /** PositionAuthorityService */
    @Autowired
    private RoleForPositionService roleForPositionService;
    
    @Autowired
    private CompanyService companyService;
    
	/** MessageSource */
    @Autowired
    private MessageSource msg;
    
    /** systemLogsService */
    @Autowired
    private SystemLogsService systemLogsService;
    
    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.POSITION_AUTHORITY;
    
    /**
     * Screen list authority (Init)
     * @return ModelAndView
     * @author KhoaNA
     */
    @GetMapping(value = UrlConst.LIST)
    public ModelAndView getList(HttpServletRequest request, Locale locale) {
        ModelAndView mav = new ModelAndView("/views/position-authority/position-authority-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        Long companyId = UserProfileUtils.getCompanyId();
        mav.addObject("companyId", companyId);
        
        return mav;
    }
    
    /**
     * List Position (Ajax)
     * @return ModelAndView
     * @author trieuvd
     */
    @GetMapping(value = UrlConst.AJAX_LIST)
    public ModelAndView getListPosition(HttpServletRequest request, Locale locale,
    		@RequestParam(value = "companyId", required = true) Long companyId) {
        ModelAndView mav = new ModelAndView("/views/position-authority/position-iauthority-list.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        List<JcaPositionDto> positionDtoList = positionService.findPositionDtoByCompany(companyId);
        
        Long positionIdSelected = null;
        if( positionDtoList.isEmpty() ) {
            MessageList messageList = new MessageList(Message.WARNING);
            String content = msg.getMessage("message.warning.position.authority.not.data", null, locale);
            messageList.add(content);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        } else {
        	JcaPositionDto positionDtoFirst = positionDtoList.get(0);
            positionIdSelected = positionDtoFirst.getPositionId();
        }
        
        mav.addObject("positionDtoList", positionDtoList);
        mav.addObject("positionIdSelected", positionIdSelected);
        return mav;
    }
    
    /**
     * Screen edit authority (Ajax-Init)
     * @return String
     * @author KhoaNA
     */
    @GetMapping(value = UrlConst.AJAX_EDIT)
    @ResponseBody
    public ModelAndView getAjaxEdit(
            @RequestParam(value = "positionId", required = false) Long positionId) {
        ModelAndView mav = new ModelAndView("/views/position-authority/position-iauthority-edit.html");
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        List<JcaRoleForPositionDto> roleForPositionDtoList = new ArrayList<>();
        if(positionId!=null)
        	roleForPositionDtoList = roleForPositionService.getRoleForPositionDtoListByPositionId(positionId);
        mav.addObject("positionIdSelected", positionId);
        mav.addObject("roleForPositionDtoList", roleForPositionDtoList);
        return mav;
    }
    
    /**
     * Screen edit authority (Ajax-Edit)
     * @return String
     * @author KhoaNA
     */
    @PostMapping(value = UrlConst.AJAX_EDIT)
    @ResponseBody
    public ModelAndView postAjaxEdit(@RequestBody JcaPositionAuthorityDto positionAuthorityDto, Locale locale, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/views/commons/message-alert.html");
        // Write system logs
        systemLogsService.writeSystemLogs(SCREEN_FUNCTION_CODE, "Save Add Position to Role", "Save Add Position to Role", request);
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgStr = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
        messageList.add(msgStr);
        
        // Save roleForPosition list to positionAuthorityDto
        roleForPositionService.savePositionAuthorityDto(positionAuthorityDto);
        
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }
}
