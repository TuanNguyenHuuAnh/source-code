/*******************************************************************************
 * Class        ：ConstantController
 * Created date ：2017/10/17
 * Lasted date  ：2017/10/17
 * Author       ：TranLTH
 * Change log   ：2017/10/17：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.cms.admin.all.constant.AdminUrlConst;
import vn.com.unit.cms.admin.all.dto.ConstantDto;
import vn.com.unit.cms.admin.all.service.ConstantService;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
import vn.com.unit.ep2p.constant.UrlConst;

/**
 * ConstantController
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Controller
@RequestMapping(AdminUrlConst.CONSTANT_TYPE_ROOT)
public class ConstantController {
    /** ConstantService */
    @Autowired
    private ConstantService constantService;
    
    /** MessageSource */
    @Autowired
    private MessageSource msg;
    
    @Autowired
    private SystemConfig systemConfig;
    
    private static final String CONSTANT_LIST = "hdb.admin.constant.list";
    
    private static final String CONSTANT_EDIT = "hdb.admin.constant.edit";
    
    private static final String CONSTANT_DETAIL = "hdb.admin.constant.detail";
    
    private static final String CONSTANT_TABLE = "constant.table";
    /**
     * dateBinder
     *
     * @param binder
     * @param request
     * @param locale
     * @author TranLTH
     */
    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
        
        request.getSession().setAttribute("formatDate",systemConfig.getConfig(SystemConfig.DATE_PATTERN));
        // The date format to parse or output your dates
        String patternDate = systemConfig.getConfig(SystemConfig.DATE_PATTERN);
        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDate);
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, ConstantCore.PATTERN_CURRENCY));
    }
    /**
     * getPostList
     *
     * @param constantDto
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.LIST, method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getPostList(@ModelAttribute(value = "constantDto") ConstantDto constantDto,
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
        // set url ajax
        constantDto.setUrl(AdminUrlConst.CONSTANT_TYPE.concat(UrlConst.LIST));
        
        ModelAndView mav = new ModelAndView(CONSTANT_LIST);

        // set language code
        constantDto.setLanguageCode(locale.getLanguage());
        
        // Init master data          
        constantService.initScreenConstantList(mav);                
        
        // Init PageWrapper
        PageWrapper<ConstantDto> pageWrapper = constantService.search(page, constantDto);
        
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }        
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);    
        
        mav.addObject("constantDto", constantDto);

        return mav;
    }
    /**
     * ajaxList
     *
     * @param constantDto
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.AJAXLIST, method = { RequestMethod.POST })
    @ResponseBody
    public ModelAndView ajaxList(@ModelAttribute(value = "constantDto") ConstantDto constantDto
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page, Locale locale){
        ModelAndView mav = new ModelAndView(CONSTANT_TABLE);
        
        // set language code
        constantDto.setLanguageCode(locale.getLanguage());
        
        // Init PageWrapper
        PageWrapper<ConstantDto> pageWrapper = constantService.search(page, constantDto);
        
        if( pageWrapper.getCountAll() == 0 ) {
            // Init message list
            MessageList messageList = new MessageList(Message.INFO);
            String msgInfo = msg.getMessage(ConstantCore.MSG_INFO_SEARCH_NO_DATA, null, locale);
            messageList.add(msgInfo);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
        }        
        mav.addObject(ConstantCore.PAGE_WRAPPER, pageWrapper);    
        
        mav.addObject("constantDto", constantDto);

        return mav;
    }
    /**
     * getEdit
     *
     * @param id
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.GET)
    public ModelAndView getEdit(
        @RequestParam(value = "id", required = false) Long id) {
        ModelAndView mav = new ModelAndView(CONSTANT_EDIT);        
        
        ConstantDto constantDto = constantService.getConstantDto(id);        
                
        // set url ajax
        String url = AdminUrlConst.CONSTANT_TYPE.concat(UrlConst.EDIT);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        constantDto.setUrl(url);
        
        mav.addObject("constantDto", constantDto);
        
        // Init master data          
        constantService.initScreenConstantList(mav);
        return mav;
    }
    /**
     * postEdit
     *
     * @param constantDto
     * @param bindingResult
     * @param locale
     * @param redirectAttributes
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.EDIT, method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView postEdit(@Valid @ModelAttribute(value = "constantDto") ConstantDto constantDto,
            BindingResult bindingResult, Locale locale, RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView(CONSTANT_EDIT);
        
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        
        // Validation
        if (bindingResult.hasErrors()) {
            // Add message error
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MSG_ERROR_UPDATE, null, locale);
            messageList.add(msgError);
            
            // Init master data          
            constantService.initScreenConstantList(mav);
            
            mav.addObject(ConstantCore.MSG_LIST, messageList);
            mav.addObject("constantDto", constantDto);
            return mav;
        }        
               
        // Update item           
        constantService.addOrEditConstant(constantDto);                       
        
        String msgInfo = StringUtils.EMPTY;
        String viewName = StringUtils.EMPTY;
        msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
        // success redirect edit page
        viewName = UrlConst.REDIRECT.concat(AdminUrlConst.CONSTANT_TYPE_ROOT).concat(UrlConst.EDIT);
        
        messageList.add(msgInfo);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addAttribute("id", constantDto.getId());
        
        mav.setViewName(viewName);        
        
        return mav;
    }
    /**
     * postDelete
     *
     * @param constantId
     * @param locale
     * @param redirectAttributes
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DELETE, method = RequestMethod.POST)
    public ModelAndView postDelete(@RequestParam(value = "constantId", required = true) Long constantId, Locale locale,
            RedirectAttributes redirectAttributes) {         
        
       constantService.delete(constantId);
             
       //Init message list
       MessageList messageList = new MessageList(Message.SUCCESS);
       String msgInfo = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
       messageList.add(msgInfo);
       redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
       
        // Delete success redirect list page
        String viewName = UrlConst.REDIRECT.concat(AdminUrlConst.CONSTANT_TYPE_ROOT).concat(UrlConst.LIST);
        ModelAndView mav = new ModelAndView(viewName);               
        return mav;
    }
    /**
     * getDetail
     *
     * @param id
     * @param page
     * @param locale
     * @return
     * @author TranLTH
     */
    @RequestMapping(value = UrlConst.DETAIL, method = RequestMethod.GET)
    public ModelAndView getDetail(@RequestParam(value = "id", required = true) Long id, 
            @RequestParam(value = ConstantCore.PAGE, required = false, defaultValue = "1") int page, Locale locale) {
        ModelAndView mav = new ModelAndView(CONSTANT_DETAIL);        
        
        ConstantDto constantDto = new ConstantDto();
        if (id != null){
            constantDto = constantService.getConstantDto(id);
        } 
        // set url ajax
        String url = AdminUrlConst.CONSTANT_TYPE.concat(UrlConst.DETAIL);
        if (null != id) {
            url = url.concat("?id=").concat(id.toString());
        }
        constantDto.setUrl(url);
        
        mav.addObject("constantDto", constantDto);        
        // Init master data          
        constantService.initScreenConstantList(mav);
        return mav;
    }
}