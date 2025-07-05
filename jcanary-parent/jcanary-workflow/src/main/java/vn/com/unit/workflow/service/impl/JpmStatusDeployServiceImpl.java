/*******************************************************************************
* Class        JpmStatusDeployServiceImpl
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.dto.JpmStatusLangDeployDto;
import vn.com.unit.workflow.entity.JpmStatusDeploy;
import vn.com.unit.workflow.repository.JpmStatusDeployRepository;
import vn.com.unit.workflow.service.JpmStatusDeployService;
import vn.com.unit.workflow.service.JpmStatusLangDeployService;

/**
 * JpmStatusDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmStatusDeployServiceImpl implements JpmStatusDeployService {

    @Autowired
    private JpmStatusDeployRepository jpmStatusDeployRepository;

    @Autowired
    private JpmStatusLangDeployService jpmStatusLangDeployService;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmStatusDeployDto getJpmStatusDeployDtoById(Long id) {
        JpmStatusDeployDto jpmStatusDeployDto = new JpmStatusDeployDto();
        if (null != id) {
            JpmStatusDeploy jpmStatusDeploy = jpmStatusDeployRepository.findOne(id);
            if (Objects.nonNull(jpmStatusDeploy) && 0L == jpmStatusDeploy.getDeletedId()) {
                jpmStatusDeployDto = objectMapper.convertValue(jpmStatusDeploy, JpmStatusDeployDto.class);
                jpmStatusDeployDto.setStatusDeployId(id);
                
                List<JpmStatusLangDeployDto> statusLangDeployDtos = jpmStatusLangDeployService.getStatusLangDeployDtosByStatusDeployId(id);
                jpmStatusDeployDto.setStatusLangs(statusLangDeployDtos);
            }
        }
        return jpmStatusDeployDto;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmStatusDeploy jpmStatusDeploy = jpmStatusDeployRepository.findOne(id);
            if (Objects.nonNull(jpmStatusDeploy) && Objects.isNull(jpmStatusDeploy.getDeletedId())) {
                jpmStatusDeploy.setDeletedId(userId);
                jpmStatusDeploy.setDeletedDate(sysDate);
                jpmStatusDeployRepository.update(jpmStatusDeploy);
                res = true;
            }
        }
        return res;
    }

    @Override
    public JpmStatusDeploy saveJpmStatusDeploy(JpmStatusDeploy jpmStatusDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmStatusDeploy.getId();
        if (null != id) {
            JpmStatusDeploy oldJpmStatusDeploy = jpmStatusDeployRepository.findOne(id);
            if (null != oldJpmStatusDeploy) {
                jpmStatusDeploy.setCreatedId(oldJpmStatusDeploy.getCreatedId());
                jpmStatusDeploy.setCreatedDate(oldJpmStatusDeploy.getCreatedDate());
                jpmStatusDeploy.setUpdatedId(userId);
                jpmStatusDeploy.setUpdatedDate(sysDate);
                jpmStatusDeployRepository.update(jpmStatusDeploy);
            }
        } else {
            jpmStatusDeploy.setCreatedId(userId);
            jpmStatusDeploy.setCreatedDate(sysDate);
            jpmStatusDeploy.setUpdatedId(userId);
            jpmStatusDeploy.setUpdatedDate(sysDate);
            jpmStatusDeployRepository.create(jpmStatusDeploy);
        }
        return jpmStatusDeploy;
    }

    @Override
    public JpmStatusDeploy saveJpmStatusDeployDto(JpmStatusDeployDto jpmStatusDeployDto) {
        JpmStatusDeploy jpmStatusDeploy = objectMapper.convertValue(jpmStatusDeployDto, JpmStatusDeploy.class);
        this.saveJpmStatusDeploy(jpmStatusDeploy);

        Long statusDeployId = jpmStatusDeploy.getId();
        List<JpmStatusLangDeployDto> statusLangs = jpmStatusDeployDto.getStatusLangs();
        jpmStatusLangDeployService.saveJpmStatusLangDeployDtos(statusLangs, statusDeployId);

        return jpmStatusDeploy;
    }

    @Override
    public Map<Long, Long> saveJpmStatusDeployDtos(List<JpmStatusDeployDto> statusDeployDtos, Long processDeployId) {
        Map<Long, Long> statusIdMap = new HashMap<>();
        if (CommonCollectionUtil.isNotEmpty(statusDeployDtos) && Objects.nonNull(processDeployId)) {
            for (JpmStatusDeployDto statusDeployDto : statusDeployDtos) {
                statusDeployDto.setProcessDeployId(processDeployId);
                JpmStatusDeploy statusDeploy = this.saveJpmStatusDeployDto(statusDeployDto);

                Long statusId = statusDeploy.getStatusId();
                Long statusDeployId = statusDeploy.getId();
                statusIdMap.put(statusId, statusDeployId);
            }
        }
        return statusIdMap;
    }

    @Override
    public List<JpmStatusDeployDto> getStatusDeployDtosByProcessDeployId(Long processDeployId) {
        List<JpmStatusDeployDto> statusDeployDtos = jpmStatusDeployRepository.getStatusDeployDtosByProcessDeployId(processDeployId);
        if (CommonCollectionUtil.isNotEmpty(statusDeployDtos)) {
            List<JpmStatusLangDeployDto> statusLangDtos = jpmStatusLangDeployService
                    .getStatusLangDeployDtosByProcessDeployId(processDeployId);
            if (CommonCollectionUtil.isNotEmpty(statusLangDtos)) {
                Map<Long, List<JpmStatusLangDeployDto>> statusLangDeployDtosMap = statusLangDtos.stream()
                        .collect(Collectors.groupingBy(JpmStatusLangDeployDto::getStatusDeployId));
                statusDeployDtos.forEach(statusDto -> statusDto.setStatusLangs(statusLangDeployDtosMap.get(statusDto.getStatusDeployId())));
            }
        }
        return statusDeployDtos;
    }

    @Override
    public int countStatusDeployDtosByProcessDeployId(Long processDeployId) {
        return jpmStatusDeployRepository.countStatusDeployDtosByProcessDeployId(processDeployId);
    }

    @Override
    public JpmStatusDeployDto getStatusDeploy(Long docId, String languageCode) {
        return jpmStatusDeployRepository.findStatusDeploy(docId, languageCode);
    }

    @Override
    public JpmStatusDeployDto getStatusDeployByStatusCode(Long docId, String statusCode, String languageCode) {
        return jpmStatusDeployRepository.findStatusDeployByStatusCode(docId, statusCode, languageCode);
    }
}