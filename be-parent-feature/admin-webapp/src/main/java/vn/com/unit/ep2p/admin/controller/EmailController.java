/*******************************************************************************
 * Class        EmailController
 * Created date 2018/08/15
 * Lasted date  2018/08/15
 * Author       phatvt
 * Change log   2018/08/1501-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.controller;

import java.awt.PageAttributes.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.ConverterTypeVia;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.FileResultDto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaEmailDto;
import vn.com.unit.core.entity.JcaAttachFileEmail;
import vn.com.unit.core.entity.JcaEmailTemplate;
//import vn.com.unit.core.enumdef.EmailStatusEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.RoleConstant;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.admin.dto.FileUploadResponseDto;
import vn.com.unit.ep2p.admin.dto.ResponseEmailDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
//import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.ep2p.admin.service.AppEmailService;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.TemplateService;
import vn.com.unit.ep2p.constant.CommonConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.EmailDto;
import vn.com.unit.ep2p.dto.EmailSearchDto;
import vn.com.unit.ep2p.enumdef.EmailSearchEnum;
import vn.com.unit.ep2p.export.util.SearchUtil;

/**
 * EmailController
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Controller
@RequestMapping(UrlConst.EMAIL)
public class EmailController {

    @Autowired
    private AppEmailService emailService;
    
    @Autowired
    private MessageSource msg;
    
    @Autowired
    private TemplateService templateService;
    
    @Autowired
    private JcaConstantService jcaConstantService;
    
    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    private CommonService comService;
    
    @Autowired
    CompanyService companyService;
    
    @Autowired
    private JRepositoryService jrepositoryService;
    
    
    private static final String EMAIL_VIEW = "/views/email/email-send.html";
    
//    private static final String MESSAGE_VIEW = "/views/commons/message-alert.html";
    
    private static final String EMAIL_LIST = "/views/email/email-list.html";
    
    private static final String EMAIL_TABLE = "/views/email/email-table.html";
    
    private static final String EMAIL_DETAIL = "/views/email/email-detail.html";
    
    /** SCREEN_FUNCTION_CODE */
    //function send mail
    private static final String SCREEN_FUNCTION_CODE = RoleConstant.SEND_MAIL;
    //function email management
    private static final String ROLE_MAIL_MANAGEMENT = RoleConstant.EMAIL_MANAGEMENT;
    
    @InitBinder
    public void dateBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        request.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
        // The date format to parse or output your dates
        String patternDate = (String) request.getSession().getAttribute(ConstantCore.FORMAT_DATE);
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
     * @param emailDto
     * @param locale
     * @return
     * @author phatvt
     */
    @GetMapping(value = UrlConst.LIST)
    public ModelAndView getPostList(@ModelAttribute(value = "emailDto") EmailDto emailDto, Locale locale)  {
        
        // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(EMAIL_VIEW);
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        mav.addObject("companyId", UserProfileUtils.getCompanyId());
        //uuid email
        UUID uUid = UUID.randomUUID();
        emailDto.setUuidEmail(uUid.toString());
        mav.addObject("emailDto", emailDto);
        mav.addObject("listExt", ConstantCore.EXT_ATTACH_FILE_LIST);
        mav.addObject("fileSize", ConstantCore.ATTACH_FILE_SIZE_BYTE);
        String [] errorArgs = new String[1];
        errorArgs[0] = ConstantCore.ATTACH_FILE_SIZE.toString();
        String error = msg.getMessage(ConstantCore.ATTACH_FILE_BIG, errorArgs, locale);
        mav.addObject("totalError", error);
        return mav;
    } 
    /**
     * sendMail
     *
     * @param emailDto
     * @param locale
     * @return
     * @author phatvt
     */
    @PostMapping(value = UrlConst.SEND_MAIL)
    @ResponseBody
    public ModelAndView sendMail(@ModelAttribute("emailDto") EmailDto emailDto, Locale locale, RedirectAttributes redirectAttributes)  {
        
        // Security for this page.
//        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))) {
//            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
//        }
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}
        
        ModelAndView mav = new ModelAndView(EMAIL_VIEW);
        /*EmailDto email = new EmailDto();
        email.setReceiveAddress(emailDto.);
        email.setSubject("TEST SUBJECT 123");
        email.setEmailContent("NOI 123123123");*/
        if(emailDto.isFlagSendImmediately()){
            //JcaConstantDto constDirSave = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind( CommonConstant.SEND_DIRECT_SAVE, ConstantDisplayType.SLA_SEND_TYPE.name(), ConstantDisplayType.SLA_SEND_TYPE.name(), UserProfileUtils.getLanguage());                       
            JcaConstantDto constDirSave = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind( CommonConstant.SEND_DIRECT_SAVE, CommonConstant.GROUP_JCA_APP_SLA, CommonConstant.KIND_SLA_SEND_TYPE, UserProfileUtils.getLanguage());
            emailDto.setSendEmailType(constDirSave.getCode());
        }else{
            emailDto.setSendEmailType(null);
        }
        ResponseEmailDto response = emailService.sendEmail(emailDto);
        MessageList messageList = new MessageList();
        if(response.getStatus().equals(CommonConstant.EMAIL_SEND_SUCCESS)){
            messageList.setStatus(Message.SUCCESS);
            String msgError = msg.getMessage(ConstantCore.SEND_MAIL_SUCCESS, null, locale);
            messageList.add(msgError);
        }else{
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.SEND_MAIL_FAIL, null, locale);
            messageList.add(msgError);
        }
        mav.addObject(ConstantCore.MSG_LIST, messageList);
        redirectAttributes.addFlashAttribute(ConstantCore.MSG_LIST, messageList);
        String viewName = UrlConst.REDIRECT.concat(UrlConst.EMAIL).concat(UrlConst.LIST);
        mav.setViewName(viewName);
        return mav;
    } 
    /**
     * importTemplate
     *
     * @param file
     * @param locale
     * @param redirectAttributes
     * @return
     * @author phatvt
     */
    @PostMapping(value = UrlConst.IMPORT)
    public ModelAndView importTemplate(@RequestParam("file") MultipartFile file, @RequestParam("companyId") Long companyId, Locale locale,
            RedirectAttributes redirectAttributes)  {
        
     // Security for this page.
        if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE.concat(ConstantCore.COLON_DISP))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        
        ModelAndView mav = new ModelAndView(EMAIL_VIEW);
        MessageList messageList = new MessageList();
        try {
            String viewName = UrlConst.REDIRECT.concat("/email").concat("/list");
            mav.setViewName(viewName);
            if(file.isEmpty()){
                messageList.setStatus(Message.ERROR);
                String msgError = msg.getMessage(ConstantCore.IMPORT_FILE_EMPTY, null, locale);
                messageList.add(msgError);
                redirectAttributes.addFlashAttribute("messageList",messageList);
                return mav;
            }
            if(!file.getOriginalFilename().endsWith("docx") && !file.getOriginalFilename().endsWith("doc") && !file.getOriginalFilename().endsWith("html")){
                messageList.setStatus(Message.ERROR);
                String msgError = msg.getMessage(ConstantCore.IMPORT_FILE_WORD, null, locale);
                messageList.add(msgError);
                redirectAttributes.addFlashAttribute("messageList",messageList);
                return mav;
            }
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            //Path path = Paths.get(CommonConstant.UPLOADED_FOLDER_TEAMPLATE + file.getOriginalFilename());
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            //save to table template
            JcaEmailTemplate template = new JcaEmailTemplate();
            template.setName(file.getOriginalFilename());
//            if(file.getOriginalFilename().endsWith("docx") || file.getOriginalFilename().endsWith("doc")){
//                template.setFileFormat(CommonConstant.FILE_FORMAT_WORD);
//            }else if(file.getOriginalFilename().endsWith("html")){
//                template.setFileFormat(CommonConstant.FILE_FORMAT_HTML);
//            }
//            
//            template.setCreatedBy(UserProfileUtils.getUserNameLogin());
            template.setCreatedDate(comService.getSystemDateTime());
            template.setCompanyId(companyId);
            //template.setEnable(1);
            templateService.saveTemplate(template);

            messageList.setStatus(Message.SUCCESS);
            String msgError = msg.getMessage(ConstantCore.MSG_SUCCESS_IMPORT, null, locale);
            messageList.add(msgError);
            
            redirectAttributes.addFlashAttribute("messageList",messageList);
            return mav;
            
        } catch(IOException ex){
            messageList.setStatus(Message.ERROR);
            String msgError = msg.getMessage(ConstantCore.IMPORT_FILE_FAIL, null, locale);
            messageList.add(msgError);
            
            redirectAttributes.addFlashAttribute("messageList",messageList);
            return mav;
        }
    } 
    
    /**
     * select2GetTemplate
     *
     * @param term
     * @return
     * @author phatvt
     */
    @PostMapping(value = UrlConst.SELECT2_TEMPLATE)
    @ResponseBody
    public Object select2GetTemplate(@RequestParam(required = false) String term, @RequestParam(required = true) Long companyId) {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> lst = templateService.getTemplateByCompanyId(term,"", companyId);
        obj.setTotal(lst.size());
        obj.setResults(lst);
        return obj;
    }
    /**
     * loadTemplate
     *
     * @param nameFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws XDocReportException
     * @author phatvt
     */
    @PostMapping(value = UrlConst.LOAD_TEMPLATE)
    @ResponseBody
    public byte[] loadTemplate(@RequestParam(value = "nameFile") String nameFile) throws FileNotFoundException, IOException, XDocReportException  {
        
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            //File tempFileDocx = new File(CommonConstant.UPLOADED_FOLDER_TEAMPLATE + nameFile);
            File tempFileDocx = new File(nameFile);
            if(nameFile.endsWith("html")){
                InputStream input = new FileInputStream(tempFileDocx);
                return IOUtils.toByteArray(input);
            }
            IXDocReport reportPdf = XDocReportRegistry.getRegistry().loadReport(new FileInputStream(tempFileDocx),
                    TemplateEngineKind.Velocity);
            PdfOptions pdfOptions = PdfOptions.create().fontEncoding("UTF-8");
            Options convertOptions = Options.getTo(ConverterTypeTo.XHTML).via(ConverterTypeVia.XWPF).subOptions(pdfOptions);
            reportPdf.convert(reportPdf.createContext(), convertOptions, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
            
        }
    }
    /**
     * importFileAttach
     *
     * @param request
     * @return
     * @author phatvt
     */
    @PostMapping(value = UrlConst.IMPORT_FILE_ATTACH)
    @ResponseBody
    public List<FileUploadResponseDto> importFileAttach(MultipartHttpServletRequest request, @RequestParam(value="companyId", required = false) Long companyId, Locale locale){
        
        //folder repo
        //String repo = systemConfig.getConfig(SystemConfig.REPO_URL_ATTACH_FILE);
        //String urlAttach = systemConfig.getPhysicalPathById(repo, null);
        companyId = (null == companyId) ? UserProfileUtils.getCompanyId() : companyId;
        List<FileUploadResponseDto> listResponse = new ArrayList<>();
        FileUploadResponseDto response = null;
        
        String emailUuid = request.getParameter("emailUuid");
        
        try{
            Iterator<String> itr = request.getFileNames();
            while (itr.hasNext()) {
                response =  new FileUploadResponseDto();
                String htmlParamName = itr.next();
                MultipartFile file = request.getFile(htmlParamName);
                String error = null;
                //file 0b
                if(file.getSize() == 0L) {
                    error = msg.getMessage(ConstantCore.ATTACH_FILE_ZERO, null, locale);
                    throw new Exception(error);
                }
                //total file 
                Long totalSize = emailService.totalSizeAttach(emailUuid) + file.getSize();
                if(totalSize > ConstantCore.ATTACH_FILE_SIZE_BYTE) {
                    String [] errorArgs = new String[1];
                    errorArgs[0] = ConstantCore.ATTACH_FILE_SIZE.toString();
                    error = msg.getMessage(ConstantCore.ATTACH_FILE_BIG, errorArgs, locale);
                    throw new Exception(error);
                }
                //ext
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                if(!ConstantCore.EXT_ATTACH_FILE_LIST.contains(ext)) {
                    error = msg.getMessage(ConstantCore.ATTACH_FILE_FAIL_EXT, null, locale);
                    throw new Exception(error);
                }
                //uuid file attach name
                UUID fileNameUuid = UUID.randomUUID();
                //save info attach file
                JcaAttachFileEmail attachFile = new JcaAttachFileEmail();
                attachFile.setFileName(file.getOriginalFilename());
                attachFile.setFileSize(file.getSize());
//                attachFile.setCreatedBy(UserProfileUtils.getUserNameLogin());
                attachFile.setCreatedDate(comService.getSystemDateTime());
//                attachFile.setUuidEmail(emailUuid);
                //emailService.saveAttachFileEmail(attachFile);
                
                /*byte[] bytes = null;
                bytes = file.getBytes();
                // create folder upload
                Path pathDirectory = Paths.get(urlAttach + "//" + emailUuid );
                File directory = new File(pathDirectory.toString());
                if(!directory.exists()){
                    directory.mkdir();
                }
                //write file to folder
                Path pathFile = Paths.get(urlAttach + "//" + emailUuid + "//" + file.getOriginalFilename());
                Files.write(pathFile, bytes);
                //rename file
                Files.move(pathFile, pathFile.resolveSibling(attachFile.getFileNameUuid()));*/
                
                CompanyDto company = companyService.findById(companyId);
                Path pathUrl = Paths.get(ConstantCore.EMAIL_ATTACH_FOLDER, company.getSystemCode(), emailUuid);
                String subFilePath = pathUrl.toString();
                FileResultDto fileResultDto = jrepositoryService.uploadFileBySettingKey(file, fileNameUuid.toString(), SystemConfig.REPO_URL_ATTACH_FILE, 2, null, subFilePath, companyId, locale);
                
                //import fail
                if(!fileResultDto.isStatus()) {
                    throw new Exception(fileResultDto.getMessage());
                }
                
//                attachFile.setFileNameUuid(fileResultDto.getFileName());
                attachFile.setCompanyId(companyId);
                attachFile.setRepositoryId(fileResultDto.getRepositoryId());
                attachFile.setUuidEmail(emailUuid);
                attachFile.setFilePath(subFilePath + "/" + fileResultDto.getFileName());
                emailService.saveAttachFileEmail(attachFile);
                
                response.setStatus("success");
                response.setIdFile(attachFile.getId());
                listResponse.add(response);
            }
           }catch(Exception e){
               response.setStatus("fail");
               response.setMessage(e.getMessage());
               listResponse.add(response);
           }
        return listResponse;
    }
    
    /**
     * getPostList
     *
     * @param searchDto
     * @param page
     * @param pageSizeParam
     * @param locale
     * @return
     * @author phatvt
     * @throws DetailException 
     */
    @GetMapping(value = UrlConst.EMAIL_LIST)
    public ModelAndView getPostList(@ModelAttribute(value = "searchDto") EmailSearchDto searchDto
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page
            , @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam, Locale locale) throws DetailException  {
        ModelAndView mav = new ModelAndView(EMAIL_LIST);
        // set url ajax
        //citySearch.setUrl(UrlConst.CITY.concat(UrlConst.LIST));
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_MAIL_MANAGEMENT)
                && !UserProfileUtils.hasRole(ROLE_MAIL_MANAGEMENT.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_MAIL_MANAGEMENT.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(EmailSearchEnum.class, mav);

        // init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
        
        searchDto.setCompanyId(UserProfileUtils.getCompanyId());
        
        PageWrapper<JcaEmailDto> pageWrapper = emailService.doSearch(page, searchDto, pageSize);
        //list status send mail
//        List<ConstantDisplay> listStatus = constantDisplayService.findByType(CommonConstant.SEND_EMAIL_STATUS);
        /*List<Select2Dto> listStatus = new ArrayList<>();
        for (EmailStatusEnum emailStatusEnum : EmailStatusEnum.values()) {
            listStatus.add(new Select2Dto(emailStatusEnum.getValue(), emailStatusEnum.getText(), emailStatusEnum.getText()));
        }*/
        List<JcaConstantDto> listStatus = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(CommonConstant.GROUP_JCA_APP_SLA, CommonConstant.KIND_SLA_SEND_STATUS, UserProfileUtils.getLanguage());
        mav.addObject("listStatus",listStatus);
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        return mav;
    } 
    
    /**
     * ajaxListEmail
     *
     * @param searchDto
     * @param page
     * @param pageSizeParam
     * @param locale
     * @return
     * @author phatvt
     * @throws DetailException 
     */
    @PostMapping(value = UrlConst.AJAXLIST)
    @ResponseBody
    public ModelAndView ajaxListEmail(@ModelAttribute(value = "searchDto") EmailSearchDto searchDto
            , @RequestParam(value = "page", required = false, defaultValue = "1") int page
            , @RequestParam(value = ConstantCore.PAGE_SIZE) Optional<Integer> pageSizeParam, Locale locale) throws DetailException  {
        ModelAndView mav = new ModelAndView(EMAIL_TABLE);
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_MAIL_MANAGEMENT)
                && !UserProfileUtils.hasRole(ROLE_MAIL_MANAGEMENT.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_MAIL_MANAGEMENT.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        // set init search
        SearchUtil.setSearchSelect(EmailSearchEnum.class, mav);

        // init page size
        int pageSize = pageSizeParam.orElse(systemConfig.getIntConfig(SystemConfig.PAGING_SIZE));
        
        PageWrapper<JcaEmailDto> pageWrapper = emailService.doSearch(page, searchDto, pageSize);
        mav.addObject("pageWrapper", pageWrapper);
        mav.addObject("searchDto", searchDto);
        return mav;
    }
    
    /**
     * getDetailEmail
     *
     * @param emailId
     * @return
     * @author phatvt
     * @throws Exception
     */
    @PostMapping(value = UrlConst.EMAIL_DETAIL)
    @ResponseBody
    public Object getDetailEmail(@RequestParam("emailId") Long emailId) throws Exception {
        JcaEmailDto result = emailService.getDetailEmail(emailId);
        return result;
    }

    /**
     * sendMail
     *
     * @param emailDto
     * @param locale
     * @return
     * @author phatvt
     */
    @PostMapping(value = UrlConst.EMAIL_SEND)
    @ResponseBody
    public ResponseEmailDto emailSend(@RequestBody EmailDto emailDto, Locale locale)  {
        return emailService.sendEmail(emailDto);
    } 
    
    /**
     * getEmailDetail
     *
     * @param emailDto
     * @param locale
     * @return
     * @author phatvt
     */
    @GetMapping(value = UrlConst.EMAIL_GET_DETAIL)
    public ModelAndView getEmailDetail(@ModelAttribute(value = "emailDto") EmailDto emailDto, Locale locale)  {
        // Security for this page.
        if (!UserProfileUtils.hasRole(ROLE_MAIL_MANAGEMENT)
                && !UserProfileUtils.hasRole(ROLE_MAIL_MANAGEMENT.concat(ConstantCore.COLON_DISP))
                && !UserProfileUtils.hasRole(ROLE_MAIL_MANAGEMENT.concat(ConstantCore.COLON_EDIT))) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }
        ModelAndView mav = new ModelAndView(EMAIL_DETAIL);
        //list name file attach
        List<JcaAttachFileEmail> listAttach = emailService.getListFileAttach(emailDto.getEmailId());
        mav.addObject("listAttach", listAttach);
        mav.addObject("emailDto", emailDto);
        return mav;
    }

    @PostMapping(value = "/delete_acttach_file")
    public void deleteAttachById(@RequestParam("id") Long id){
        try {
            emailService.deleteAttachFileById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostMapping(value = "/clear_acttach_file")
    public void clearAttach(@RequestParam("emailUuid") String emailUuid){
        try {
            emailService.clearAttachFile(emailUuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
