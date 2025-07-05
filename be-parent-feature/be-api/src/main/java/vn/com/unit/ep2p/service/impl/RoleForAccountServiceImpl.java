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

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.core.service.JcaRoleForAccountService;
import vn.com.unit.core.service.JcaRoleService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.req.dto.RoleForAccountAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.RoleForAccountInfoReq;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.res.RoleForAccountListInfoRes;
import vn.com.unit.ep2p.service.RoleForAccountService;

/**
 * RoleForTeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForAccountServiceImpl extends AbstractCommonService implements RoleForAccountService {

	@Autowired
	private JcaRoleForAccountService jcaRoleForAccountService;
	
	@Autowired
	private JcaAccountService jcaAccountService;
	
	@Autowired
	@Qualifier("jcaRoleServiceImpl")
	private JcaRoleService jcaRoleService;
	
	@Override
	public RoleForAccountListInfoRes getListRoleForAccountDto(Long userId) throws DetailException {
	    RoleForAccountListInfoRes roleForAccountListInfoRes = new RoleForAccountListInfoRes();
	    checkUserId(userId);
	    try {
            List<JcaRoleForAccountDto> jcaRoleForAccountDtos =  jcaRoleForAccountService.getJcaRoleForAccountDtoByUserId(userId);
            roleForAccountListInfoRes.setJcaRoleForAccountDtos(jcaRoleForAccountDtos);
         } catch (Exception e) {
             handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022501_APPAPI_ROLE_FOR_TEAM_LIST_ERROR);
        }
		return roleForAccountListInfoRes;
	}
	
	private void checkUserId(Long userId) throws DetailException {
	    if(null != userId) {
	        JcaAccountDto  jcaAccountDto = jcaAccountService.getJcaAccountDtoById(userId);
	        if(null == jcaAccountDto) {
	            throw new DetailException(AppApiExceptionCodeConstant.E402806_APPAPI_ACCOUNT_NOT_FOUND, true);
            }
	    }
	}
	
	private void setUserId(Long userId, JcaRoleForAccountDto jcaRoleForAccountDto)  throws DetailException {
        jcaRoleForAccountDto.setUserId(userId);
    }
	
	private void setRoleId(Long roleId, JcaRoleForAccountDto jcaRoleForAccountDto) throws DetailException {
	    if(null != roleId) {
	        JcaRoleDto dto = jcaRoleService.getJcaRoleDtoById(roleId);
	        if(null != dto) {
	            jcaRoleForAccountDto.setRoleId(roleId);
	        }else {
	            throw new DetailException(AppApiExceptionCodeConstant.E4022507_APPAPI_ROLE_FOR_TEAM_NOT_FOUND_ROLE_ERROR, new String[] { roleId.toString() }, true);
	        }
	    }
	}
	
	private void saveRoleForAccount(Long userId, List<RoleForAccountAddInfoReq> accountAddInfoReqs) throws DetailException {
	    checkUserId(userId);
	    jcaRoleForAccountService.deleteJcaRoleForAccountByUserId(userId);
	    if(CommonCollectionUtil.isNotEmpty(accountAddInfoReqs)) {
	        for (RoleForAccountAddInfoReq roleForAccountAddInfoReq : accountAddInfoReqs) {
	            JcaRoleForAccountDto jcaRoleForAccountDto = new JcaRoleForAccountDto();
	            if(null!= userId) {
	                setUserId(userId, jcaRoleForAccountDto);
	            }
	            if(null!= roleForAccountAddInfoReq.getRoleId()) {
	                setRoleId(roleForAccountAddInfoReq.getRoleId(), jcaRoleForAccountDto);
	            }
	            
	            if(null != roleForAccountAddInfoReq.getStartDate()) {
	                jcaRoleForAccountDto.setStartDate(CommonDateUtil.parseDate(roleForAccountAddInfoReq.getStartDate(), AppCoreConstant.YYYYMMDD));
	            }
	            if(null != roleForAccountAddInfoReq.getEndDate()) {
	                jcaRoleForAccountDto.setEndDate(CommonDateUtil.parseDate(roleForAccountAddInfoReq.getEndDate(), AppCoreConstant.YYYYMMDD));
	            }
	            this.save(jcaRoleForAccountDto);
            }
	    }
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RoleForAccountListInfoRes create(RoleForAccountInfoReq roleForAccountInfoReq) throws DetailException {
		try {
		    saveRoleForAccount(roleForAccountInfoReq.getUserId(), roleForAccountInfoReq.getRoleForAccountAddInfoReqs());
		} catch (Exception e) {
			handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022502_APPAPI_ROLE_FOR_TEAM_ADD_ERROR);
		}
		return this.getListRoleForAccountDto(roleForAccountInfoReq.getUserId());
	}
	
	@Override
	public JcaRoleForAccountDto save(JcaRoleForAccountDto jcaRoleForAccountDto) {
	    jcaRoleForAccountService.saveJcaRoleForAccountDto(jcaRoleForAccountDto); 
	    return jcaRoleForAccountDto;
	}
	
	
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long roleForAccountId) throws DetailException {
        if(null != roleForAccountId) {
            JcaRoleForAccountDto jcaRoleForAccountDto = jcaRoleForAccountService.getJcaRoleForAccountDtoById(roleForAccountId);
            if(null != jcaRoleForAccountDto) {
                try {
                    jcaRoleForAccountService.deleteJcaRoleForAccountById(roleForAccountId);
                } catch (Exception e) {
                    handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024502_APPAPI_ROLE_FOR_ACCOUNT_DELETE_ERROR);
                }
            }else {
                throw new DetailException(AppApiExceptionCodeConstant.E4024503_APPAPI_ROLE_FOR_ACCOUNT_NOT_FOUND_ERROR, true);
            }
        }
    }
}
