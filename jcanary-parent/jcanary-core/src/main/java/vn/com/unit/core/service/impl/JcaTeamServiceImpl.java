/*******************************************************************************
 * Class        JcaTeamServiceImpl
 * Created date 2020/12/08
 * Lasted date  2020/12/08
 * Author       MinhNV
 * Change log   2020/12/08 01-00 MinhNV create a new
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
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.dto.JcaTeamSearchDto;
import vn.com.unit.core.entity.JcaTeam;
import vn.com.unit.core.repository.JcaTeamRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaTeamService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaTeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaTeamServiceImpl implements JcaTeamService {
    
    @Autowired
    private JcaTeamRepository teamRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public int countJcaTeamDtoByCondition(JcaTeamSearchDto teamSearchDto) {
        return teamRepository.countJcaTeamDtoByCondition(teamSearchDto);
    }

    @Override
    public List<JcaTeamDto> getJcaTeamDtoByCondition(JcaTeamSearchDto teamSearchDto, Pageable pagable) {
        return teamRepository.getJcaTeamDtoByCondition(teamSearchDto, pagable);
    }

    @Override
    public JcaTeam getTeamById(Long id) {
        return teamRepository.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaTeam saveJcaTeam(JcaTeam jcaTeam) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = jcaTeam.getId();
        if (null != id) {
            JcaTeam oldJcaTeam = teamRepository.findOne(id);
            if (null != oldJcaTeam) {
                jcaTeam.setCreatedDate(oldJcaTeam.getCreatedDate());
                jcaTeam.setCreatedId(oldJcaTeam.getCreatedId());
                jcaTeam.setUpdatedDate(sysDate);
                jcaTeam.setUpdatedId(userId);
                teamRepository.update(jcaTeam);
            }

        } else {
            jcaTeam.setCreatedDate(sysDate);
            jcaTeam.setCreatedId(userId);
            jcaTeam.setUpdatedDate(sysDate);
            jcaTeam.setUpdatedId(userId);
            teamRepository.create(jcaTeam);
        }
        return jcaTeam;
    }

    @Override
    public JcaTeam saveJcaTeamDto(JcaTeamDto jcaTeamDto) {
        JcaTeam jcaTeam = objectMapper.convertValue(jcaTeamDto, JcaTeam.class);
        jcaTeam.setId(jcaTeamDto.getTeamId());
        
        // save data
        jcaTeam = this.saveJcaTeam(jcaTeam);
        
        // update id
        jcaTeamDto.setTeamId(jcaTeam.getId());
        return jcaTeam;
    }

    @Override
    public JcaTeamDto getJcaTeamDtoById(Long id) {
        return teamRepository.getJcaTeamDtoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaTeamById(Long id) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if (null != id) {
            JcaTeam jcaTeam = teamRepository.findOne(id);
            if (null != jcaTeam) {
                jcaTeam.setDeletedDate(sysDate);
                jcaTeam.setDeletedId(userId);
                teamRepository.update(jcaTeam);
            }
        }
    }

    @Override
    public int countJcaTeamDtoByName(String name, Long teamId) {
        return teamRepository.countJcaTeamDtoByName(name, teamId);
    }

    @Override
    public int countJcaTeamDtoByCode(String code) {
        return teamRepository.countJcaTeamDtoByCode(code);
    }

    @Override
    public DbRepository<JcaTeam, Long> initRepo() {
        // TODO Auto-generated method stub
        return null;
    }
}
