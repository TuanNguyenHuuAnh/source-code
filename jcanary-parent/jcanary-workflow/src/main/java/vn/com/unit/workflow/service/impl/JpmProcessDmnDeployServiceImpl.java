/*******************************************************************************
* Class        JpmProcessDmnDeployServiceImpl
* Created date 2021/03/15
* Lasted date  2021/03/15
* Author       KhuongTH
* Change log   2021/03/15 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmProcessDmnDeployDto;
import vn.com.unit.workflow.entity.JpmProcessDmnDeploy;
import vn.com.unit.workflow.repository.JpmProcessDmnDeployRepository;
import vn.com.unit.workflow.service.JpmProcessDmnDeployService;

/**
 * JpmProcessDmnDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmProcessDmnDeployServiceImpl implements JpmProcessDmnDeployService {

    @Autowired
    private JpmProcessDmnDeployRepository jpmProcessDmnDeployRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmProcessDmnDeployDto getJpmProcessDmnDeployDtoById(Long id) {
        JpmProcessDmnDeployDto jpmProcessDmnDeployDto = new JpmProcessDmnDeployDto();
        if (null != id) {
            JpmProcessDmnDeploy jpmProcessDmnDeploy = jpmProcessDmnDeployRepository.findOne(id);
            if (null != jpmProcessDmnDeploy) {
                jpmProcessDmnDeployDto = objectMapper.convertValue(jpmProcessDmnDeploy, JpmProcessDmnDeployDto.class);
            }
        }
        return jpmProcessDmnDeployDto;
    }

    @Override
    public JpmProcessDmnDeploy saveJpmProcessDmnDeploy(JpmProcessDmnDeploy jpmProcessDmnDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmProcessDmnDeploy.getId();
        if (null != id) {
            JpmProcessDmnDeploy oldJpmProcessDmnDeploy = jpmProcessDmnDeployRepository.findOne(id);
            if (null != oldJpmProcessDmnDeploy) {
                jpmProcessDmnDeploy.setCreatedId(oldJpmProcessDmnDeploy.getCreatedId());
                jpmProcessDmnDeploy.setCreatedDate(oldJpmProcessDmnDeploy.getCreatedDate());
                jpmProcessDmnDeployRepository.update(jpmProcessDmnDeploy);
            }
        } else {
            jpmProcessDmnDeploy.setCreatedId(userId);
            jpmProcessDmnDeploy.setCreatedDate(sysDate);
            jpmProcessDmnDeployRepository.create(jpmProcessDmnDeploy);
        }
        return jpmProcessDmnDeploy;
    }

    @Override
    public JpmProcessDmnDeploy saveJpmProcessDmnDeployDto(JpmProcessDmnDeployDto jpmProcessDmnDeployDto) {
        JpmProcessDmnDeploy jpmProcessDmnDeploy = objectMapper.convertValue(jpmProcessDmnDeployDto, JpmProcessDmnDeploy.class);
        return this.saveJpmProcessDmnDeploy(jpmProcessDmnDeploy);
    }

    @Override
    public List<JpmProcessDmnDeployDto> getJpmProcessDmnDeployDtosByProcessDeployId(Long processDeployId) {
        List<JpmProcessDmnDeployDto> processDmnDeployDtos = new ArrayList<>();
        if (Objects.nonNull(processDeployId)) {
            processDmnDeployDtos = jpmProcessDmnDeployRepository.getJpmProcessDmnDeployDtosByProcessDeployId(processDeployId);
        }
        return processDmnDeployDtos;
    }

}