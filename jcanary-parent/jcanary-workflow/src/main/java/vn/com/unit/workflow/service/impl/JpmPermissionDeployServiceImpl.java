/*******************************************************************************
* Class        JpmPermissionDeployServiceImpl
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmPermissionDeployDto;
import vn.com.unit.workflow.entity.JpmPermissionDeploy;
import vn.com.unit.workflow.repository.JpmPermissionDeployRepository;
import vn.com.unit.workflow.service.JpmPermissionDeployService;

/**
 * JpmPermissionDeployServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmPermissionDeployServiceImpl implements JpmPermissionDeployService {

    @Autowired
    private JpmPermissionDeployRepository jpmPermissionDeployRepository;

    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmPermissionDeployDto getJpmPermissionDeployDtoById(Long id) {
        JpmPermissionDeployDto jpmPermissionDeployDto = new JpmPermissionDeployDto();
        if (null != id) {
            JpmPermissionDeploy jpmPermissionDeploy = jpmPermissionDeployRepository.findOne(id);
            if (null != jpmPermissionDeploy) {
                jpmPermissionDeployDto = objectMapper.convertValue(jpmPermissionDeploy, JpmPermissionDeployDto.class);
            }
        }
        return jpmPermissionDeployDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        if (null != id) {
            try {
                jpmPermissionDeployRepository.delete(id);
                res = true;
            } catch (Exception e) {
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmPermissionDeploy saveJpmPermissionDeploy(JpmPermissionDeploy jpmPermissionDeploy) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmPermissionDeploy.getId();
        if (null != id) {
            JpmPermissionDeploy oldJpmPermissionDeploy = jpmPermissionDeployRepository.findOne(id);
            if (null != oldJpmPermissionDeploy) {
                jpmPermissionDeploy.setCreatedId(oldJpmPermissionDeploy.getCreatedId());
                jpmPermissionDeploy.setCreatedDate(oldJpmPermissionDeploy.getCreatedDate());
                jpmPermissionDeployRepository.update(jpmPermissionDeploy);
            }
        } else {
            jpmPermissionDeploy.setCreatedId(userId);
            jpmPermissionDeploy.setCreatedDate(sysDate);
            jpmPermissionDeployRepository.create(jpmPermissionDeploy);
        }
        return jpmPermissionDeploy;
    }

    @Override
    public JpmPermissionDeploy saveJpmPermissionDeployDto(JpmPermissionDeployDto jpmPermissionDeployDto) {
        JpmPermissionDeploy jpmPermissionDeploy = objectMapper.convertValue(jpmPermissionDeployDto, JpmPermissionDeploy.class);
        return this.saveJpmPermissionDeploy(jpmPermissionDeploy);
    }

    @Override
    public Map<Long, Long> saveJpmPermissionDeployDtos(List<JpmPermissionDeployDto> permissionDeployDtos, Long processDeployId) {
        Map<Long, Long> permissionIdMap = new HashMap<>();
        if (CommonCollectionUtil.isNotEmpty(permissionDeployDtos) && Objects.nonNull(processDeployId)) {
            for (JpmPermissionDeployDto permissionDeployDto : permissionDeployDtos) {
                permissionDeployDto.setProcessDeployId(processDeployId);
                JpmPermissionDeploy permissionDeploy = this.saveJpmPermissionDeployDto(permissionDeployDto);

                Long permissionId = permissionDeploy.getPermissionId();
                Long permissionDeployId = permissionDeploy.getId();
                permissionIdMap.put(permissionId, permissionDeployId);
            }
        }
        return permissionIdMap;
    }

    @Override
    public List<JpmPermissionDeployDto> getPermissionDeployDtosByProcessDeployId(Long processDeployId) {
        return jpmPermissionDeployRepository.getPermissionDeployDtosByProcessDeployId(processDeployId);
    }

}