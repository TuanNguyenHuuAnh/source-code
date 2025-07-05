/*******************************************************************************
 * Class        ：ProcessParamServiceImpl
 * Created date ：2021/01/12
 * Lasted date  ：2021/01/12
 * Author       ：SonND
 * Change log   ：2021/01/12：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.exception.HandlerCastException;
import vn.com.unit.ep2p.core.req.dto.ProcessParamConfigInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessParamInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessParamUpdateInfoReq;
import vn.com.unit.ep2p.service.ProcessParamService;
import vn.com.unit.workflow.dto.JpmParamConfigDto;
import vn.com.unit.workflow.dto.JpmParamDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.dto.JpmStepDto;
import vn.com.unit.workflow.entity.JpmParam;
import vn.com.unit.workflow.service.JpmParamService;
import vn.com.unit.workflow.service.JpmProcessService;
import vn.com.unit.workflow.service.JpmStepService;

/**
 * <p>
 * ProcessParamServiceImpl
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProcessParamServiceImpl implements ProcessParamService {

    @Autowired
    protected HandlerCastException handlerCastException;
    
    @Autowired
    private JpmParamService jpmParamService;
    
    @Autowired
    private JpmProcessService jpmProcessService;
    
    @Autowired
    private JpmStepService jpmStepService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ProcessParamInfoReq processParamInfoReq) throws DetailException {
        Long paramId = null;
        try {
            JpmParamDto jpmParamDto = new JpmParamDto();

            if (null != processParamInfoReq.getProcessId()) {
                setProcessId(processParamInfoReq.getProcessId(), jpmParamDto);
            }
            if(null != processParamInfoReq.getFieldName()) {
                jpmParamDto.setFieldName(processParamInfoReq.getFieldName());
            }
            if(null != processParamInfoReq.getDataType()) {
                jpmParamDto.setDataType(processParamInfoReq.getDataType());
            }
            if(null != processParamInfoReq.getFormFieldName()) {
                jpmParamDto.setFormFieldName(processParamInfoReq.getFormFieldName());
            }
            if(null != processParamInfoReq.getListParamConfig()) {
                setListParamConfig(processParamInfoReq.getProcessId(), paramId, processParamInfoReq.getListParamConfig(), jpmParamDto);
            }
            this.save(jpmParamDto);
            paramId = jpmParamDto.getParamId();
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023501_APPAPI_PROCESS_PARAM_ADD_ERROR);
        }
        return paramId;
    }
    
    private void setStepId(Long stepId, JpmParamConfigDto jpmParamConfigDto) throws DetailException {
        JpmStepDto jpmStepDto = jpmStepService.getStepDtoByStepId(stepId);
        if(null != jpmStepDto){
            jpmParamConfigDto.setStepId(stepId);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023905_APPAPI_PROCESS_STEP_ID_NOT_FOUND, new String [] {stepId.toString()}, true);
        }
    }
    
    private void setListParamConfig(Long processId, Long paramId, List<ProcessParamConfigInfoReq> listParamConfig, JpmParamDto jpmParamDto) throws DetailException {
        List<JpmParamConfigDto> paramConfigDtos = new ArrayList<>();
        for (ProcessParamConfigInfoReq processParamConfigInfoReq : listParamConfig) {
            JpmParamConfigDto jpmParamConfigDto = new JpmParamConfigDto();
            jpmParamConfigDto.setParamId(paramId);
            jpmParamConfigDto.setProcessId(processId);
            if(null !=  processParamConfigInfoReq.getStepId()) {
                setStepId(processParamConfigInfoReq.getStepId(), jpmParamConfigDto);
            }
            jpmParamConfigDto.setRequired(processParamConfigInfoReq.getRequired());
            paramConfigDtos.add(jpmParamConfigDto);
        }
        jpmParamDto.setParamConfigDtos(paramConfigDtos);
    }
    
    private void setProcessId(Long processId, JpmParamDto jpmParamDto) throws DetailException {
        JpmProcessDto jpmProcessDto = jpmProcessService.getJpmProcessDtoByProcessId(processId);
        if (null != jpmProcessDto) {
            jpmParamDto.setProcessId(processId);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProcessParamUpdateInfoReq processParamUpdateInfoReq) throws DetailException { 
        Long paramId = processParamUpdateInfoReq.getParamId();
        JpmParamDto jpmParamDto = jpmParamService.getJpmParamDtoByParamId(paramId);
        
        if(null != jpmParamDto) {
            try {
                if (null != processParamUpdateInfoReq.getProcessId()) {
                    setProcessId(processParamUpdateInfoReq.getProcessId(), jpmParamDto);
                }
                if(null != processParamUpdateInfoReq.getFieldName()) {
                    jpmParamDto.setFieldName(processParamUpdateInfoReq.getFieldName());
                }
                if(null != processParamUpdateInfoReq.getDataType()) {
                    jpmParamDto.setDataType(processParamUpdateInfoReq.getDataType());
                }
                if(null != processParamUpdateInfoReq.getFormFieldName()) {
                    jpmParamDto.setFormFieldName(processParamUpdateInfoReq.getFormFieldName());
                }
                if(null != processParamUpdateInfoReq.getListParamConfig()) {
                    setListParamConfig(processParamUpdateInfoReq.getProcessId(), paramId, processParamUpdateInfoReq.getListParamConfig(), jpmParamDto);
                }
                this.save(jpmParamDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023502_APPAPI_PROCESS_PARAM_UPDATE_INFO_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023504_APPAPI_PROCESS_PARAM_NOT_FOUND, true);
        }
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long processParamId) throws DetailException {
        JpmParamDto jpmParamDto = jpmParamService.getJpmParamDtoByParamId(processParamId);
        if(null != jpmParamDto) {
            try {
               jpmParamService.deleteById(processParamId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023503_APPAPI_PROCESS_PARAM_DELETE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023504_APPAPI_PROCESS_PARAM_NOT_FOUND, true);
        }
        
    }

    @Override
    public JpmParamDto save(JpmParamDto jpmParamDto) {
        JpmParam jpmParam = jpmParamService.saveJpmParamDto(jpmParamDto);
        jpmParamDto.setParamId(jpmParam.getId());
        return jpmParamDto;
    }

    @Override
    public List<JpmParamDto> getParamDtosByProcessId(Long processId) {
        List<JpmParamDto> jpmParamDtoList = new ArrayList<>();
        if (null != processId) {
            jpmParamDtoList = jpmParamService.getParamDtosByProcessId(processId);
        }
        return jpmParamDtoList;
    }

    @Override
    public JpmParamDto getJpmParamDtoByParamId(Long paramId) throws DetailException {
        JpmParamDto jpmParamDto = jpmParamService.getJpmParamDtoByParamId(paramId);
        if(null != jpmParamDto) {
            return jpmParamDto;
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023504_APPAPI_PROCESS_PARAM_NOT_FOUND, true);
        }
    }
}
