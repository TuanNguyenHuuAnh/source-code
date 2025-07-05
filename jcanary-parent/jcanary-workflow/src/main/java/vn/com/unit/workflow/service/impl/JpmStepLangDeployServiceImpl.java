/*******************************************************************************
* Class        JpmStepLangDeployServiceImpl
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
import vn.com.unit.workflow.dto.JpmStepLangDeployDto;
import vn.com.unit.workflow.entity.JpmStepLangDeploy;
import vn.com.unit.workflow.repository.JpmStepLangDeployRepository;
import vn.com.unit.workflow.service.JpmStepLangDeployService;

/**
 * JpmStepLangDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStepLangDeployServiceImpl implements JpmStepLangDeployService {

    @Autowired
    private JpmStepLangDeployRepository jpmStepLangDeployRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmStepLangDeploy saveJpmStepLangDeploy(JpmStepLangDeploy jpmStepLangDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmStepLangDeploy.setCreatedId(userId);
        jpmStepLangDeploy.setCreatedDate(sysDate);
        jpmStepLangDeployRepository.create(jpmStepLangDeploy);
        return jpmStepLangDeploy;
    }

    @Override
    public JpmStepLangDeploy saveJpmStepLangDeployDto(JpmStepLangDeployDto jpmStepLangDeployDto) {
        JpmStepLangDeploy jpmStepLangDeploy = objectMapper.convertValue(jpmStepLangDeployDto, JpmStepLangDeploy.class);
        return this.saveJpmStepLangDeploy(jpmStepLangDeploy);
    }

    @Override
    public void saveJpmStepLangDeployDtos(List<JpmStepLangDeployDto> stepLangDeployDtos, Long stepDeployId) {
        if (CommonCollectionUtil.isNotEmpty(stepLangDeployDtos) && Objects.nonNull(stepDeployId)) {
            for (JpmStepLangDeployDto stepLangDeployDto : stepLangDeployDtos) {
                stepLangDeployDto.setStepDeployId(stepDeployId);
                this.saveJpmStepLangDeployDto(stepLangDeployDto);
            }
        }
    }

    @Override
    public List<JpmStepLangDeployDto> getStepLangDeployDtosByProcessDeployId(Long processDeployId) {
        return jpmStepLangDeployRepository.getStepLangDeployDtosByProcessDeployId(processDeployId);
    }

    @Override
    public List<JpmStepLangDeployDto> getStepLangDeployDtosByStepDeployId(Long stepDeployId) {
        return jpmStepLangDeployRepository.getStepLangDeployDtosByStepDeployId(stepDeployId);
    }

}