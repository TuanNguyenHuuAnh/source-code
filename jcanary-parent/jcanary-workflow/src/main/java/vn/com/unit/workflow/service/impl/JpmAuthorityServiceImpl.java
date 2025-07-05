/*******************************************************************************
 * Class        ：JpmAuthorityServiceImpl
 * Created date ：2021/03/07
 * Lasted date  ：2021/03/07
 * Author       ：Tan Tai
 * Change log   ：2021/03/07：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmAuthorityDto;
import vn.com.unit.workflow.entity.JpmAuthority;
import vn.com.unit.workflow.repository.JpmAuthorityRepository;
import vn.com.unit.workflow.service.JpmAuthorityService;

/**
 * JpmAuthorityServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmAuthorityServiceImpl implements JpmAuthorityService {

    @Autowired
    private JpmAuthorityRepository jpmAuthorityRepository;

    @Override
    public List<JpmAuthorityDto> getListJpmAuthorityDtoByPermissionIds(List<Long> permissionIds) {
        return jpmAuthorityRepository.getListJpmAuthorityDtoByPermissionIds(permissionIds);
    }

    @Override
    public List<JpmAuthorityDto> getJpmAuthorityDtosByProcessDeployIdAndRoleId(Long processDeployId, Long roleId) {
        return jpmAuthorityRepository.getJpmAuthorityDtosByProcessDeployIdAndRoleId(processDeployId, roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveJpmAuthorityDtosByProcessDeployIdAndRoleId(List<JpmAuthorityDto> authorityDtos, Long processDeployId, Long roleId) {
        if (CommonCollectionUtil.isEmpty(authorityDtos) || Objects.isNull(roleId) || Objects.isNull(processDeployId)) {
            return false;
        }

        // delete old
        jpmAuthorityRepository.deleteByProcessDeployIdAndRoleId(processDeployId, roleId);

        // save
        Long user = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        for (JpmAuthorityDto authorityDto : authorityDtos) {
            if (!authorityDto.isAccessFlag())
                continue;
            JpmAuthority jpmAuthority = new JpmAuthority();
            jpmAuthority.setPermissionDeployId(authorityDto.getPermissionDeployId());
            jpmAuthority.setProcessDeployId(processDeployId);
            jpmAuthority.setRoleId(roleId);
            jpmAuthority.setCreatedId(user);
            jpmAuthority.setCreatedDate(sysDate);

            jpmAuthorityRepository.create(jpmAuthority);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cloneRoleForProcess(Long oldProcessDeployId, Long newProcessDeployId) {
        Long user = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jpmAuthorityRepository.cloneRoleForProcess(oldProcessDeployId, newProcessDeployId, user, sysDate);
    }
}
