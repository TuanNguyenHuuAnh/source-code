/*******************************************************************************
* Class        JpmPermissionServiceImpl
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
import vn.com.unit.common.utils.CommonMapUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmPermissionDto;
import vn.com.unit.workflow.entity.JpmPermission;
import vn.com.unit.workflow.repository.JpmPermissionRepository;
import vn.com.unit.workflow.service.JpmHiPermissionService;
import vn.com.unit.workflow.service.JpmPermissionService;

/**
 * JpmPermissionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmPermissionServiceImpl implements JpmPermissionService {

    @Autowired
    private JpmPermissionRepository jpmPermissionRepository;

    @Autowired
    private JpmHiPermissionService jpmHiPermissionService;
    
    // Object mapper
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JpmPermissionDto getJpmPermissionDtoById(Long id) {
        JpmPermissionDto jpmPermissionDto = new JpmPermissionDto();
        if (null != id) {
            JpmPermission jpmPermission = jpmPermissionRepository.findOne(id);
            if (null != jpmPermission && null == jpmPermission.getDeletedId()) {
                jpmPermissionDto = objectMapper.convertValue(jpmPermission, JpmPermissionDto.class);
            }
        }
        return jpmPermissionDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JpmPermission jpmPermission = jpmPermissionRepository.findOne(id);
            if (null != jpmPermission) {
                jpmPermission.setDeletedId(userId);
                jpmPermission.setDeletedDate(sysDate);
                jpmPermissionRepository.update(jpmPermission);
                res = true;
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmPermission saveJpmPermission(JpmPermission jpmPermission) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmPermission.getId();
        if (null != id) {
            JpmPermission oldJpmPermission = jpmPermissionRepository.findOne(id);
            if (null != oldJpmPermission) {
                // jpmPermission.setPermissionCode(oldJpmPermission.getPermissionCode());
                jpmPermission.setCreatedId(oldJpmPermission.getCreatedId());
                jpmPermission.setCreatedDate(oldJpmPermission.getCreatedDate());
                jpmPermission.setUpdatedId(userId);
                jpmPermission.setUpdatedDate(sysDate);
                jpmPermissionRepository.update(jpmPermission);
            }
        } else {

            // init code
            String permissionCode = jpmPermission.getPermissionCode();
            if (CommonStringUtil.isBlank(permissionCode)) {
                int count = jpmPermissionRepository.getMaxPermissionCodeByProcess(jpmPermission.getProcessId());
                permissionCode = CommonStringUtil.leftPad(String.valueOf(count + 1), 5, "0");
            }
            jpmPermission.setPermissionCode(permissionCode);
            jpmPermission.setCreatedId(userId);
            jpmPermission.setCreatedDate(sysDate);
            jpmPermission.setUpdatedId(userId);
            jpmPermission.setUpdatedDate(sysDate);
            jpmPermissionRepository.create(jpmPermission);
        }
        
        // save history
        jpmHiPermissionService.saveJpmHiPermission(jpmPermission);
        
        return jpmPermission;
    }

    @Override
    public JpmPermission saveJpmPermissionDto(JpmPermissionDto jpmPermissionDto) {
        JpmPermission jpmPermission = objectMapper.convertValue(jpmPermissionDto, JpmPermission.class);
        jpmPermission.setId(jpmPermissionDto.getPermissionId());
        this.saveJpmPermission(jpmPermission);
        jpmPermissionDto.setPermissionId(jpmPermission.getId());

        return jpmPermission;
    }

    @Override
    public List<JpmPermissionDto> getPermissionDtosByProcessId(Long processId) {
        return jpmPermissionRepository.getPermissionDtosByProcessId(processId);
    }

    @Override
    public JpmPermissionDto getJpmPermissionDtoByPermissionId(Long processPermissionId) {
        return jpmPermissionRepository.getJpmPermissionDtoByPermissionId(processPermissionId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.workflow.service.JpmPermissionService#savePermissionDtosByProcessId(java.util.List, java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Long, Long> savePermissionDtosByProcessId(List<JpmPermissionDto> permissionDtos, Long processId) {

        Map<Long, Long> resMap = new HashMap<>();
        List<JpmPermissionDto> currentJpmPermissionDtos = this.getPermissionDtosByProcessId(processId);
        Map<String, JpmPermissionDto> jpmPermissionDtoMap = CommonCollectionUtil.isEmpty(currentJpmPermissionDtos) ? new HashMap<>()
                : currentJpmPermissionDtos.stream().collect(Collectors.toMap(JpmPermissionDto::getPermissionCode, stepDto -> stepDto));

        for (JpmPermissionDto permissionDto : permissionDtos) {
            Long oldpermissionId = permissionDto.getPermissionId();
            String permisstionCode = permissionDto.getPermissionCode();
            JpmPermissionDto currentpermissionDto = jpmPermissionDtoMap.remove(permisstionCode);
            if (Objects.isNull(currentpermissionDto)) {
                permissionDto.setPermissionId(null);
            } else {
                permissionDto.setPermissionId(currentpermissionDto.getPermissionId());
            }
            permissionDto.setProcessId(processId);
            this.saveJpmPermissionDto(permissionDto);

            if (Objects.nonNull(oldpermissionId)) {
                resMap.put(oldpermissionId, permissionDto.getPermissionId());
            }
        }
        // delete
        if (CommonMapUtil.isNotEmpty(jpmPermissionDtoMap)) {
            for (Map.Entry<String, JpmPermissionDto> entry : jpmPermissionDtoMap.entrySet()) {
                Long id = entry.getValue().getPermissionId();
                this.deleteById(id);
            }
        }

        return resMap;

    }

}