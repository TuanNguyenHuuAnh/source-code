/*******************************************************************************
* Class        JpmParamServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonMapUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmParamConfigDto;
import vn.com.unit.workflow.dto.JpmParamDto;
import vn.com.unit.workflow.entity.JpmParam;
import vn.com.unit.workflow.repository.JpmParamRepository;
import vn.com.unit.workflow.service.JpmHiParamService;
import vn.com.unit.workflow.service.JpmParamConfigService;
import vn.com.unit.workflow.service.JpmParamService;

/**
 * JpmParamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmParamServiceImpl implements JpmParamService {

    @Autowired
    private JpmParamRepository jpmParamRepository;

    @Autowired
    private JpmParamConfigService jpmParamConfigService;
    
    @Autowired
    private JpmHiParamService jpmHiParamService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmParamDto getJpmParamDtoById(Long id) {
        JpmParamDto jpmParamDto = null;
        if (null != id) {
            JpmParam jpmParam = jpmParamRepository.findOne(id);
            if (null != jpmParam && 0L == jpmParam.getDeletedId()) {
                jpmParamDto = objectMapper.convertValue(jpmParam, JpmParamDto.class);
                jpmParamDto.setParamId(id);

                List<JpmParamConfigDto> paramConfigDtos = jpmParamConfigService
                        .getParamConfigDtosByProcessIdAndParamId(jpmParam.getProcessId(), id);
                jpmParamDto.setParamConfigDtos(paramConfigDtos);
            }
        }
        return jpmParamDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmParam jpmParam = jpmParamRepository.findOne(id);
            if (null != jpmParam && Long.valueOf(0L).equals(jpmParam.getDeletedId())) {
                jpmParam.setDeletedId(userId);
                jpmParam.setDeletedDate(sysDate);
                jpmParamRepository.update(jpmParam);
                res = true;
            }
            
            // delete param config
            jpmParamConfigService.deleteJpmParamConfigByParamId(id);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmParam saveJpmParam(JpmParam jpmParam) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmParam.getId();
        if (null != id) {
            JpmParam oldJpmParam = jpmParamRepository.findOne(id);
            if (null != oldJpmParam && (null == oldJpmParam.getDeletedId() || 0 == oldJpmParam.getDeletedId()) ) {
                jpmParam.setCreatedId(oldJpmParam.getCreatedId());
                jpmParam.setCreatedDate(oldJpmParam.getCreatedDate());
                jpmParam.setUpdatedId(userId);
                jpmParam.setUpdatedDate(sysDate);
                jpmParamRepository.update(jpmParam);
            }
        } else {
            jpmParam.setCreatedId(userId);
            jpmParam.setCreatedDate(sysDate);
            jpmParam.setUpdatedId(userId);
            jpmParam.setUpdatedDate(sysDate);
            jpmParamRepository.create(jpmParam);
        }
        
        // save history
        jpmHiParamService.saveJpmHiParam(jpmParam);
        
        return jpmParam;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmParam saveJpmParamDto(JpmParamDto jpmParamDto) {
        JpmParam jpmParam = objectMapper.convertValue(jpmParamDto, JpmParam.class);
        jpmParam.setId(jpmParamDto.getParamId());
        this.saveJpmParam(jpmParam);
        
        Long paramId = jpmParam.getId();
        jpmParamDto.setParamId(paramId);
        
        //saveConfig
        List<JpmParamConfigDto> paramConfigDtos = jpmParamDto.getParamConfigDtos();
        if (CommonCollectionUtil.isNotEmpty(paramConfigDtos)) {
            Long processId = jpmParamDto.getProcessId();
            jpmParamConfigService.saveJpmParamConfigDtosByProcessId(paramConfigDtos, processId, paramId);
        }
        
        return jpmParam;
    }
    
    @Override
    public List<JpmParamDto> getParamDtosByProcessId(Long processId) {
        List<JpmParamDto> paramDtos = jpmParamRepository.getParamDtosByProcessId(processId);
        if (CommonCollectionUtil.isNotEmpty(paramDtos)) {
            List<JpmParamConfigDto> paramConfigDtos = jpmParamConfigService.getParamConfigDtosByProcessId(processId);
            if (CommonCollectionUtil.isNotEmpty(paramConfigDtos)) {
                Map<Long, List<JpmParamConfigDto>> paramConfigDtosMap = paramConfigDtos.stream()
                        .collect(Collectors.groupingBy(JpmParamConfigDto::getParamId));
                paramDtos.forEach(paramDto -> paramDto.setParamConfigDtos(paramConfigDtosMap.get(paramDto.getParamId())));
            }
        }
        return paramDtos;
    }

    @Override
    public JpmParamDto getJpmParamDtoByParamId(Long paramId) {
        return jpmParamRepository.getJpmParamDtoByParamId(paramId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveParamDtosByProcessId(@NotNull List<JpmParamDto> paramDtos, @NotNull Long processId) {
        List<JpmParamDto> currentParamDtos = this.getParamDtosByProcessId(processId);
        Map<String, JpmParamDto> paramMap = CommonCollectionUtil.isEmpty(currentParamDtos) ? new HashMap<>()
                : currentParamDtos.stream().collect(Collectors.toMap(JpmParamDto::getFieldName, param -> param));
        for (JpmParamDto paramDto : paramDtos) {
            String param = paramDto.getFieldName();
            JpmParamDto currentParamDto = paramMap.remove(param);
            if (Objects.isNull(currentParamDto)) {
                paramDto.setParamId(null);
                paramDto.setProcessId(processId);
                this.saveJpmParamDto(paramDto);
            }
        }
        
        // DELETE
        if (CommonMapUtil.isNotEmpty(paramMap)) {
            for(Map.Entry<String, JpmParamDto> entry : paramMap.entrySet()) {
                Long paramId = entry.getValue().getParamId();
                this.deleteById(paramId);
            }
        }
    }

}