/*******************************************************************************
* Class        JpmParamDeployServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmParamConfigDeployDto;
import vn.com.unit.workflow.dto.JpmParamDeployDto;
import vn.com.unit.workflow.dto.LanguageMapDto;
import vn.com.unit.workflow.entity.JpmParamDeploy;
import vn.com.unit.workflow.repository.JpmParamDeployRepository;
import vn.com.unit.workflow.service.JpmParamConfigDeployService;
import vn.com.unit.workflow.service.JpmParamDeployService;

/**
 * JpmParamDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmParamDeployServiceImpl implements JpmParamDeployService {

    @Autowired
    private JpmParamDeployRepository jpmParamDeployRepository;

    @Autowired
    private JpmParamConfigDeployService jpmParamConfigDeployService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmParamDeployDto getJpmParamDeployDtoById(Long id) {
        JpmParamDeployDto jpmParamDeployDto = new JpmParamDeployDto();
        if (null != id) {
            JpmParamDeploy jpmParamDeploy = jpmParamDeployRepository.findOne(id);
            if (Objects.nonNull(jpmParamDeploy) && 0L == jpmParamDeploy.getDeletedId()) {
                jpmParamDeployDto = objectMapper.convertValue(jpmParamDeploy, JpmParamDeployDto.class);
                jpmParamDeployDto.setParamDeployId(id);
                
                List<JpmParamConfigDeployDto> paramConfigDeployDtos = jpmParamConfigDeployService.getParamConfigDeployDtosByParamDeployId(id);
                jpmParamDeployDto.setParamConfigDtos(paramConfigDeployDtos);
            }
        }
        return jpmParamDeployDto;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmParamDeploy jpmParamDeploy = jpmParamDeployRepository.findOne(id);
            if (null != jpmParamDeploy) {
                jpmParamDeploy.setDeletedId(userId);
                jpmParamDeploy.setDeletedDate(sysDate);
                jpmParamDeployRepository.update(jpmParamDeploy);
                res = true;
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmParamDeploy saveJpmParamDeploy(JpmParamDeploy jpmParamDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmParamDeploy.getId();
        if (null != id) {
            JpmParamDeploy oldJpmParamDeploy = jpmParamDeployRepository.findOne(id);
            if (null != oldJpmParamDeploy) {
                jpmParamDeploy.setCreatedId(oldJpmParamDeploy.getCreatedId());
                jpmParamDeploy.setCreatedDate(oldJpmParamDeploy.getCreatedDate());
                jpmParamDeploy.setUpdatedId(userId);
                jpmParamDeploy.setUpdatedDate(sysDate);
                jpmParamDeployRepository.update(jpmParamDeploy);
            }
        } else {
            jpmParamDeploy.setCreatedId(userId);
            jpmParamDeploy.setCreatedDate(sysDate);
            jpmParamDeploy.setUpdatedId(userId);
            jpmParamDeploy.setUpdatedDate(sysDate);
            jpmParamDeployRepository.create(jpmParamDeploy);
        }
        return jpmParamDeploy;
    }

    @Override
    public JpmParamDeploy saveJpmParamDeployDto(JpmParamDeployDto jpmParamDeployDto) {
        JpmParamDeploy jpmParamDeploy = objectMapper.convertValue(jpmParamDeployDto, JpmParamDeploy.class);
        jpmParamDeploy = this.saveJpmParamDeploy(jpmParamDeploy);

        Long paramDeployId = jpmParamDeploy.getId();
        Long processDeployId = jpmParamDeploy.getProcessDeployId();
        List<JpmParamConfigDeployDto> paramConfigDeployDtos = jpmParamDeployDto.getParamConfigDtos();
        if (CommonCollectionUtil.isNotEmpty(paramConfigDeployDtos)) {
            jpmParamConfigDeployService.saveJpmParamConfigDeployDtos(paramConfigDeployDtos, paramDeployId, processDeployId);
        }

        return this.saveJpmParamDeploy(jpmParamDeploy);
    }

    @Override
    public List<LanguageMapDto> getListNameInPassiveByButtonId(Long buttonId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveJpmParamDeployDtos(List<JpmParamDeployDto> paramDeployDtos, Long processDeployId) {
        if (CommonCollectionUtil.isNotEmpty(paramDeployDtos) && Objects.nonNull(processDeployId)) {
            for (JpmParamDeployDto paramDeployDto : paramDeployDtos) {
                paramDeployDto.setProcessDeployId(processDeployId);
                this.saveJpmParamDeployDto(paramDeployDto);
            }
        }
    }

    @Override
    public List<JpmParamDeployDto> getParamDeployDtosByProcessDeployId(Long processDeployId) {
        List<JpmParamDeployDto> paramDeployDtos = jpmParamDeployRepository.getParamDeployDtosByProcessDeployId(processDeployId);
        if (CommonCollectionUtil.isNotEmpty(paramDeployDtos)) {
            List<JpmParamConfigDeployDto> paramConfigDtos = jpmParamConfigDeployService.getParamConfigDeployDtosByProcessDeployId(processDeployId);
            if (CommonCollectionUtil.isNotEmpty(paramConfigDtos)) {
                Map<Long, List<JpmParamConfigDeployDto>> paramConfigDeployDtosMap = paramConfigDtos.stream()
                        .collect(Collectors.groupingBy(JpmParamConfigDeployDto::getParamDeployId));
                paramDeployDtos.forEach(paramDto -> paramDto.setParamConfigDtos(paramConfigDeployDtosMap.get(paramDto.getParamDeployId())));
            }
        }
        return paramDeployDtos;
    }
}