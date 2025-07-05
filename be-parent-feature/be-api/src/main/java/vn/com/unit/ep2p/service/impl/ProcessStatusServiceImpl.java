/*******************************************************************************
 * Class        ：ProcessStatusServiceImpl
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2021/01/13：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.exception.HandlerCastException;
import vn.com.unit.ep2p.core.req.dto.ProcessStatusAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessStatusLangInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessStatusUpdateInfoReq;
import vn.com.unit.ep2p.service.ProcessStatusService;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.dto.JpmStatusDto;
import vn.com.unit.workflow.dto.JpmStatusLangDto;
import vn.com.unit.workflow.entity.JpmStatus;
import vn.com.unit.workflow.service.JpmProcessService;
import vn.com.unit.workflow.service.JpmStatusService;

/**
 * <p>
 * ProcessStatusServiceImpl
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProcessStatusServiceImpl implements ProcessStatusService {

    @Autowired
    protected HandlerCastException handlerCastException;
    
    @Autowired
    private JpmStatusService jpmStatusService;
    
    @Autowired
    private JpmProcessService jpmProcessService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ProcessStatusAddInfoReq processStatusAddInfoReq) throws DetailException {
        Long processStatusId = null;
        try {
            JpmStatusDto jpmStatusDto = new JpmStatusDto();
            if (null != processStatusAddInfoReq.getProcessId()) {
                setProcessId(processStatusAddInfoReq.getProcessId(), jpmStatusDto);
            }
            jpmStatusDto.setStatusCode(processStatusAddInfoReq.getStatusCode());
            jpmStatusDto.setStatusName(processStatusAddInfoReq.getStatusName());
            if(CommonCollectionUtil.isNotEmpty(processStatusAddInfoReq.getStatusLangs())) {
                this.setListStatusLang(processStatusId, processStatusAddInfoReq.getStatusLangs(), jpmStatusDto);
            }
            this.save(jpmStatusDto);
            processStatusId = jpmStatusDto.getStatusId();
           
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023701_APPAPI_PROCESS_STATUS_ADD_ERROR);
        }
        return processStatusId;
    }
    
    private void setListStatusLang(Long processStatusId, List<ProcessStatusLangInfoReq> listStatusLang, JpmStatusDto jpmStatusDto) {
        List<JpmStatusLangDto> statusLangs  = new ArrayList<>();
        for (ProcessStatusLangInfoReq processStatusLangInfoReq : listStatusLang) {
            JpmStatusLangDto jpmStatusLangDto = new JpmStatusLangDto();
            jpmStatusLangDto.setStatusId(processStatusId);
            jpmStatusLangDto.setLangCode(processStatusLangInfoReq.getLangCode());
            jpmStatusLangDto.setStatusName(processStatusLangInfoReq.getStatusName());
            statusLangs.add(jpmStatusLangDto);
        }
        jpmStatusDto.setStatusLangs(statusLangs);
    }
    
    private void setProcessId(Long processId, JpmStatusDto jpmStatusDto) throws DetailException {
        JpmProcessDto jpmProcessDto = jpmProcessService.getJpmProcessDtoByProcessId(processId);
        if (null != jpmProcessDto) {
            jpmStatusDto.setProcessId(processId);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProcessStatusUpdateInfoReq processStatusUpdateInfoReq) throws DetailException { 
        Long processStatusId = processStatusUpdateInfoReq.getStatusId();
        JpmStatusDto jpmStatusDto = jpmStatusService.getStatusDtoByStatusId(processStatusId);
        
        if(null != jpmStatusDto) {
            try {
                if (null != processStatusUpdateInfoReq.getProcessId()) {
                    setProcessId(processStatusUpdateInfoReq.getProcessId(), jpmStatusDto);
                }
                jpmStatusDto.setStatusName(processStatusUpdateInfoReq.getStatusName());
                if(CommonCollectionUtil.isNotEmpty(processStatusUpdateInfoReq.getStatusLangs())) {
                    this.setListStatusLang(processStatusId, processStatusUpdateInfoReq.getStatusLangs(), jpmStatusDto);
                }
                this.save(jpmStatusDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023702_APPAPI_PROCESS_STATUS_UPDATE_INFO_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023704_APPAPI_PROCESS_STATUS_NOT_FOUND, true);
        }
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long processStatusId) throws DetailException {
        JpmStatusDto jpmStatusDto =  jpmStatusService.getStatusDtoByStatusId(processStatusId);
        if(null != jpmStatusDto) {
            try {
                jpmStatusService.deleteById(processStatusId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023703_APPAPI_PROCESS_STATUS_DELETE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023704_APPAPI_PROCESS_STATUS_NOT_FOUND, true);
        }
        
    }

    @Override
    public JpmStatusDto save(JpmStatusDto jpmStatusDto) {
        JpmStatus jpmStatus = jpmStatusService.saveJpmStatusDto(jpmStatusDto);
        jpmStatusDto.setStatusId(jpmStatus.getId());
        return jpmStatusDto;
    }

    @Override
    public List<JpmStatusDto> getStatusDtosByProcessId(Long processId) {
        List<JpmStatusDto> jpmStatusDtoList = new ArrayList<>();
        if (null != processId) {
            jpmStatusDtoList = jpmStatusService.getStatusDtosByProcessId(processId);
        }
        return jpmStatusDtoList;
    }
    
    @Override
    public List<JpmStatusDto> getStatusDtosByBusinessCode(String businessCode) {
        List<JpmStatusDto> jpmStatusDtoList = new ArrayList<>();
        if (StringUtils.isNotBlank(businessCode)) {
            jpmStatusDtoList = jpmStatusService.getStatusDtosByBusinessCode(businessCode);
        }
        return jpmStatusDtoList;
    }

    @Override
    public JpmStatusDto getStatusDtoByStatusId(Long statusId) throws DetailException {
        JpmStatusDto jpmStatusDto =  jpmStatusService.getStatusDtoByStatusId(statusId);
        if(null != jpmStatusDto) {
            return jpmStatusDto;
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023704_APPAPI_PROCESS_STATUS_NOT_FOUND, true);
        }
    }
}
