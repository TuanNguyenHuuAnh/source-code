/*******************************************************************************
 * Class        ：ProcessPermissionServiceImpl
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
import vn.com.unit.ep2p.core.req.dto.ProcessPermissionAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessPermissionUpdateInfoReq;
import vn.com.unit.ep2p.service.ProcessPermissionService;
import vn.com.unit.workflow.dto.JpmPermissionDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.entity.JpmPermission;
import vn.com.unit.workflow.service.JpmPermissionService;
import vn.com.unit.workflow.service.JpmProcessService;

/**
 * <p>
 * ProcessPermissionServiceImpl
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProcessPermissionServiceImpl implements ProcessPermissionService {

    @Autowired
    protected HandlerCastException handlerCastException;
    
    @Autowired
    private JpmPermissionService jpmPermissionService;
    
    @Autowired
    private JpmProcessService jpmProcessService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ProcessPermissionAddInfoReq processPermissionAddInfoReq) throws DetailException {
        Long processPermissionId = null;
        try {
            JpmPermissionDto jpmPermissionDto = new JpmPermissionDto();

            if (null != processPermissionAddInfoReq.getProcessId()) {
                setProcessId(processPermissionAddInfoReq.getProcessId(), jpmPermissionDto);
            }
            jpmPermissionDto.setPermissionCode(processPermissionAddInfoReq.getPermissionCode());
            jpmPermissionDto.setPermissionName(processPermissionAddInfoReq.getPermissionName());
            jpmPermissionDto.setPermissionType(processPermissionAddInfoReq.getPermissionType());
            this.save(jpmPermissionDto);
            processPermissionId = jpmPermissionDto.getPermissionId();
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023801_APPAPI_PROCESS_PERMISSION_ADD_ERROR);
        }
        return processPermissionId;
    }
    
    private void setProcessId(Long processId, JpmPermissionDto jpmPermissionDto) throws DetailException {
        JpmProcessDto jpmProcessDto = jpmProcessService.getJpmProcessDtoByProcessId(processId);
        if (null != jpmProcessDto) {
            jpmPermissionDto.setProcessId(processId);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProcessPermissionUpdateInfoReq processPermissionUpdateInfoReq) throws DetailException { 
        Long permissionId = processPermissionUpdateInfoReq.getPermissionId();
        JpmPermissionDto jpmPermissionDto = jpmPermissionService.getJpmPermissionDtoByPermissionId(permissionId);
        
        if(null != jpmPermissionDto) {
            try {
                if (null != processPermissionUpdateInfoReq.getProcessId()) {
                    setProcessId(processPermissionUpdateInfoReq.getProcessId(), jpmPermissionDto);
                }
                jpmPermissionDto.setPermissionName(processPermissionUpdateInfoReq.getPermissionName());
                jpmPermissionDto.setPermissionType(processPermissionUpdateInfoReq.getPermissionType());
                this.save(jpmPermissionDto);
            } catch (DetailException de) {
                throw de;
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023802_APPAPI_PROCESS_PERMISSION_UPDATE_INFO_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023804_APPAPI_PROCESS_PERMISSION_NOT_FOUND, true);
        }
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long processPermissionId) throws DetailException {
        JpmPermissionDto jpmPermissionDto = jpmPermissionService.getJpmPermissionDtoByPermissionId(processPermissionId);
        if(null != jpmPermissionDto) {
            try {
               jpmPermissionService.deleteById(processPermissionId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023803_APPAPI_PROCESS_PERMISSION_DELETE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023804_APPAPI_PROCESS_PERMISSION_NOT_FOUND, true);
        }
        
    }

    @Override
    public JpmPermissionDto save(JpmPermissionDto jpmPermissionDto) {
        JpmPermission jpmPermission = jpmPermissionService.saveJpmPermissionDto(jpmPermissionDto);
        jpmPermissionDto.setPermissionId(jpmPermission.getId());
        return jpmPermissionDto;
    }

    @Override
    public List<JpmPermissionDto> getPermissionDtosByProcessId(Long processId) {
        
        List<JpmPermissionDto> jpmPermissionDtoList = new ArrayList<>();
        if (null != processId) {
            jpmPermissionDtoList = jpmPermissionService.getPermissionDtosByProcessId(processId);
        }
        return jpmPermissionDtoList;
    }

    @Override
    public JpmPermissionDto getJpmPermissionDtoByPermissionId(Long permissionId) throws DetailException {
        JpmPermissionDto jpmPermissionDto = jpmPermissionService.getJpmPermissionDtoByPermissionId(permissionId);
        if(null != jpmPermissionDto) {
            return jpmPermissionDto;
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023804_APPAPI_PROCESS_PERMISSION_NOT_FOUND, true);
        }
    }
}
