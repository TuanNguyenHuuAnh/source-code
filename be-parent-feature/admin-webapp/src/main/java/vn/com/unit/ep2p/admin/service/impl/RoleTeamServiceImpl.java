/*******************************************************************************
 * Class        RoleTeamServiceImpl
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phunghn
 * Change log   2017/09/1201-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.dto.JcaRoleForTeamDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaTeam;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.impl.JcaRoleForTeamServiceImpl;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.dto.RoleTeamEditDto;
import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
import vn.com.unit.ep2p.admin.repository.RoleTeamRepository;
import vn.com.unit.ep2p.admin.repository.TeamRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.RoleService;
import vn.com.unit.ep2p.admin.service.RoleTeamService;

/**
 * RoleTeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleTeamServiceImpl extends JcaRoleForTeamServiceImpl implements RoleTeamService, AbstractCommonService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    RoleTeamRepository roleTeamRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    SystemConfig systemConfig;
    
    @Autowired
    JCommonService commonService;

    @Override
    public List<JcaTeamDto> getListTeam(Long companyId) {
//        List<TeamDto> list = teamRepository.findAllTeam(companyId, UserProfileUtils.isCompanyAdmin());

        // if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
        // list = teamRepository.findAllTeamForOracle(companyId, UserProfileUtils.isCompanyAdmin());
        // } else {
        // list = teamRepository.findAllTeam(companyId, UserProfileUtils.isCompanyAdmin());
        // }
//        return list;
        return null;
    }

    @Override
    public List<JcaRoleForTeamDto> getListRoleForTeam(Long teamId) {
        JcaTeam team = teamRepository.findOne(teamId);
        Long companyId = null != team ? team.getCompanyId() : null;
        List<RoleEditDto> roleList = roleService.findRoleListByRoleType(ConstantCore.STR_ONE, companyId, false);
        List<JcaRoleForTeamDto> listRoleTeam = roleTeamRepository.getListRoleForTeam(teamId);
        //
        Map<Long, JcaRoleForTeamDto> mapRoleForTeam = new HashMap<>();
        for (JcaRoleForTeamDto item : listRoleTeam) {
            mapRoleForTeam.put(item.getRoleId(), item);
        }
        //
        List<JcaRoleForTeamDto> list = new ArrayList<>();
        for (RoleEditDto item : roleList) {
        	JcaRoleForTeamDto role = new JcaRoleForTeamDto();
            role.setFlgChecked(false);
            if (mapRoleForTeam.containsKey(item.getId())) {
                role.setFlgChecked(true);
            }
            role.setRoleId(item.getId());
            role.setRoleName(item.getName());
            role.setActive(item.isActived());
            list.add(role);
        }
        return list;
    }

    @Override
    @Transactional
    public int saveRoleForTeam(RoleTeamEditDto roleDto) {
        int rs = 0;
        try {
            roleTeamRepository.deleteAllRoleForTeam(roleDto.getTeamId());
            // insert to jca_m_role_for_grade
            Long userId = UserProfileUtils.getAccountId();

            if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
                for (JcaRoleForTeamDto item : roleDto.getData()) {
                    if (item.isFlgChecked()) {
                        item.setTeamId(roleDto.getTeamId());
                        roleTeamRepository.saveRoleForTeamOracle(item, userId);
                    }
                }
            } else {
                for (JcaRoleForTeamDto item : roleDto.getData()) {
                    if (item.isFlgChecked()) {
                        item.setTeamId(roleDto.getTeamId());
                        item.setDefFlag(0);
                        roleTeamRepository.saveRoleForTeam(item, userId);
                    }
                }
            }
         
            rs = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }
    
    @Override
    public List<JcaRoleForTeamDto> getListRoleForTeamWithRoleType(Long teamId) {
        JcaTeam team = teamRepository.findOne(teamId);
        Long companyId = null != team ? team.getCompanyId() : null;
        List<RoleEditDto> roleList = roleService.findRoleListByRoleType(null, companyId, false);
        List<JcaRoleForTeamDto> listRoleTeam = roleTeamRepository.getListRoleForTeam(teamId);
        //
        Map<Long, JcaRoleForTeamDto> mapRoleForTeam = new HashMap<>();
        for (JcaRoleForTeamDto item : listRoleTeam) {
            mapRoleForTeam.put(item.getRoleId(), item);
        }
        //
        List<JcaRoleForTeamDto> list = new ArrayList<>();
        for (RoleEditDto item : roleList) {
        	JcaRoleForTeamDto role = new JcaRoleForTeamDto();
            role.setFlgChecked(false);
            if (mapRoleForTeam.containsKey(item.getId())) {
                role.setFlgChecked(true);
            }
            role.setRoleId(item.getId());
            role.setRoleName(item.getName());
            role.setActive(item.isActived());
            list.add(role);
        }
        return list;
    }
    
    @Override
    public int saveRoleForTeamWithRoleType(RoleTeamEditDto roleDto) {
        int rs = 0;
        try {
            String roleType = null;
            
            // Check select multi role type
            for (JcaRoleForTeamDto item : roleDto.getData()) {
                if (item.isFlgChecked()) {
                    RoleEditDto roleEdit = roleService.findRoleEditDtoById(item.getRoleId());
                    if (StringUtils.isBlank(roleType)) {
                        roleType = roleEdit.getRoleType();
                    } else {
                        if (!roleType.equalsIgnoreCase(roleEdit.getRoleType())) {
                            return 2;
                        }
                    }
                }
            }
            roleTeamRepository.deleteAllRoleForTeam(roleDto.getTeamId());
            // insert to jca_m_role_for_grade
            Long userId = UserProfileUtils.getAccountId();

            if (StringUtils.equals(DatabaseTypeEnum.ORACLE.toString(), systemConfig.getConfig(SystemConfig.DBTYPE))) {
                for (JcaRoleForTeamDto item : roleDto.getData()) {
                    if (item.isFlgChecked()) {
                        item.setTeamId(roleDto.getTeamId());
                        roleTeamRepository.saveRoleForTeamOracle(item, userId);
                    }
                }
            } else {
                for (JcaRoleForTeamDto item : roleDto.getData()) {
                    if (item.isFlgChecked()) {
                        item.setTeamId(roleDto.getTeamId());
                        roleTeamRepository.saveRoleForTeam(item, userId);
                    }
                }
            }

            rs = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

	@Override
	public List<JcaTeamDto> findTeamByCompanyId(Long companyId) {
		return teamRepository.findByCompanyId(companyId);
	}
	
//	/**
//     * UpdateRoleTeamParamDto use for call procedure
//     * 
//     * @version 01-00
//     * @since 01-00
//     * @author KhuongTH
//     */
//    private class UpdateRoleTeamParamDto {
//        @In
//        public Long teamId;
//        @Out
//        public String mes;
//    }

	@Override
	public JCommonService getCommonService() {
		return commonService;
	}

}
