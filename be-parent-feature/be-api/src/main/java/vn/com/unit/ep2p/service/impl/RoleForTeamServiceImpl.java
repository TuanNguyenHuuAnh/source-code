/*******************************************************************************
 * Class        ：RoleForTeamServiceImpl
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：SonND
 * Change log   ：2020/12/22：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForTeamDto;
import vn.com.unit.core.dto.JcaRoleForTeamSearchDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.entity.JcaRoleForTeam;
import vn.com.unit.core.service.JcaRoleForTeamService;
import vn.com.unit.core.service.JcaRoleService;
import vn.com.unit.core.service.JcaTeamService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.req.dto.RoleForTeamInfoListReq;
import vn.com.unit.ep2p.core.req.dto.RoleForTeamInfoReq;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.res.RoleForTeamInfoRes;
import vn.com.unit.ep2p.dto.res.RoleForTeamListInfoRes;
import vn.com.unit.ep2p.service.RoleForTeamService;
import vn.com.unit.ep2p.service.TeamService;

/**
 * RoleForTeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForTeamServiceImpl extends AbstractCommonService implements RoleForTeamService {

	@Autowired
	private JcaRoleForTeamService jcaRoleForTeamService;
	
	@Autowired
	@Qualifier("jcaRoleServiceImpl")
	private JcaRoleService jcaRoleService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	@Qualifier("jcaTeamServiceImpl")
	private JcaTeamService jcaTeamService;

	@Override
	public RoleForTeamListInfoRes getListRoleForTeamDto(MultiValueMap<String, String> commonSearch) throws DetailException {
	    RoleForTeamListInfoRes roleForTeamListInfoRes = new RoleForTeamListInfoRes();
		try {
            /** init param search repository */
            JcaRoleForTeamSearchDto reqSearch = this.buildJcaRoleForTeamSearchDto(commonSearch);
            
		    roleForTeamListInfoRes.setListRoleDtos(jcaRoleForTeamService.getJcaRoleDto(reqSearch));
		    roleForTeamListInfoRes.setListTeamDtos(jcaRoleForTeamService.getJcaTeamDto(reqSearch));
		} catch (Exception e) {
			handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022501_APPAPI_ROLE_FOR_TEAM_LIST_ERROR);
		}
		return roleForTeamListInfoRes;
	}
	
	private JcaRoleForTeamSearchDto buildJcaRoleForTeamSearchDto(MultiValueMap<String, String> commonSearch) {
	    JcaRoleForTeamSearchDto reqSearch = new JcaRoleForTeamSearchDto();
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        reqSearch.setCompanyId(companyId);
        return  reqSearch;
    }
	
	private void setRoleId(Long roleId, JcaRoleForTeamDto jcaRoleForTeamDto) throws DetailException {
	    if(null != roleId) {
	        JcaRoleDto dto = jcaRoleService.getJcaRoleDtoById(roleId);
	        if(null != dto) {
	            jcaRoleForTeamDto.setRoleId(roleId);
	        }else {
	            throw new DetailException(AppApiExceptionCodeConstant.E4022507_APPAPI_ROLE_FOR_TEAM_NOT_FOUND_ROLE_ERROR, new String[] { roleId.toString() }, true);
	        }
	    }
	}
	
	private void setTeamId(Long teamId, JcaRoleForTeamDto jcaRoleForTeamDto) throws DetailException {
        if(null != teamId) {
            JcaTeamDto dto = teamService.detail(teamId);
            if(null != dto) {
                jcaRoleForTeamDto.setTeamId(teamId);
            }else {
                throw new DetailException(AppApiExceptionCodeConstant.E4021506_APPAPI_TEAM_NOT_FOUND, true);
            }
        }
    }

	private void setCompanyId(Long companyId, JcaRoleForTeamDto jcaRoleForTeamDto) throws DetailException {
//        if(null != companyId) {
//            JcaCompanyDto dto = companyService.detail(companyId);
//            if(null != dto) {
//                jcaRoleForTeamDto.setCompanyId(companyId);
//            }else {
//                throw new DetailException(AppApiExceptionCodeConstant.E4021203_APPAPI_COMPANY_NOT_FOUND, true);
//            }
//        }
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveListRoleForTeam(RoleForTeamInfoListReq roleForTeamInfoListReq) throws DetailException {
	    if(CommonCollectionUtil.isNotEmpty(roleForTeamInfoListReq.getListRoleForTeamInfoReq())) {
	        for (RoleForTeamInfoReq roleForTeamInfoReq : roleForTeamInfoListReq.getListRoleForTeamInfoReq()) {
	            this.saveRoleForTeam(roleForTeamInfoReq);
            }
	    }
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveRoleForTeam(RoleForTeamInfoReq roleForTeamInfoReq) throws DetailException {
	    List<Long> listRoleId = roleForTeamInfoReq.getListRoleId();
	    // delete all role by team id
	    jcaRoleForTeamService.deleteAllRoleByTeamId(roleForTeamInfoReq.getTeamId());
		try {
		    if(CommonCollectionUtil.isNotEmpty(listRoleId)) {
		        // save role for team id
		        for (int i = 0; i < listRoleId.size(); i++) {
		            JcaRoleForTeamDto jcaRoleForTeamDto = new JcaRoleForTeamDto();
		            this.setRoleId(listRoleId.get(i), jcaRoleForTeamDto);
                    this.setTeamId(roleForTeamInfoReq.getTeamId(), jcaRoleForTeamDto);
                    this.setCompanyId(roleForTeamInfoReq.getCompanyId(), jcaRoleForTeamDto);
                    this.save(jcaRoleForTeamDto); 
                }
		    }
		    
		} catch (Exception e) {
			handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022502_APPAPI_ROLE_FOR_TEAM_ADD_ERROR);
		}
	}

	@Override
	public JcaRoleForTeamDto save(JcaRoleForTeamDto jcaRoleForTeamDto) {
	    JcaRoleForTeam jcaRoleForTeam = jcaRoleForTeamService.saveJcaRoleForTeamDto(jcaRoleForTeamDto);
	    jcaRoleForTeamDto.setId(jcaRoleForTeam.getId());
		return jcaRoleForTeamDto;
	}


	@Override
	public RoleForTeamInfoRes getRoleForTeamInfoResByTeamId(Long teamId, Long companyId) throws DetailException {
	    RoleForTeamInfoRes roleForTeamInfoRes = new RoleForTeamInfoRes();
        JcaTeamDto jcaTeamDto = jcaTeamService.getJcaTeamDtoById(teamId);
        if (null != jcaTeamDto) {
            try {
                List<JcaRoleDto> listJcaRoleDto = jcaRoleForTeamService.getJcaRoleDtoByTeamId(teamId,companyId);
                roleForTeamInfoRes.setListRoleDtos(listJcaRoleDto);
                roleForTeamInfoRes.setTeamId(teamId);
                roleForTeamInfoRes.setTeamCode(jcaTeamDto.getCode());
                roleForTeamInfoRes.setTeamName(jcaTeamDto.getName());
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022506_APPAPI_ROLE_FOR_TEAM_INFO_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021506_APPAPI_TEAM_NOT_FOUND);
        }
	    
		return roleForTeamInfoRes;
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long teamId) throws DetailException {
        List<JcaRoleForTeamDto> jcaRoleForTeamDtos = jcaRoleForTeamService.getJcaRoleForTeamDtoByTeamId(teamId);
        if(CommonCollectionUtil.isNotEmpty(jcaRoleForTeamDtos)) {
            try {
                jcaRoleForTeamService.deleteAllRoleByTeamId(teamId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022505_APPAPI_ROLE_FOR_TEAM_DELETE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021506_APPAPI_TEAM_NOT_FOUND, true);
        }
    }
}
