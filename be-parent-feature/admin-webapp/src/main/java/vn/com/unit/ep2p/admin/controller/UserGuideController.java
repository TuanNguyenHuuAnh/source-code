/*******************************************************************************
 * Class        ：UserGuideController
 * Created date ：2019/11/12
 * Lasted date  ：2019/11/12
 * Author       ：taitt
 * Change log   ：2019/11/12：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaUserGuideDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.AppLanguageService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.UserGuideService;
import vn.com.unit.ep2p.admin.utils.URLUtil;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.utils.ExecMessage;
import vn.com.unit.ep2p.utils.FileUtil;

/**
 * UserGuideController
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Controller
@RequestMapping(UrlConst.USERGUIDE)
public class UserGuideController {
    
    @Autowired
    private MessageSource msg;
    
    @Autowired
    private UserGuideService userGuideService;
    
    @Autowired
    private AppLanguageService languageService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private JcaConstantService jcaConstantService;

    /** SCREEN_FUNCTION_CODE */
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.USER_GUIDE;
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(UserGuideController.class);
    
    private static final String DOWNLOAD_USER = "/download-user";
    
    private static final String COMPANY_ID = "companyId";
    
    private static final String APP_CODE = "appCode";
    
    private static final String OBJ_USER_GUIDE = "objectDto";
    
//    private static final String LANGUAGE_LIST = "languageList";
    
    private static final String MAV_USER_GUIDE_DETAIL = "/views/user-guide/user-guide-detail.html";
    
//    private static final String MAV_USER_GUIDE_ADD = "user.guide.add";
    
//    private static final String COMPANY_LIST = "companyList";
    
    private static final String ID = "id";
    
    private static final String URL = "url";
    
    private static final String MSG_FLAG = "msgFlag";
    
     /**
      * 
      * downloadPDF
      * @param id
      * @param locale
      * @return
      * @author taitt
      */
    @RequestMapping(value = UrlConst.DOWNLOAD, method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<byte[]> adminDownloadUserGuide(@RequestParam(required=false) Long id, Locale locale) {
        ResponseEntity<byte[]> response = null;

        response = userGuideService.getUserGuideFileById(id);
        
        return response;
    }
    
    @RequestMapping(value =DOWNLOAD_USER, method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<byte[]> userDownloadUserGuide(Locale locale, HttpServletRequest request,HttpServletResponse response) throws IOException {
        ResponseEntity<byte[]> response_ = null;
        
        String appCode = ConstantCore.APP_CODE_WEB;
        
        List<String> listCodeLanguage = new ArrayList<>();
        listCodeLanguage.add(locale.getLanguage().toUpperCase());
        
        Long companyId = UserProfileUtils.getCompanyId();
        String fileName = null;
        List<JcaUserGuideDto> userGLst = userGuideService.getListUserGuideDtoByCompanyIdAndLocale(companyId, listCodeLanguage,appCode);
        if (!userGLst.isEmpty() && null != userGLst){
            Long id  = userGLst.get(0).getId();
            fileName = userGLst.get(0).getFileName();
            response_ = userGuideService.getUserGuideFileById(id);
        }
        if (null != response_){
            byte[] arr = response_.getBody();
//            HttpHeaders header = response_.getHeaders();
//            
//            // get value fileName to header response_ 
//            List<String> listHeader = header.get("Content-Disposition"); 
//            String disposition = listHeader.get(0);
//            String[] dispositionList =  disposition.split(";");
//            String fileName = dispositionList[1];
//            
//            // set value response
//            response.addHeader("Content-Disposition",
//                    "inline; "+ fileName);
//            response.getOutputStream().write(arr);
            
            
            
            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            header.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
            header.add(HttpHeaders.CONTENT_TYPE, FileUtil.getContentType(fileName));
            header.add(HttpHeaders.PRAGMA, "no-cache");
            header.add(HttpHeaders.EXPIRES, "0");

            response_ = new ResponseEntity<>(arr, header, HttpStatus.OK);
        }
        return response_;
    }
    
    @RequestMapping(value = "/validate-download-userguide", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String validateDownloadUserGudie(Locale locale, HttpServletRequest request,HttpServletResponse response) {        
        String msgContent = null;
        
        String appCode = ConstantCore.APP_CODE_WEB;
        
        List<String> listCodeLanguage = new ArrayList<>();
        listCodeLanguage.add(locale.getLanguage().toUpperCase());
        
        Long companyId = UserProfileUtils.getCompanyId();
        List<JcaUserGuideDto> userGLst = userGuideService.getListUserGuideDtoByCompanyIdAndLocale(companyId, listCodeLanguage,appCode);
        if (!userGLst.isEmpty() && null != userGLst){
            response.setHeader(MSG_FLAG, ConstantCore.STR_ONE);
        }else {
            msgContent =  msg.getMessage(ConstantCore.MSG_NOT_FOUND_USER_GUIDE, null, locale);
            response.setHeader(MSG_FLAG, ConstantCore.STR_ZERO);
        }
        
        return msgContent;
    }
    
    
    @RequestMapping(value = UrlConst.DETAIL, method = { RequestMethod.GET })
    public ModelAndView getList(@RequestParam(value = COMPANY_ID,required = false) Long companyId,@RequestParam(value = APP_CODE,required = false) String appCode,Locale locale) {

     // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        JcaUserGuideDto   userGuideDto = new JcaUserGuideDto();
        ModelAndView mav = new ModelAndView(MAV_USER_GUIDE_DETAIL);  
        // URL ajax redirect
        StringBuilder urlRedirect = new StringBuilder(UrlConst.USERGUIDE.substring(1).concat(UrlConst.DETAIL));
        List<JcaUserGuideDto> listUserProfileLang = userGuideService.getListUserGuideDto();
        userGuideDto.setListUserGuilde(listUserProfileLang);
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        //add languageList
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject("languageList", languageList);
        
        
        //add languageList
        List<JcaConstantDto> systemList = jcaConstantService.getListJcaConstantDtoByKind(ConstantDisplayType.SYSTEM_APP_TYPE.toString(),locale.toString());
        mav.addObject("systemList", systemList);
        
        
      //add list categorylanguage
        List<JcaUserGuideDto> categoryLanguageDtos = new ArrayList<>();
        List<JcaUserGuideDto> categoryLanguageDtosTmp = new ArrayList<>();
        if (null != companyId){

            List<String> listCodeLanguage = languageList.stream().map(lang -> lang.getCode()).sorted().collect(Collectors.toList());
            categoryLanguageDtosTmp = userGuideService.getListUserGuideDtoByCompanyIdAndLocale(companyId, listCodeLanguage,appCode);
            
        }else {
            companyId =Long.parseLong(companyList.get(0).getId());
            appCode = systemList.get(0).getCode();
            categoryLanguageDtosTmp = userGuideService.getListUserGuideDtoByCompanyIdAndLocale(companyId, null,appCode);
        }

        // Build language for category when edit. listMenuLanguageTmp not empty.            
        for (LanguageDto languageDto : languageList) {
            boolean checkExist = false;
            for (JcaUserGuideDto categoryLanguageDto : categoryLanguageDtosTmp) {
                if (languageDto.getCode().equals(categoryLanguageDto.getLangCode())) {
                    JcaUserGuideDto newCategoryLanguageDto = new JcaUserGuideDto();
                    newCategoryLanguageDto.setId(categoryLanguageDto.getId());
                    newCategoryLanguageDto.setLangId(categoryLanguageDto.getLangId());
                    newCategoryLanguageDto.setLangCode(categoryLanguageDto.getLangCode());
                    newCategoryLanguageDto.setCompanyId(categoryLanguageDto.getCompanyId());
                    newCategoryLanguageDto.setFileName(categoryLanguageDto.getFileName());
                    newCategoryLanguageDto.setFilePath(categoryLanguageDto.getFilePath());
                    newCategoryLanguageDto.setAppCode(categoryLanguageDto.getAppCode());
                    categoryLanguageDtos.add(newCategoryLanguageDto);
                    checkExist = true;
                    break;
                }
            }
            if (!checkExist) {
                JcaUserGuideDto newCategoryLanguageDto = new JcaUserGuideDto();
                newCategoryLanguageDto.setLangCode(languageDto.getCode());
                categoryLanguageDtos.add(newCategoryLanguageDto);
            }
        }
        boolean isFirstParam = true;
        if( companyId != null ) {
            urlRedirect.append(URLUtil.buildParamWithPrefix(isFirstParam, COMPANY_ID, companyId));
        }
        userGuideDto.setListUserGuilde(categoryLanguageDtos);;
        mav.addObject(URL, urlRedirect.toString());
        userGuideDto.setCompanyId(companyId);
        userGuideDto.setAppCode(appCode);
        mav.addObject(OBJ_USER_GUIDE, userGuideDto);
        return mav;
    }
    
    
    @RequestMapping(value = "/add", method = { RequestMethod.POST})
    public ModelAndView addUserGuide(@ModelAttribute(value = OBJ_USER_GUIDE) JcaUserGuideDto userGuideDto,
            BindingResult bindingResult,
            @RequestParam(value = "url", required = false) String urlRedirect, Locale locale,
            RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        
        ModelAndView mav = new ModelAndView(MAV_USER_GUIDE_DETAIL);
        // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        String msgContent = null;
        if (userGuideDto == null)
            userGuideDto = new JcaUserGuideDto();
//        Long companyId = userGuideDto.getCompanyId();
        initDataForEditScreen(mav,userGuideDto.getCompanyId(),userGuideDto.getAppCode(),locale);
        try{
            // save userGuide
            userGuideService.saveUserGuide(userGuideDto,locale);
            msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, locale);
        }catch(Exception e){
            logger.error("Error: ", e);
            messageList.add(e.getMessage());
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.MESSAGE_NOT_HAVE_REPOSITORY, null, locale);
            messageList.add(msgError);
//            this.initDataForEditScreen(mav,companyId);
            mav.addObject(ConstantCore.MSG_LIST, messageList);
//            redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
//            redirectAttributes.addAttribute(COMPANY_ID, companyId);
//            // Redirect
//            mav.setViewName(UrlConst.REDIRECT.concat(UrlConst.USERGUIDE).concat(UrlConst.DETAIL));

            return mav;
        }
        messageList.add(msgContent);
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        return mav;
    }
    
    private void initDataForEditScreen(ModelAndView mav, Long companyId,String appcode,Locale locale) {
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        //add languageList
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject("languageList", languageList);
        
        List<JcaConstantDto> systemList = jcaConstantService.getListJcaConstantDtoByKind(ConstantDisplayType.SYSTEM_APP_TYPE.toString(),locale.toString());
        mav.addObject("systemList", systemList);
    }
    
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST})
    public ModelAndView deleteUserGuide(@RequestParam(required=false) Long companyId,@RequestParam(value =  ID,required = false) Long id, Locale locale, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        
     // Init message list
        MessageList messageList = new MessageList(Message.SUCCESS);
        boolean deleteFlg = false;
        String msgContent = null;
        try{
            // save userGuide
            userGuideService.deleteUserGuide(id);
        } catch (AppException appEx) {
            logger.error("Error: ", appEx);
            messageList.setStatus(Message.ERROR);
            msgContent = ExecMessage.getErrorMessage(msg, appEx.getCode(), appEx.getArgs(), locale).getErrorDesc();
        } catch (Exception e) {
            logger.error(e.getMessage());
            // Add message error
            messageList.setStatus(Message.ERROR);
            msgContent = msg.getMessage(ConstantCore.MSG_ERROR_DELETE, null, locale);
        }
        // Check delete
        if (deleteFlg) {
            // Add message success
            msgContent = msg.getMessage(ConstantCore.MSG_SUCCESS_DELETE, null, locale);
        }
        
        messageList.add(msgContent);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute(COMPANY_ID, companyId);

        // Redirect
        String viewName = UrlConst.REDIRECT.concat(UrlConst.USERGUIDE).concat(UrlConst.DETAIL);
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }
}
