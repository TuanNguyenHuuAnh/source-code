/*******************************************************************************
* Class        JpmParamConfigDeployServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmParamConfigDeployDto;
import vn.com.unit.workflow.entity.JpmParamConfigDeploy;
import vn.com.unit.workflow.repository.JpmParamConfigDeployRepository;
import vn.com.unit.workflow.service.JpmParamConfigDeployService;

/**
 * JpmParamConfigDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmParamConfigDeployServiceImpl implements JpmParamConfigDeployService {

    @Autowired
    private JpmParamConfigDeployRepository jpmParamConfigDeployRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmParamConfigDeployDto getJpmParamConfigDeployDtoById(Long id) {
        JpmParamConfigDeployDto jpmParamConfigDeployDto = new JpmParamConfigDeployDto();
        if (null != id) {
            JpmParamConfigDeploy jpmParamConfigDeploy = jpmParamConfigDeployRepository.findOne(id);
            if (null != jpmParamConfigDeploy) {
                jpmParamConfigDeployDto = objectMapper.convertValue(jpmParamConfigDeploy, JpmParamConfigDeployDto.class);
            }
        }
        return jpmParamConfigDeployDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmParamConfigDeployRepository.delete(id);
                res = true;
            } catch (Exception e) {
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmParamConfigDeploy saveJpmParamConfigDeploy(JpmParamConfigDeploy jpmParamConfigDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmParamConfigDeploy.setCreatedId(userId);
        jpmParamConfigDeploy.setCreatedDate(sysDate);
        jpmParamConfigDeployRepository.create(jpmParamConfigDeploy);
        return jpmParamConfigDeploy;
    }

    @Override
    public JpmParamConfigDeploy saveJpmParamConfigDeployDto(JpmParamConfigDeployDto jpmParamConfigDeployDto) {
        JpmParamConfigDeploy jpmParamConfigDeploy = objectMapper.convertValue(jpmParamConfigDeployDto, JpmParamConfigDeploy.class);
        return this.saveJpmParamConfigDeploy(jpmParamConfigDeploy);
    }

    @Override
    public void saveJpmParamConfigDeployDtos(List<JpmParamConfigDeployDto> paramConfigDeployDtos, Long paramDeployId,
            Long processDeployId) {
        if (CommonCollectionUtil.isNotEmpty(paramConfigDeployDtos) && Objects.nonNull(processDeployId)) {
            for (JpmParamConfigDeployDto paramConfigDeployDto : paramConfigDeployDtos) {
                paramConfigDeployDto.setProcessDeployId(processDeployId);
                paramConfigDeployDto.setParamDeployId(paramDeployId);
                this.saveJpmParamConfigDeployDto(paramConfigDeployDto);
            }
        }
    }

    @Override
    public List<JpmParamConfigDeployDto> getParamConfigDeployDtosByProcessDeployId(Long processDeployId) {
        return jpmParamConfigDeployRepository.getParamConfigDeployDtosByProcessDeployId(processDeployId);
    }

    @Override
    public List<JpmParamConfigDeployDto> getParamConfigDeployDtosByParamDeployId(Long paramDeployId) {
        return jpmParamConfigDeployRepository.getParamConfigDeployDtosByParamDeployId(paramDeployId);
    }

}