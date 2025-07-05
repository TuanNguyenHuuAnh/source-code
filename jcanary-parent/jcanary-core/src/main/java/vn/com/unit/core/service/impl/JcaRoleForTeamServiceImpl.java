/*******************************************************************************
 * Class        :JcaRoleForTeamServiceImpl
 * Created date :2020/12/22
 * Lasted date  :2020/12/22
 * Author       :SonND
 * Change log   :2020/12/2 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForTeamDto;
import vn.com.unit.core.dto.JcaRoleForTeamSearchDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaRoleForTeam;
import vn.com.unit.core.repository.JcaRoleForTeamRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaRoleForTeamService;

/**
 * JcaRoleForTeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true ,rollbackFor = Exception.class)
public class JcaRoleForTeamServiceImpl implements JcaRoleForTeamService {

    @Autowired
    JcaRoleForTeamRepository jcaRoleForTeamRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public JcaRoleForTeam getJcaRoleForTeamById(Long id) {
        return jcaRoleForTeamRepository.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaRoleForTeam saveJcaRoleForTeam(JcaRoleForTeam jcaRoleForTeam) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = jcaRoleForTeam.getId();
        if(null != id) {
            JcaRoleForTeam oldJcaRoleForTeam =  jcaRoleForTeamRepository.findOne(id);
            if (null != oldJcaRoleForTeam) {
                jcaRoleForTeam.setCreatedDate(oldJcaRoleForTeam.getCreatedDate());
                jcaRoleForTeam.setCreatedId(oldJcaRoleForTeam.getCreatedId());
//                jcaRoleForTeam.setUpdatedDate(sysDate);
//                jcaRoleForTeam.setUpdatedId(userId);
                jcaRoleForTeamRepository.update(jcaRoleForTeam);
            }
            
        }else {
            jcaRoleForTeam.setCreatedDate(sysDate);
            jcaRoleForTeam.setCreatedId(userId);
//            jcaRoleForTeam.setUpdatedDate(sysDate);
//            jcaRoleForTeam.setUpdatedId(userId);
            jcaRoleForTeamRepository.create(jcaRoleForTeam);
        }
        return jcaRoleForTeam;
    }
    
    @Override
    public JcaRoleForTeam saveJcaRoleForTeamDto(JcaRoleForTeamDto jcaRoleForTeamDto) {
        JcaRoleForTeam jcaRoleForTeam = objectMapper.convertValue(jcaRoleForTeamDto, JcaRoleForTeam.class);
        // save data
        jcaRoleForTeam = this.saveJcaRoleForTeam(jcaRoleForTeam);
        
        // update id
        jcaRoleForTeamDto.setId(jcaRoleForTeam.getId());
        return jcaRoleForTeam;
    }

    @Override
    public List<JcaRoleDto> getJcaRoleDto(JcaRoleForTeamSearchDto jcaRoleForTeamSearchDto) {
        return jcaRoleForTeamRepository.getJcaRoleDto(jcaRoleForTeamSearchDto);
    }

    @Override
    public List<JcaTeamDto> getJcaTeamDto(JcaRoleForTeamSearchDto jcaRoleForTeamSearchDto) {
        return jcaRoleForTeamRepository.getJcaTeamDto(jcaRoleForTeamSearchDto);
    }

    @Override
    public List<JcaRoleDto> getJcaRoleDtoByTeamId(Long teamId, Long companyId) {
        return jcaRoleForTeamRepository.getJcaRoleDtoByTeamId(teamId, companyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllRoleByTeamId(Long teamId) {
        jcaRoleForTeamRepository.deleteAllRoleByTeamId(teamId);
    }

    @Override
    public List<JcaRoleForTeamDto> getJcaRoleForTeamDtoByTeamId(Long teamId) {
        return jcaRoleForTeamRepository.getJcaRoleForTeamDtoByTeamId(teamId);
    }

 

   

   
}
