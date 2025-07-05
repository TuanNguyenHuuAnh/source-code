/*******************************************************************************
* Class        JpmParamConfigServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmParamConfigDto;
import vn.com.unit.workflow.entity.JpmParamConfig;
import vn.com.unit.workflow.repository.JpmParamConfigRepository;
import vn.com.unit.workflow.service.JpmParamConfigService;

/**
 * JpmParamConfigServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmParamConfigServiceImpl implements JpmParamConfigService {

    @Autowired
    private JpmParamConfigRepository jpmParamConfigRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmParamConfigRepository.delete(id);
                res = true;
            } catch (Exception e) {
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmParamConfig saveJpmParamConfig(JpmParamConfig jpmParamConfig) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmParamConfig.setCreatedId(userId);
        jpmParamConfig.setCreatedDate(sysDate);
        jpmParamConfigRepository.create(jpmParamConfig);
        return jpmParamConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmParamConfig saveJpmParamConfigDto(JpmParamConfigDto jpmParamConfigDto) {
        JpmParamConfig jpmParamConfig = objectMapper.convertValue(jpmParamConfigDto, JpmParamConfig.class);
        this.saveJpmParamConfig(jpmParamConfig);
        return jpmParamConfig;
    }

    @Override
    public List<JpmParamConfigDto> getParamConfigDtosByProcessId(Long processId) {
        return jpmParamConfigRepository.getParamConfigDtosByProcessId(processId);
    }

    @Override
    public void saveParamConfigDtosByParamId(@NotNull List<JpmParamConfigDto> paramConfigDtos, @NotNull Long paramId) {
        for (JpmParamConfigDto paramConfigDto : paramConfigDtos) {
            paramConfigDto.setParamId(paramId);
            this.saveJpmParamConfigDto(paramConfigDto);
        }
    }

    @Override
    public List<JpmParamConfigDto> getParamConfigDtosByProcessIdAndParamId(Long processId, Long paramId) {
        return jpmParamConfigRepository.getParamConfigDtosByProcessIdAndParamId(processId, paramId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJpmParamConfigDtosByProcessId(List<JpmParamConfigDto> paramConfigDtos, Long processId, Long paramId) {
        //delete old
        this.deleteJpmParamConfigByParamId(paramId);
        //save new
        for (JpmParamConfigDto paramConfigDto : paramConfigDtos) {
            paramConfigDto.setProcessId(processId);
            paramConfigDto.setParamId(paramId);
            this.saveJpmParamConfigDto(paramConfigDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJpmParamConfigByParamId(Long paramId) {
        jpmParamConfigRepository.deleteJpmParamConfigByParamId(paramId);
    }

}