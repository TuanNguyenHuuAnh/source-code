/*******************************************************************************
 * Class        ：ProcessStepServiceImpl
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2021/01/13：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.exception.HandlerCastException;
import vn.com.unit.ep2p.core.req.dto.ProcessButtonForStepInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessStepAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessStepLangInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessStepUpdateInfoReq;
import vn.com.unit.ep2p.service.ProcessStepService;
import vn.com.unit.workflow.dto.JpmButtonDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDto;
import vn.com.unit.workflow.dto.JpmPermissionDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.dto.JpmStatusDto;
import vn.com.unit.workflow.dto.JpmStepDto;
import vn.com.unit.workflow.dto.JpmStepLangDto;
import vn.com.unit.workflow.entity.JpmStep;
import vn.com.unit.workflow.service.JpmButtonService;
import vn.com.unit.workflow.service.JpmPermissionService;
import vn.com.unit.workflow.service.JpmProcessService;
import vn.com.unit.workflow.service.JpmStatusService;
import vn.com.unit.workflow.service.JpmStepService;

/**
 * <p>
 * ProcessStepServiceImpl
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProcessStepServiceImpl implements ProcessStepService {

    @Autowired
    protected HandlerCastException handlerCastException;
    
    @Autowired
    private JpmStepService jpmStepService;
    
    @Autowired
    private JpmButtonService jpmButtonService;
    
    @Autowired
    private JpmProcessService jpmProcessService;
    
    @Autowired
    private JpmPermissionService jpmPermissionService;
    
    @Autowired
    private JpmStatusService jpmStatusService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ProcessStepAddInfoReq processStepAddInfoReq) throws DetailException {
        Long processStepId = null;
        try {
            JpmStepDto jpmStepDto = new JpmStepDto();
            if (null != processStepAddInfoReq.getProcessId()) {
                setProcessId(processStepAddInfoReq.getProcessId(), jpmStepDto);
            }
            if( null != processStepAddInfoReq.getStatusId()) {
                setStatusId(processStepAddInfoReq.getStatusId(), jpmStepDto);
            }
            jpmStepDto.setStepNo(processStepAddInfoReq.getStepNo());
            jpmStepDto.setStepCode(processStepAddInfoReq.getStepCode());
            jpmStepDto.setStepName(processStepAddInfoReq.getStepName());
            jpmStepDto.setStepType(processStepAddInfoReq.getStepType());
            jpmStepDto.setStepKind(processStepAddInfoReq.getStepKind());
            jpmStepDto.setCommonStatusCode(processStepAddInfoReq.getStatusCodeCommon());
            jpmStepDto.setUseClaimButton(processStepAddInfoReq.getUseClaimButton());
            if(CommonCollectionUtil.isNotEmpty(processStepAddInfoReq.getStepLangs())) {
                this.setListStepLang(processStepId, processStepAddInfoReq.getStepLangs(), jpmStepDto); 
            }
            if(CommonCollectionUtil.isNotEmpty(processStepAddInfoReq.getButtonsForStep())) {
                this.setListButtonForStep(processStepId, processStepAddInfoReq.getButtonsForStep(), jpmStepDto);
            }
            
            this.save(jpmStepDto);
            processStepId = jpmStepDto.getStepId();
           
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023901_APPAPI_PROCESS_STEP_ADD_ERROR);
        }
        return processStepId;
    }
    
    private void setStatusId(Long statusId, JpmStepDto jpmStepDto) throws DetailException {
       JpmStatusDto jpmStatusDto = jpmStatusService.getStatusDtoByStatusId(statusId);
       if( null != jpmStatusDto) {
           jpmStepDto.setStatusId(statusId);
       }else {
           throw new DetailException(AppApiExceptionCodeConstant.E4023704_APPAPI_PROCESS_STATUS_NOT_FOUND, true);
       }
    }
    
    private void setPermissionId(Long permissionId, JpmButtonForStepDto jpmButtonForStepDto) throws DetailException {
        JpmPermissionDto jpmPermissionDto = jpmPermissionService.getJpmPermissionDtoByPermissionId(permissionId);
        if( null != jpmPermissionDto) {
            jpmButtonForStepDto.setPermissionId(permissionId);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023805_APPAPI_PROCESS_PERMISSION_ID_NOT_FOUND, new String [] {permissionId.toString()},true);
        }
    }
    
    private void setButtonId(Long buttonId, JpmButtonForStepDto jpmButtonForStepDto ) throws DetailException {
        JpmButtonDto jpmButtonDto = jpmButtonService.getJpmButtonDtoByButtonId(buttonId);
        if(null != jpmButtonDto) {
            jpmButtonForStepDto.setButtonId(buttonId);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023605_APPAPI_PROCESS_BUTTON_ID_NOT_FOUND, new String [] {buttonId.toString()},true);
        }
    }
    
    private void setListButtonForStep(Long processStepId, List<ProcessButtonForStepInfoReq> listButtonForStep, JpmStepDto jpmStepDto) throws DetailException {
        List<JpmButtonForStepDto> buttonsForStep  = new ArrayList<>();
        for (ProcessButtonForStepInfoReq processButtonForStepInfoReq : listButtonForStep) {
            JpmButtonForStepDto jpmButtonForStepDto = new JpmButtonForStepDto();
            jpmButtonForStepDto.setStepId(processStepId);
            if(null != processButtonForStepInfoReq.getButtonId()) {
                setButtonId(processButtonForStepInfoReq.getButtonId(), jpmButtonForStepDto);
            }
            if(null != processButtonForStepInfoReq.getPermissionId()) {
                setPermissionId(processButtonForStepInfoReq.getPermissionId(), jpmButtonForStepDto);
            }
            jpmButtonForStepDto.setPermissionId(processButtonForStepInfoReq.getPermissionId());
            jpmButtonForStepDto.setOptionSaveForm(processButtonForStepInfoReq.isOptionSaveForm());
            jpmButtonForStepDto.setOptionSaveEform(processButtonForStepInfoReq.isOptionSaveEform());
            jpmButtonForStepDto.setOptionAuthenticate(processButtonForStepInfoReq.isOptionAuthenticate());
            jpmButtonForStepDto.setOptionSigned(processButtonForStepInfoReq.isOptionSigned());
            jpmButtonForStepDto.setOptionExportPdf(processButtonForStepInfoReq.isOptionExportPdf());
            jpmButtonForStepDto.setOptionShowHistory(processButtonForStepInfoReq.isOptionShowHistory());
            jpmButtonForStepDto.setOptionFillToEform(processButtonForStepInfoReq.isOptionFillToEform());
            buttonsForStep.add(jpmButtonForStepDto);
        }
        jpmStepDto.setButtonForStepDtos(buttonsForStep);
    }
    
    
    
    private void setListStepLang(Long processStepId, List<ProcessStepLangInfoReq> listStepLang, JpmStepDto jpmStepDto) {
        List<JpmStepLangDto> stepLangs  = new ArrayList<>();
        for (ProcessStepLangInfoReq processStepLangInfoReq : listStepLang) {
            JpmStepLangDto jpmStepLangDto = new JpmStepLangDto();
            jpmStepLangDto.setStepId(processStepId);
            jpmStepLangDto.setLangCode(processStepLangInfoReq.getLangCode());
            jpmStepLangDto.setStepName(processStepLangInfoReq.getStepName());
            stepLangs.add(jpmStepLangDto);
        }
        jpmStepDto.setStepLangs(stepLangs);
    }
    private void setProcessId(Long processId, JpmStepDto jpmStepDto) throws DetailException {
        JpmProcessDto jpmProcessDto = jpmProcessService.getJpmProcessDtoByProcessId(processId);
        if (null != jpmProcessDto) {
            jpmStepDto.setProcessId(processId);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProcessStepUpdateInfoReq processStepUpdateInfoReq) throws DetailException { 
        Long processStepId = processStepUpdateInfoReq.getStepId();
        JpmStepDto jpmStepDto = jpmStepService.getStepDtoByStepId(processStepId);
        
        if(null != jpmStepDto) {
            try {
                if (null != processStepUpdateInfoReq.getProcessId()) {
                    setProcessId(processStepUpdateInfoReq.getProcessId(), jpmStepDto);
                }
                if( null != processStepUpdateInfoReq.getStatusId()) {
                    setStatusId(processStepUpdateInfoReq.getStatusId(), jpmStepDto);
                }
                jpmStepDto.setStepNo(processStepUpdateInfoReq.getStepNo());
                jpmStepDto.setStepName(processStepUpdateInfoReq.getStepName());
                jpmStepDto.setStepType(processStepUpdateInfoReq.getStepType());
                jpmStepDto.setStepKind(processStepUpdateInfoReq.getStepKind());
                jpmStepDto.setCommonStatusCode(processStepUpdateInfoReq.getStatusCodeCommon());
                jpmStepDto.setUseClaimButton(processStepUpdateInfoReq.getUseClaimButton());
                if(CommonCollectionUtil.isNotEmpty(processStepUpdateInfoReq.getStepLangs())) {
                    this.setListStepLang(processStepId, processStepUpdateInfoReq.getStepLangs(), jpmStepDto); 
                }
                if(CommonCollectionUtil.isNotEmpty(processStepUpdateInfoReq.getButtonsForStep())) {
                    this.setListButtonForStep(processStepId, processStepUpdateInfoReq.getButtonsForStep(), jpmStepDto);
                }
                this.save(jpmStepDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023902_APPAPI_PROCESS_STEP_UPDATE_INFO_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023904_APPAPI_PROCESS_STEP_NOT_FOUND, true);
        }
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long processStepId) throws DetailException {
        JpmStepDto jpmStepDto =  jpmStepService.getStepDtoByStepId(processStepId);
        if(null != jpmStepDto) {
            try {
                jpmStepService.deleteById(processStepId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023903_APPAPI_PROCESS_STEP_DELETE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023904_APPAPI_PROCESS_STEP_NOT_FOUND, true);
        }
        
    }

    @Override
    public JpmStepDto save(JpmStepDto jpmStepDto) {
        JpmStep jpmStep = jpmStepService.saveJpmStepDto(jpmStepDto);
        jpmStepDto.setStepId(jpmStep.getId());
        return jpmStepDto;
    }
}
