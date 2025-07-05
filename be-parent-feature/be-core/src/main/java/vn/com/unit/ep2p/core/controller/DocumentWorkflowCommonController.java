/*******************************************************************************
 * Class        ：DocumentCommonWorkflowController
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：TaiTM
 * Change log   ：2021/01/19：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.binding.DoubleEditor;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Message;
import vn.com.unit.ep2p.admin.constant.MessageList;
import vn.com.unit.ep2p.admin.constant.ViewConstant;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.res.dto.DocumentAppRes;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;
import vn.com.unit.ep2p.core.service.AttachedFileService;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;
import vn.com.unit.workflow.activiti.core.impl.ActivitiWorkflowParams;
import vn.com.unit.workflow.constant.WorkflowConstant;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.dto.JpmButtonLangDeployDto;
import vn.com.unit.workflow.dto.JpmButtonWrapper;
import vn.com.unit.workflow.dto.JpmHiTaskDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmStatusCommonDto;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.service.JpmButtonForStepDeployService;
import vn.com.unit.workflow.service.JpmButtonLangDeployService;
import vn.com.unit.workflow.service.JpmHiTaskService;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmStatusCommonService;
import vn.com.unit.workflow.service.JpmStatusDeployService;

/**
 * DocumentCommonWorkflowController
 *
 * @author TaiTM
 * @version 01-00
 * @since 01-00
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class DocumentWorkflowCommonController<SAVE extends DocumentSaveReq, ACTION extends DocumentActionReq>
        implements AttachedFileControllerInterface {


    @Autowired
    private MessageSource msg;

    @Autowired
    private ActivitiWorkflowParams activitiWorkflowParams;
    @Autowired
    private JpmHiTaskService jpmHiTaskService;

    @Autowired
    private JpmProcessDeployService jpmProcessDeployService;

    @Autowired
    private JpmButtonForStepDeployService jpmButtonForStepDeployService;

    @Autowired
    private AttachedFileService attachedFileService;

    @Autowired
    private JpmButtonLangDeployService jpmButtonLangDeployService;

    public abstract DocumentWorkflowCommonService<SAVE, ACTION> getService();

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private JpmStatusDeployService jpmStatusDeployService;

    @Autowired
    private JpmStatusCommonService jpmStatusCommonService;
    

    private static final String STATUS_COMMON_DRAFT = "000";

    @InitBinder
    public void initBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
        if (systemConfig == null) {
            return;
        }
        locale = new Locale("en");
        // binder.setAutoGrowCollectionLimit(1000);
        // The date format to parse or output your dates
        SimpleDateFormat dateFormat = new SimpleDateFormat(systemConfig.getConfig(SystemConfig.DATE_PATTERN)); // SystemConfig.DATE_PATTERN
        // Create a new CustomDateEditor
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        // Register it as custom editor for the Date type
        binder.registerCustomEditor(Date.class, editor);

        binder.registerCustomEditor(Double.class, new DoubleEditor(locale, "#,###.##"));

        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        CustomNumberEditor customNumberEditor = new CustomNumberEditor(BigDecimal.class, numberFormat, true);
        binder.registerCustomEditor(BigDecimal.class, customNumberEditor);
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, numberFormat, true));
        customeBinder(binder, request, locale);
    }

    @Override
    public AttachedFileService getAttachedFileService() {
        return attachedFileService;
    }

    abstract public String getControllerURL(String customerAlias);

    abstract public String getBusinessCode(String customerAlias);

    abstract public String viewEdit(String customerAlias);

    abstract public void sendEmailAction(ACTION editDto, Long buttonId);

    abstract public void sendEmailEdit(ACTION editDto, Long userUpdated);

    public String objectEditName() {
        return "editDto";
    }

    public boolean showButtonClone(ACTION editDto) {
        return false;
    }

    public boolean showButtonUpdate(ACTION editDto) {
        return false;
    }

    abstract public String firstStepInProcess(String customerAlias);

    abstract public String roleForAttachment(String customerAlias);

    abstract public void initDataEdit(ModelAndView mav, String customerAlias, ACTION editDto, boolean isDetail,
                                      Locale locale);

    abstract public void validate(ACTION editDto, BindingResult bindingResult);

    public void customeBinder(WebDataBinder binder, HttpServletRequest request, Locale locale) {
        // TODO: Override
    }

    public boolean hasRoleForEdit(String customerAlias) {
        return (false || hasRoleSpecialForEdit(customerAlias));
    }

    public boolean hasRoleSpecialForEdit(String customerAlias) {
        return false;
    }

    public boolean hasRoleForDetail(String customerAlias) {
        return false;
    }

    public ModelAndView returnModelProcess(ACTION objectDto, Long referenceId, String customerAlias,
                                           HttpServletRequest httpServletRequest, Locale locale, RedirectAttributes redirectAtrribute) {
        ModelAndView mav = new ModelAndView();

        Long id = referenceId;

        MessageList messageList = objectDto.getMessageList();
        String viewName = UrlConst.REDIRECT.concat("/").concat(getControllerURL(customerAlias));
        if (Message.SUCCESS.equals(messageList.getStatus())) {
            viewName = viewName.concat("/edit?id=" + id);
        } else {
            if (id != null) {
                viewName = viewName.concat("/edit?id=" + id);
            } else {
                viewName = viewName.concat("/edit");
            }
        }

        redirectAtrribute.addFlashAttribute("messageList", objectDto.getMessageList());
        redirectAtrribute.addAttribute("id", id);
        mav.setViewName(viewName);
        return mav;
    }

    private void setStatusForEditDto(ACTION editDto, Locale locale) {
        if (editDto.getDocId() == null) {
            JpmStatusCommonDto statusCommon = jpmStatusCommonService.getStatusCommon(STATUS_COMMON_DRAFT,
                    locale.toString());
            if (statusCommon != null) {
                editDto.setStatusName(statusCommon.getStatusName());
                editDto.setStatusCode(statusCommon.getStatusCode());
            }
        } else {
            JpmStatusDeployDto status = jpmStatusDeployService.getStatusDeploy(editDto.getDocId(), locale.toString());
            if (status != null) {
                editDto.setStatusName(status.getStatusName());
                editDto.setStatusCode(status.getStatusCode());
            }
        }
    }

    /**
     * doGetEdit
     *
     * @param id
     * @param locale
     * @return
     * @throws Exception
     * @author TaiTM
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView doGetEdit(@PathVariable(required = false) String customerAlias,
                                  @RequestParam(value = "id", required = false) Long id, RedirectAttributes redirectAtrribute, Locale locale)
            throws Exception {
        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));
        if (!hasRoleForEdit(customerAlias) && !hasRoleSpecialForEdit(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ACTION editDto = getService().getEdit(id, customerAlias, locale);

        setStatusForEditDto(editDto, locale);

//        if (STATUS_COMMON_FINISHED.equals(editDto.getStatusCode())) {
//            String viewName = UrlConst.REDIRECT.concat("/").concat(getControllerURL(customerAlias))
//                    .concat("/detail?id=" + id);
//            redirectAtrribute.addAttribute("id", id);
//            mav.setViewName(viewName);
//            return mav;
//        }

        // init attach file plugin
        attachedFileService.initComponent(mav, getBusinessCode(customerAlias), editDto.getRefAttachment(), true,
                roleForAttachment(customerAlias));

        boolean isDetail = false;
        mav.addObject(objectEditName(), editDto);

        initDataEdit(mav, customerAlias, editDto, isDetail, locale);
        initDataProcess(mav, customerAlias, editDto, isDetail, locale);

        mav.addObject("customerAlias", customerAlias);
        mav.addObject("hasRoleForEdit", hasRoleForEdit(customerAlias));
        mav.addObject("hasRoleForAdd", hasRoleForAdd(customerAlias));
        mav.addObject("hasRoleSpecialForEdit", hasRoleSpecialForEdit(customerAlias));

        if (("999".equals(editDto.getStatusCode()) || "994".equals(editDto.getStatusCode()))
                && hasRoleSpecialForEdit(customerAlias)) {
            mav.addObject("showButtonUpdate", showButtonUpdate(editDto));
        }
        mav.addObject("showButtonClone", showButtonClone(editDto));
        return mav;
    }

    /**
     * doGetDetail
     *
     * @param id
     * @param locale
     * @return
     * @throws Exception
     * @author TaiTM
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView doGetDetail(@PathVariable(required = false) String customerAlias,
            @RequestParam(value = "id", required = false) Long id, RedirectAttributes redirectAtrribute, Locale locale)
throws Exception {
        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));

        if (!hasRoleForDetail(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ACTION editDto = getService().getEdit(id, customerAlias, locale);

        setStatusForEditDto(editDto, locale);

        // init attach file plugin
        attachedFileService.initComponent(mav, getBusinessCode(customerAlias), editDto.getRefAttachment(), true,
                roleForAttachment(customerAlias));

        editDto.setHasEdit(false);
        boolean isDetail = true;
        String url = getControllerURL(customerAlias).concat("/detail");
        if (id != null) {
            url = url + "?id=" + id;
        }
        editDto.setUrl(url);
        mav.addObject(objectEditName(), editDto);

        initDataEdit(mav, customerAlias, editDto, isDetail, locale);
        initDataProcess(mav, customerAlias, editDto, isDetail, locale);

        mav.addObject("showButtonClone", showButtonClone(editDto));
        mav.addObject("isDetail", true);
        return mav;
    }

    public void initDataProcess(ModelAndView mav, String customerAlias, ACTION editDto, boolean isDeatail,
                                Locale locale) throws Exception {
        DocumentAppRes<ACTION> docDto = new DocumentAppRes();
        Long docId = editDto.getDocId();

        if (docId != null) {
            try {
                docDto = getService().detail(docId);
            } catch (Exception e) {
            }
        }

        editDto.setProcessDeployCode(getBusinessCode(customerAlias));

        // Kiểm tra và set lại process deploy id
        JpmProcessDeployDto processDeployDto = new JpmProcessDeployDto();
        if (docDto.getEfoDocDto() != null) {
            DocumentDataResultDto efo = docDto.getEfoDocDto();

            editDto.setProcessDeployId(efo.getProcessDeployId());
            editDto.setBusinessId(efo.getBusinessId());
            editDto.setCoreTaskId(efo.getCoreTaskId());
        } else {
            processDeployDto = jpmProcessDeployService.getJpmProcessDeployLastedByBusinessCode(
                    UserProfileUtils.getCompanyId(), editDto.getProcessDeployCode());
            if (editDto.getProcessDeployId() == null && processDeployDto != null) {
                editDto.setProcessDeployId(processDeployDto.getProcessDeployId());
                editDto.setBusinessId(processDeployDto.getBusinessId());
            }
        }

        // History
        List<JpmHiTaskDto> historyProcessList = new ArrayList<>();

        if (docDto.getDto() == null && docId != null) {
            historyProcessList = getService().getListProcessHistoryDocument(docId);
        } else {
            historyProcessList = getHistory(docDto);
        }
        mav.addObject("historyProcessList", historyProcessList);

        // Button
        JpmButtonWrapper<JpmButtonForDocDto> buttonWrapper = new JpmButtonWrapper<JpmButtonForDocDto>();
        if (docDto.getDto() != null || docId == null) {
            buttonWrapper = getButtons(docDto, customerAlias, docId, editDto.getProcessDeployId(), isDeatail, locale);
        }

        mav.addObject("buttonWrapper", buttonWrapper);
        mav.addObject("controllerURL", getControllerURL(customerAlias));

        // Hiển thì Workflow
        boolean isWorkflow = false;
        if (docId != null) {
            isWorkflow = true;
        }
        mav.addObject("isWorkflow", isWorkflow);

        boolean isShowWorkflow = false;
        if (processDeployDto != null) {
            StringBuilder processNameBuilder = new StringBuilder(ConstantCore.EMPTY);
            String processDeployName = processDeployDto.getProcessName();
            Long majorVersion = processDeployDto.getMajorVersion();
            if (majorVersion == null) {
                majorVersion = 0L;
            }
            Long minorVersion = processDeployDto.getMinorVersion();
            if (minorVersion == null) {
                minorVersion = 0L;
            }
            processNameBuilder.append(processDeployName);
            processNameBuilder.append(ConstantCore.HYPHEN);
            processNameBuilder.append(majorVersion.toString());
            processNameBuilder.append(ConstantCore.DOT);
            processNameBuilder.append(minorVersion);
            isShowWorkflow = processDeployDto.isShowWorkflow();
        }
        mav.addObject("isShowWorkflow", isShowWorkflow);
    }

    public List<JpmHiTaskDto> getHistory(DocumentAppRes<ACTION> docDto) {
        return docDto.getListProcessHistory();
    }

    public JpmButtonWrapper<JpmButtonForDocDto> getButtons(DocumentAppRes<ACTION> docDto, String customerAlias,
                                                           Long docId, Long processDeployId, boolean isDeatail, Locale locale) {
        JpmButtonWrapper<JpmButtonForDocDto> buttonWrapper = new JpmButtonWrapper<>();

        if (!isDeatail) {
            if (docId != null) {
                buttonWrapper = docDto.getJpmButtons();

                // trường hợp có trạng thái là lưu nháp.
                if (docDto.getEfoDocDto() != null && "000".equals(docDto.getEfoDocDto().getCommonStatusCode())) {
                    createButtonForDraft(buttonWrapper, locale);
                } else {
                    // Kiểm tra thêm ITEM_FUNCTION_CODE
                    String itemFunctionCode = docDto.getEfoDocDto().getItemFunctionCode();
                    if (StringUtils.isNotBlank(itemFunctionCode) && !UserProfileUtils.hasRole(itemFunctionCode)
                            && !UserProfileUtils.hasRole(itemFunctionCode.concat(CoreConstant.COLON_DISP))
                            && !UserProfileUtils.hasRole(itemFunctionCode.concat(CoreConstant.COLON_EDIT))) {
                        buttonWrapper = new JpmButtonWrapper<>();
                    }
                }
            } else if (docDto.getEfoDocDto() == null || "000".equals(docDto.getEfoDocDto().getCommonStatusCode())) {
                // Trường hợp tạo mới
                buttonWrapper = jpmButtonForStepDeployService.getListButtonForDocDtoByProcessDeployIdAndStepCode(
                        processDeployId, firstStepInProcess(customerAlias), locale.getLanguage().toString());

                createButtonForDraft(buttonWrapper, locale);
            }
        }

        return buttonWrapper;
    }

    public JpmButtonWrapper<JpmButtonForDocDto> createButtonForDraft(JpmButtonWrapper<JpmButtonForDocDto> buttonWrapper,
                                                                     Locale locale) {
        if (CollectionUtils.isNotEmpty(buttonWrapper.getData())) {
            // Button save
            JpmButtonForDocDto saveButton = new JpmButtonForDocDto();
            saveButton.setId(-99L);
            saveButton.setButtonClass("btn-primary");
            saveButton.setButtonNamePassive("save");
            saveButton.setButtonText(msg.getMessage("save.button", null, locale));
            saveButton.setButtonType("SAVE");
            saveButton.setButtonValue("-1");

            List<JpmButtonForDocDto> lstButton = new ArrayList<>();
            lstButton.add(saveButton);

            lstButton.addAll(buttonWrapper.getData());
            buttonWrapper.setData(lstButton);
        }

        return buttonWrapper;
    }

    @RequestMapping(value = "/do-action-process", method = RequestMethod.POST)
    public ModelAndView doActionProcess(@PathVariable(required = false) String customerAlias,
                                        @Valid @ModelAttribute(value = "objectDto") ACTION documentActionReq,
                                        @RequestParam(value = "referenceId", required = false) Long referenceId, BindingResult bindingResult,
                                        HttpServletRequest httpServletRequest, Locale locale, RedirectAttributes redirectAtrribute)
            throws Exception {

        MessageList messageList = new MessageList();
        documentActionReq.setLanguageCode(locale.toString());

        String[] actionNames = new String[1];
        Long buttonId = Long.valueOf(documentActionReq.getButtonId());

        List<JpmButtonLangDeployDto> button = new ArrayList<>();
        Long btnSaveId = -99L;
        if (btnSaveId.equals(buttonId)) { // Button Save
            actionNames[0] = msg.getMessage("save.button", null, locale);

        } else {
            button = jpmButtonLangDeployService.getButtonLangDeployDtosByButtonDeployId(buttonId);
        }

        String buttonName = "";
        if (CollectionUtils.isNotEmpty(button)) {
            for (JpmButtonLangDeployDto bt : button) {
                if (StringUtils.isNotBlank(bt.getLangCode())
                        && bt.getLangCode().equalsIgnoreCase(locale.getLanguage())) {
                    buttonName = bt.getButtonName();
                    break;
                }
            }
            actionNames[0] = buttonName;
        }

        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));
        try {
            // Save thì không chuyển bước
            if (documentActionReq.getButtonText().equals("SAVE")) {
                documentActionReq.setNextStep(false);
            } else if (documentActionReq.getButtonText().equals("SUBMIT")) {
                validate(documentActionReq, bindingResult);

                if (bindingResult.hasErrors()) {
                    boolean isDetail = false;
                    documentActionReq.setHasEdit(!isDetail);

                    initDataEdit(mav, customerAlias, documentActionReq, isDetail, locale);
                    initDataProcess(mav, customerAlias, documentActionReq, isDetail, locale);

                    if (documentActionReq.getMessageList() != null) {
                        messageList = documentActionReq.getMessageList();
                        messageList.setStatus(Message.ERROR);
                    } else {
                        String msgError = msg.getMessage(ConstantCore.MSG_ERROR_AND_BUTTON_NAME, actionNames, locale);
                        messageList.add(msgError);
                    }

                    messageList.setStatus(Message.ERROR);
                    mav.addObject(ConstantCore.MSG_LIST, messageList);

                    // init attach file plugin
                    attachedFileService.initComponent(mav, getBusinessCode(customerAlias),
                            documentActionReq.getRefAttachment(), true, roleForAttachment(customerAlias));

                    mav.addObject(Message.ERROR, true);
                    mav.addObject(objectEditName(), documentActionReq);

                    return mav;
                }
            }
            documentActionReq.setDocInputJson("{skip: 0}");
			documentActionReq = (ACTION) getService().action(documentActionReq, locale);
            if (buttonId != -99) {
                sendEmailAction(documentActionReq, buttonId);
            }

            String coreTaskId = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_CORE_TASK_ID);
            documentActionReq.setCoreTaskId(coreTaskId);

            String msgSucess = msg.getMessage(ConstantCore.MSG_SUCCESS_AND_BUTTON_NAME, actionNames, locale);
            messageList.setStatus(Message.SUCCESS);
            messageList.add(msgSucess);

            documentActionReq.setMessageList(messageList);
        } catch (Exception e) {
            documentActionReq.setHasEdit(true);

            initDataEdit(mav, customerAlias, documentActionReq, false, locale);
            initDataProcess(mav, customerAlias, documentActionReq, false, locale);

            if (documentActionReq.getMessageList() != null) {
                messageList = documentActionReq.getMessageList();
                messageList.setStatus(Message.ERROR);
            } else {
                String msgError = msg.getMessage(ConstantCore.MSG_ERROR_AND_BUTTON_NAME, actionNames, locale);
                messageList.add(msgError);
            }

            messageList.setStatus(Message.ERROR);
            mav.addObject(ConstantCore.MSG_LIST, messageList);

            // init attach file plugin
            attachedFileService.initComponent(mav, getBusinessCode(customerAlias), documentActionReq.getRefAttachment(),
                    true, roleForAttachment(customerAlias));

            mav.addObject(Message.ERROR, true);
            mav.addObject(objectEditName(), documentActionReq);
            return mav;
        }
        return returnModelProcess(documentActionReq, documentActionReq.getDataId(), customerAlias, httpServletRequest,
                locale, redirectAtrribute);
    }

    @RequestMapping(value = "/do-update-date", method = RequestMethod.POST)
    public ModelAndView doUpdateData(@PathVariable(required = false) String customerAlias,
                                     @Valid @ModelAttribute(value = "objectDto") ACTION documentActionReq,
                                     @RequestParam(value = "referenceId", required = false) Long referenceId, BindingResult bindingResult,
                                     HttpServletRequest httpServletRequest, Locale locale, RedirectAttributes redirectAtrribute)
            throws Exception {

        documentActionReq.setNextStep(false);
        documentActionReq.setHasUpdateData(true);
        MessageList messageList = new MessageList();
        try {
            List<JpmHiTaskDto> listHiTask = jpmHiTaskService.getListJpmHiTaskDtoByDocId(documentActionReq.getDocId());
            JpmHiTaskDto hiTaskLastest = listHiTask.stream().reduce((first, second) -> second)
            		  .filter(hi -> "taskWaitingApprove".equals(hi.getStepDeployCode()) && hi.getJsonData() != null)
            		  .orElse(null);
            sendEmailEdit(documentActionReq, hiTaskLastest.getCompletedId());
        } catch(Exception e) {
        	logger.error("sendEmailEdit", e);
        }
        documentActionReq = (ACTION) getService().action(documentActionReq, locale);
        String msgSucess = msg.getMessage("message.success.update.label", null, locale);
        messageList.setStatus(Message.SUCCESS);
        messageList.add(msgSucess);

        documentActionReq.setMessageList(messageList);


        return returnModelProcess(documentActionReq, documentActionReq.getDataId(), customerAlias, httpServletRequest,
                locale, redirectAtrribute);
    }

    @RequestMapping(value = "/clone-data", method = RequestMethod.POST)
    public ModelAndView doCloneData(@PathVariable(required = false) String customerAlias,
                                    @RequestParam(value = "id", required = false) Long id, Locale locale) throws Exception {

        ModelAndView mav = new ModelAndView(viewEdit(customerAlias));
        if (!hasRoleForEdit(customerAlias)) {
            return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
        }

        ACTION editDto = getService().getEdit(id, customerAlias, locale);

        editDto.setDocId(null);
        editDto.setCloneData(true);

        setStatusForEditDto(editDto, locale);

        // init attach file plugin
        attachedFileService.initComponent(mav, getBusinessCode(customerAlias), editDto.getRefAttachment(), true,
                roleForAttachment(customerAlias));

        boolean isDetail = false;
        editDto.setUrl(getControllerURL(customerAlias).concat("/edit"));
        mav.addObject(objectEditName(), editDto);

        initDataEdit(mav, customerAlias, editDto, isDetail, locale);
        initDataProcess(mav, customerAlias, editDto, isDetail, locale);

        mav.addObject("customerAlias", customerAlias);
        mav.addObject("hasRoleForEdit", hasRoleForEdit(customerAlias));
        mav.addObject("hasRoleForAdd", hasRoleForAdd(customerAlias));
        mav.addObject("id", null);

        return mav;
    }

    public boolean showButtonImport(String customerAlias) {
        return false;
    }

	public boolean hasRoleForAdd(String customerAlias) {
		return (false || hasRoleSpecialForEdit(customerAlias));
	}

	public boolean hasRoleMaker(String customerAlias) {
		return (false || hasRoleSpecialForEdit(customerAlias));
	}

}
