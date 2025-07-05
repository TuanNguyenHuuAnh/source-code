/*******************************************************************************
 * Class        ：JcaRoleServiceImpl
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：ngannh
 * Change log   ：2020/12/02：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.core.entity.JcaRole;
import vn.com.unit.core.repository.JcaRoleRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaRoleService;

/**
 * JcaRoleServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
    
    
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaRoleServiceImpl implements JcaRoleService {
    @Autowired
    private JcaRoleRepository jcaRoleRepository;
    
    @Autowired
    ObjectMapper objectMapper;
   
    @Override
    public int countRoleByCondition(JcaRoleSearchDto jcaRoleSearchDto) {
        return jcaRoleRepository.countJcaRoleByCondition(jcaRoleSearchDto);
    }

    @Override
    public List<JcaRoleDto> getRoleByCondition(JcaRoleSearchDto jcaRoleSearchDto, Pageable pageable) {
        return jcaRoleRepository.getJcaRoleByCondition(jcaRoleSearchDto,pageable).getContent();
    }
    
    public JcaRole create(JcaRole jcaRole) {
        return jcaRoleRepository.create(jcaRole);
    }

    public JcaRole update(JcaRole jcaRole) {  
        return jcaRoleRepository.update(jcaRole);
    }
    
    @Override
    public JcaRole getRoleById(Long roleId) {  
        return jcaRoleRepository.findOne(roleId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaRole saveJcaRole(JcaRole jcaRole) {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jcaRole.getId();
        if (null != id) {
            JcaRole jcaRoleDB = jcaRoleRepository.findOne(id);
            if (null != jcaRoleDB) {
                jcaRole.setCreatedId(jcaRoleDB.getCreatedId());
                jcaRole.setCreatedDate(jcaRoleDB.getCreatedDate());
                jcaRole.setUpdatedId(userId);
                jcaRole.setUpdatedDate(sysDate);
                jcaRoleRepository.update(jcaRole);
            }
        } else {
            jcaRole.setCreatedId(userId);
            jcaRole.setCreatedDate(sysDate);
            jcaRole.setUpdatedId(userId);
            jcaRole.setUpdatedDate(sysDate);
            jcaRoleRepository.create(jcaRole);
        }
        return jcaRole;
    }
      
    
    
    @Override
    public JcaRole saveJcaRoleDto(JcaRoleDto jcaRoleDto) {
        JcaRole jcaRole = objectMapper.convertValue(jcaRoleDto, JcaRole.class);
        jcaRole.setId(jcaRoleDto.getRoleId());
        
        // save data
        jcaRole = this.saveJcaRole(jcaRole);
        
        // update id
        jcaRoleDto.setRoleId(jcaRole.getId());
        return jcaRole;
    }
    
    @Override
    public JcaRoleDto getJcaRoleDtoById(Long id) {
        return jcaRoleRepository.getJcaRoleDtoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletedJcaRoleById(Long id) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (null != id) {
            JcaRole jcaRole = jcaRoleRepository.findOne(id);
            if (null != jcaRole) {
                jcaRole.setDeletedDate(sysDate);
                jcaRole.setDeletedId(userId);
                jcaRoleRepository.update(jcaRole);
            }
        }
    }

    @Override
    public int countJcaRoleDtoByRoleCode(String roleCode) {
        return jcaRoleRepository.countJcaRoleDtoByRoleCode(roleCode);
    }

    @Override
    public int countJcaRoleDtoByRoleName(String roleName, Long roleId) {
        return jcaRoleRepository.countJcaRoleDtoByRoleName(roleName, roleId);
    }
    
    
}
