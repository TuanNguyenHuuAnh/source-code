/*******************************************************************************
* Class        JpmStatusLangDeployServiceImpl
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
import vn.com.unit.workflow.dto.JpmStatusLangDeployDto;
import vn.com.unit.workflow.entity.JpmStatusLangDeploy;
import vn.com.unit.workflow.repository.JpmStatusLangDeployRepository;
import vn.com.unit.workflow.service.JpmStatusLangDeployService;

/**
 * JpmStatusLangDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStatusLangDeployServiceImpl implements JpmStatusLangDeployService {

    @Autowired
    private JpmStatusLangDeployRepository jpmStatusLangDeployRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmStatusLangDeployDto getJpmStatusLangDeployDtoById(Long id) {
        JpmStatusLangDeployDto jpmStatusLangDeployDto = new JpmStatusLangDeployDto();
        if (null != id) {
            JpmStatusLangDeploy jpmStatusLangDeploy = jpmStatusLangDeployRepository.findOne(id);
            if (null != jpmStatusLangDeploy) {
                jpmStatusLangDeployDto = objectMapper.convertValue(jpmStatusLangDeploy, JpmStatusLangDeployDto.class);
            }
        }
        return jpmStatusLangDeployDto;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmStatusLangDeployRepository.delete(id);
                res = true;
            } catch (Exception e) {
            }
        }
        return res;
    }

    @Override
    public JpmStatusLangDeploy saveJpmStatusLangDeploy(JpmStatusLangDeploy jpmStatusLangDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmStatusLangDeploy.setCreatedId(userId);
        jpmStatusLangDeploy.setCreatedDate(sysDate);
        jpmStatusLangDeployRepository.create(jpmStatusLangDeploy);
        return jpmStatusLangDeploy;
    }

    @Override
    public JpmStatusLangDeploy saveJpmStatusLangDeployDto(JpmStatusLangDeployDto jpmStatusLangDeployDto) {
        JpmStatusLangDeploy jpmStatusLangDeploy = objectMapper.convertValue(jpmStatusLangDeployDto, JpmStatusLangDeploy.class);
        return this.saveJpmStatusLangDeploy(jpmStatusLangDeploy);
    }

    @Override
    public void saveJpmStatusLangDeployDtos(List<JpmStatusLangDeployDto> statusLangDeployDtos, Long statusDeployId) {
        if (CommonCollectionUtil.isNotEmpty(statusLangDeployDtos) && Objects.nonNull(statusDeployId)) {
            for (JpmStatusLangDeployDto statusLangDeployDto : statusLangDeployDtos) {
                statusLangDeployDto.setStatusDeployId(statusDeployId);
                this.saveJpmStatusLangDeployDto(statusLangDeployDto);
            }
        }
    }

    @Override
    public List<JpmStatusLangDeployDto> getStatusLangDeployDtosByProcessDeployId(Long processDeployId) {
        return jpmStatusLangDeployRepository.getStatusLangDeployDtosByProcessDeployId(processDeployId);
    }

    @Override
    public List<JpmStatusLangDeployDto> getStatusLangDeployDtosByStatusDeployId(Long statusDeployId) {
        return jpmStatusLangDeployRepository.getStatusLangDeployDtosByStatusDeployId(statusDeployId);
    }

}