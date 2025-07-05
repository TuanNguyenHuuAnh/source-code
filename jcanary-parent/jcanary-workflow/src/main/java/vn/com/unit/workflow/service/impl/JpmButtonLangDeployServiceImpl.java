/*******************************************************************************
* Class        JpmButtonLangDeployServiceImpl
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
import vn.com.unit.workflow.dto.JpmButtonLangDeployDto;
import vn.com.unit.workflow.entity.JpmButtonLangDeploy;
import vn.com.unit.workflow.repository.JpmButtonLangDeployRepository;
import vn.com.unit.workflow.service.JpmButtonLangDeployService;

/**
 * JpmButtonLangDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmButtonLangDeployServiceImpl implements JpmButtonLangDeployService {

    @Autowired
    private JpmButtonLangDeployRepository jpmButtonLangDeployRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmButtonLangDeployDto getJpmButtonLangDeployDtoById(Long id) {
        JpmButtonLangDeployDto jpmButtonLangDeployDto = new JpmButtonLangDeployDto();
        if (null != id) {
            JpmButtonLangDeploy jpmButtonLangDeploy = jpmButtonLangDeployRepository.findOne(id);
            if (null != jpmButtonLangDeploy) {
                jpmButtonLangDeployDto = objectMapper.convertValue(jpmButtonLangDeploy, JpmButtonLangDeployDto.class);
            }
        }
        return jpmButtonLangDeployDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmButtonLangDeployRepository.delete(id);
                res = true;
            } catch (Exception e) {
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmButtonLangDeploy saveJpmButtonLangDeploy(JpmButtonLangDeploy jpmButtonLangDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmButtonLangDeploy.setCreatedId(userId);
        jpmButtonLangDeploy.setCreatedDate(sysDate);
        jpmButtonLangDeployRepository.create(jpmButtonLangDeploy);
        return jpmButtonLangDeploy;
    }

    @Override
    public JpmButtonLangDeploy saveJpmButtonLangDeployDto(JpmButtonLangDeployDto jpmButtonLangDeployDto) {
        JpmButtonLangDeploy jpmButtonLangDeploy = objectMapper.convertValue(jpmButtonLangDeployDto, JpmButtonLangDeploy.class);
        return this.saveJpmButtonLangDeploy(jpmButtonLangDeploy);
    }

    @Override
    public String getButtonNameInPassiveByButtonDeployIdAndLangCode(Long buttonDeployId, String langCode) {
        return jpmButtonLangDeployRepository.getButtonNameInPassiveByButtonDeployIdAndLangCode(buttonDeployId, langCode);
    }

    @Override
    public void saveJpmButtonLangDeployDtos(List<JpmButtonLangDeployDto> buttonLangDeployDtos, Long buttonDeployId) {
        if (CommonCollectionUtil.isNotEmpty(buttonLangDeployDtos) && Objects.nonNull(buttonDeployId)) {
            for (JpmButtonLangDeployDto buttonLangDeployDto : buttonLangDeployDtos) {
                buttonLangDeployDto.setButtonDeployId(buttonDeployId);
                this.saveJpmButtonLangDeployDto(buttonLangDeployDto);
            }
        }
    }

    @Override
    public List<JpmButtonLangDeployDto> getButtonLangDeployDtosByProcessDeployId(Long processDeployId) {
        return jpmButtonLangDeployRepository.getButtonLangDeployDtosByProcessDeployId(processDeployId);
    }

    @Override
    public List<JpmButtonLangDeployDto> getButtonLangDeployDtosByButtonDeployId(Long buttonDeployId) {
        return jpmButtonLangDeployRepository.getButtonLangDeployDtosByButtonDeployId(buttonDeployId);
    }

}