/*******************************************************************************
 * Class        ：ProcessButtonServiceImpl
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
import vn.com.unit.ep2p.core.req.dto.ProcessButtonAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessButtonLangInfoReq;
import vn.com.unit.ep2p.core.req.dto.ProcessButtonUpdateInfoReq;
import vn.com.unit.ep2p.service.ProcessButtonService;
import vn.com.unit.workflow.dto.JpmButtonDto;
import vn.com.unit.workflow.dto.JpmButtonLangDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.entity.JpmButton;
import vn.com.unit.workflow.service.JpmButtonService;
import vn.com.unit.workflow.service.JpmProcessService;

/**
 * <p>
 * ProcessButtonServiceImpl
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProcessButtonServiceImpl implements ProcessButtonService {

    @Autowired
    protected HandlerCastException handlerCastException;
    
    @Autowired
    private JpmButtonService jpmButtonService;
    
    @Autowired
    private JpmProcessService jpmProcessService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long create(ProcessButtonAddInfoReq processButtonAddInfoReq) throws DetailException {
        Long processButtonId = null;
        try {
            JpmButtonDto jpmButtonDto = new JpmButtonDto();

            if (null != processButtonAddInfoReq.getProcessId()) {
                setProcessId(processButtonAddInfoReq.getProcessId(), jpmButtonDto);
            }
            jpmButtonDto.setButtonCode(processButtonAddInfoReq.getButtonCode());
            jpmButtonDto.setButtonText(processButtonAddInfoReq.getButtonText());
            jpmButtonDto.setButtonValue(processButtonAddInfoReq.getButtonValue());
            jpmButtonDto.setButtonClass(processButtonAddInfoReq.getButtonClass());
            jpmButtonDto.setButtonType(processButtonAddInfoReq.getButtonType());
            jpmButtonDto.setDisplayOrder(processButtonAddInfoReq.getDisplayOrder());
            if(CommonCollectionUtil.isNotEmpty(processButtonAddInfoReq.getButtonLangs())) {
                this.setListButtonLang(processButtonId, processButtonAddInfoReq.getButtonLangs(), jpmButtonDto);
            }
            this.save(jpmButtonDto);
            processButtonId = jpmButtonDto.getButtonId();
           
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023601_APPAPI_PROCESS_BUTTON_ADD_ERROR);
        }
        return processButtonId;
    }
    
    private void setListButtonLang(Long processButtonId, List<ProcessButtonLangInfoReq> listButtonLang, JpmButtonDto jpmButtonDto) {
        List<JpmButtonLangDto> buttonLangs  = new ArrayList<>();
        for (ProcessButtonLangInfoReq processButtonLangInfoReq : listButtonLang) {
            JpmButtonLangDto jpmButtonLangDto = new JpmButtonLangDto();
            jpmButtonLangDto.setButtonId(processButtonId);
            jpmButtonLangDto.setLangCode(processButtonLangInfoReq.getLangCode());
            jpmButtonLangDto.setButtonName(processButtonLangInfoReq.getButtonName());
            jpmButtonLangDto.setButtonNameInPassive(processButtonLangInfoReq.getButtonNameInPassive());
            buttonLangs.add(jpmButtonLangDto);
        }
        jpmButtonDto.setButtonLangs(buttonLangs);
    }
    private void setProcessId(Long processId, JpmButtonDto jpmButtonDto) throws DetailException {
        JpmProcessDto jpmProcessDto = jpmProcessService.getJpmProcessDtoByProcessId(processId);
        if (null != jpmProcessDto) {
            jpmButtonDto.setProcessId(processId);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021405_APPAPI_PROCESS_NOT_FOUND, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProcessButtonUpdateInfoReq processButtonUpdateInfoReq) throws DetailException { 
        Long buttonId = processButtonUpdateInfoReq.getButtonId();
        JpmButtonDto jpmButtonDto = jpmButtonService.getJpmButtonDtoByButtonId(buttonId);
        
        if(null != jpmButtonDto) {
            try {
                if (null != processButtonUpdateInfoReq.getProcessId()) {
                    setProcessId(processButtonUpdateInfoReq.getProcessId(), jpmButtonDto);
                }
                jpmButtonDto.setButtonText(processButtonUpdateInfoReq.getButtonText());
                jpmButtonDto.setButtonValue(processButtonUpdateInfoReq.getButtonValue());
                jpmButtonDto.setButtonClass(processButtonUpdateInfoReq.getButtonClass());
                jpmButtonDto.setButtonType(processButtonUpdateInfoReq.getButtonType());
                jpmButtonDto.setDisplayOrder(processButtonUpdateInfoReq.getDisplayOrder());
                if(CommonCollectionUtil.isNotEmpty(processButtonUpdateInfoReq.getButtonLangs())) {
                    this.setListButtonLang(buttonId, processButtonUpdateInfoReq.getButtonLangs(), jpmButtonDto);
                }
                this.save(jpmButtonDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023602_APPAPI_PROCESS_BUTTON_UPDATE_INFO_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023604_APPAPI_PROCESS_BUTTON_NOT_FOUND, true);
        }
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long processButtonId) throws DetailException {
        JpmButtonDto jpmButtonDto = jpmButtonService.getJpmButtonDtoByButtonId(processButtonId);
        if(null != jpmButtonDto) {
            try {
                jpmButtonService.deleteById(processButtonId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023603_APPAPI_PROCESS_BUTTON_DELETE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023604_APPAPI_PROCESS_BUTTON_NOT_FOUND, true);
        }
        
    }

    @Override
    public JpmButtonDto save(JpmButtonDto jpmButtonDto) {
        JpmButton jpmButton = jpmButtonService.saveJpmButtonDto(jpmButtonDto);
        jpmButtonDto.setButtonId(jpmButton.getId());
        return jpmButtonDto;
    }

    @Override
    public List<JpmButtonDto> getButtonDtosByProcessId(Long processId) {
        List<JpmButtonDto> jpmButtonDtoList = new ArrayList<>();
        if (null != processId) {
            jpmButtonDtoList = jpmButtonService.getButtonDtosByProcessId(processId);
        }
        return jpmButtonDtoList;
    }

    @Override
    public JpmButtonDto getJpmButtonDtoByButtonId(Long buttonId) throws DetailException{
        JpmButtonDto jpmButtonDto = jpmButtonService.getJpmButtonDtoByButtonId( buttonId);
        if(null != jpmButtonDto) {
            return jpmButtonDto;
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023604_APPAPI_PROCESS_BUTTON_NOT_FOUND, true);
        }
    }
}
