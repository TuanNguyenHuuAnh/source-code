/*******************************************************************************
 * Class        ：DocumentAppRest
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.app;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.res.dto.DocumentAppRes;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;
//import vn.com.unit.ep2p.core.res.dto.RecruitmentCandidateResultRes;
import vn.com.unit.ep2p.core.res.dto.TaskSlaRes;
import vn.com.unit.ep2p.core.service.DocumentAppService;
import vn.com.unit.ep2p.rest.AbstractRest;
import vn.com.unit.workflow.activiti.core.impl.ActivitiWorkflowParams;
import vn.com.unit.workflow.constant.WorkflowConstant;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.dto.JpmButtonWrapper;
import vn.com.unit.workflow.dto.JpmHiTaskDto;

/**
 * 
 * DocumentAppRest
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
@RestController
@RequestMapping(AppApiConstant.API_V1 + AppApiConstant.API_APP)
@Api(tags = { AppApiConstant.API_APP_DOCUMENT_DESCR })
public abstract class DocumentCommonAppRest<SAVE extends DocumentSaveReq, ACTION extends DocumentActionReq>
        extends AbstractRest {

    // @Autowired
    // private DocumentAppValidator documentAppValidator;

    @Autowired
    private ActivitiWorkflowParams activitiWorkflowParams;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    private static final String CANDIDATE_USERNAME = "candidate";
    private static final String CANDIDATE_PASSWORD = "C@ndid@te123$%^";

    public abstract DocumentAppService getService();

    @PostMapping(AppApiConstant.API_APP_DOCUMENT)
    @ApiOperation("Create document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024402, message = "Error process add document"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse saveDocument(
            @ApiParam(name = "body", value = "Document information to add new") @RequestBody SAVE documentSaveReq,
            BindingResult bindingResult) {
        long start = System.currentTimeMillis();
        try {
//            EfoDocDto efoOzDocDto = getService().setValueSaveDocument(documentSaveReq);
            // 1.Validate
//            documentAppValidator.validate(efoOzDocDto, bindingResult);
//
//            if (bindingResult.hasErrors()) {
//                return this.successHandler.handlerSuccess(bindingResult.getAllErrors(), start);
//            }

            SAVE DTO = (SAVE) getService().save(documentSaveReq);

            return this.successHandler.handlerSuccess(DTO, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    protected void saveBussiness(SAVE documentSaveReq) throws Exception {
        getService().save(documentSaveReq);
    }

    protected void saveDataBussiness(ACTION documentSaveReq) throws Exception {
        getService().saveDataBussiness(documentSaveReq);
    }

    @PutMapping(AppApiConstant.API_APP_DOCUMENT)
    @ApiOperation("Update document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse actionDocument(
            @ApiParam(name = "body", value = "Document information to update") @RequestBody ACTION documentActionReq,
            BindingResult bindingResult, Locale locale) {
        long start = System.currentTimeMillis();
        try {
//            EfoDocDto efoOzDocDto = getService().setValueActionDocument(documentActionReq);
//
//            // 1.Validate
//            documentAppValidator.validate(efoOzDocDto, bindingResult);
//
//            if (bindingResult.hasErrors()) {
//                return this.successHandler.handlerSuccess(bindingResult.getAllErrors(), start);
//            }

            EfoDocDto efoOzDocDto = getService().setValueActionDocument(documentActionReq);
            if (efoOzDocDto.getId() == null) {
                efoOzDocDto = getService().setValueSaveDocument(documentActionReq);
                efoOzDocDto = getService().save(efoOzDocDto);

                documentActionReq.setDocId(efoOzDocDto.getDocId());
                DocumentAppRes doc = getService().detail(efoOzDocDto.getDocId());
                if (doc != null && !doc.getJpmButtons().isEmpty()) {
                    JpmButtonWrapper<JpmButtonForDocDto> lstButton = (JpmButtonWrapper<JpmButtonForDocDto>) doc
                            .getJpmButtons();
                    if (lstButton != null && !lstButton.getData().isEmpty()) {
                        documentActionReq.setButtonId(lstButton.getData().get(0).getId());
                    }
                }
            }

            ACTION dto = (ACTION) getService().action(documentActionReq, locale);

            String coreTaskId = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_CORE_TASK_ID);
            dto.setCoreTaskId(coreTaskId);
            return this.successHandler.handlerSuccess(dto, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_DOCUMENT + "/{documentId}")
    @ApiOperation("detail document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse detailDocument(
            @ApiParam(name = "documentId", value = "Get document information detail on system by id", example = "1") @PathVariable("documentId") Long documentId) {
        long start = System.currentTimeMillis();
        try {
            DocumentAppRes res = getService().detail(documentId);
            // getService().actionNextStep(1440L, "waitingTraining", "Update");
            return this.successHandler.handlerSuccess(res, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_DOCUMENT + "/process/{documentId}")
    @ApiOperation("list process history document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse listProcessHistoryDocument(
            @ApiParam(name = "documentId", value = "Get list process history document information on system by id", example = "1") @PathVariable("documentId") Long documentId) {
        long start = System.currentTimeMillis();
        try {
            List<JpmHiTaskDto> res = getService().getListProcessHistoryDocument(documentId);
            return this.successHandler.handlerSuccess(res, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_DOCUMENT + "/init/{documentId}")
    @ApiOperation("Init document by form")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse initDocument(
            @ApiParam(name = "documentId", value = "Get document information default by form Id", example = "1") @PathVariable("documentId") Long documentId) {
        long start = System.currentTimeMillis();
        try {
            DocumentAppRes res = getService().initDocument(documentId);
            return this.successHandler.handlerSuccess(res, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_DOCUMENT + "/sla-list/{documentId}")
    @ApiOperation("list sla of document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse listSlaForDocument(
            @ApiParam(name = "documentId", value = "Get list process history document information on system by id", example = "1") @PathVariable("documentId") Long documentId) {
        long start = System.currentTimeMillis();
        try {
            List<TaskSlaRes> res = getService().getListSlaDocument(documentId);
            return this.successHandler.handlerSuccess(res, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_APP_DOCUMENT + "/saveData")
    @ApiOperation("Update document history")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse saveData(
            @ApiParam(name = "body", value = "Document information to update") @RequestBody ACTION documentActionReq,
            BindingResult bindingResult, Locale locale) {
        long start = System.currentTimeMillis();
        try {
            documentActionReq.setNextStep(false);
            DocumentActionReq data = getService().action(documentActionReq, locale);
            getService().createHistTask(data.getDocId(), "Update", null);
            return this.successHandler.handlerSuccess(data, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_APP_DOCUMENT + "/list-approve")
    @ApiOperation("Update list document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse actionProcessListDocument(
            @ApiParam(name = "body", value = "Document information to update") @RequestBody ACTION documentActionReq,
            BindingResult bindingResult, Locale locale) {
        long start = System.currentTimeMillis();
        try {
            // list data
            // button by buttonCode
            // owner
            ACTION dto = (ACTION) getService().actionList(documentActionReq, locale);
            return this.successHandler.handlerSuccess(dto, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @GetMapping(AppApiConstant.API_APP_DOCUMENT + "/history" + "/{documentId}")
    @ApiOperation("view history document")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse detailHistoryDocument(
            @ApiParam(name = "documentId", value = "Get document information detail on system by id", example = "1") @PathVariable("documentId") Long documentId) {
        long start = System.currentTimeMillis();
        try {
            DocumentAppRes res = getService().detailHistory(documentId);
            return this.successHandler.handlerSuccess(res, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }

    @PutMapping(AppApiConstant.API_APP_DOCUMENT + "/candidate")
    @ApiOperation("Create document candidate")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 402, message = "Forbidden"),
            @ApiResponse(code = 4024403, message = "Error process update info document"),
            @ApiResponse(code = 4024404, message = "Error document not found"),
            @ApiResponse(code = 500, message = "Internal server error"), })
    public DtsApiResponse saveDocumentForCandidate(
            @ApiParam(name = "body", value = "Document information to update") @RequestBody ACTION documentActionReq,
            BindingResult bindingResult, Locale locale) {
        long start = System.currentTimeMillis();
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    CANDIDATE_USERNAME, CANDIDATE_PASSWORD);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // end authen
            if (UserProfileUtils.getAccountId() == null) {
                String message = "Login temp user for candidate fail";
                throw new DetailException(message);
            }

            EfoDocDto efoOzDocDto = getService().setValueActionDocument(documentActionReq);
            if (efoOzDocDto.getId() == null) {
                efoOzDocDto = getService().setValueSaveDocument(documentActionReq);
                efoOzDocDto = getService().save(efoOzDocDto);

                documentActionReq.setDocId(efoOzDocDto.getDocId());

                DocumentAppRes doc = getService().detail(efoOzDocDto.getDocId());
                if (doc != null && !doc.getJpmButtons().isEmpty()) {
                    JpmButtonWrapper<JpmButtonForDocDto> lstButton = (JpmButtonWrapper<JpmButtonForDocDto>) doc
                            .getJpmButtons();
                    if (lstButton != null && !lstButton.getData().isEmpty()) {
                        documentActionReq.setButtonId(lstButton.getData().get(0).getId());
                    }
                }
            }

            ACTION dto = (ACTION) getService().action(documentActionReq, locale);

            String coreTaskId = (String) activitiWorkflowParams.getParam(WorkflowConstant.P_CORE_TASK_ID);
            dto.setCoreTaskId(coreTaskId);
            return this.successHandler.handlerSuccess(dto, start);
        } catch (Exception ex) {
            return this.errorHandler.handlerException(ex, start);
        }
    }
}
