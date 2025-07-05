/*******************************************************************************
 * Class        ：RoleForCompanyServiceImp
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：ngannh
 * Change log   ：2020/12/22：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForCompanyDto;
import vn.com.unit.core.service.JcaCompanyService;
import vn.com.unit.core.service.JcaOrganizationService;
import vn.com.unit.core.service.JcaRoleForCompanyService;
import vn.com.unit.core.service.JcaRoleService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.req.dto.RoleForCompanyInforReq;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.RoleForCompanyAddListReq;
import vn.com.unit.ep2p.dto.req.RoleForCompanyAddReq;
import vn.com.unit.ep2p.dto.res.RoleForCompanyInfoRes;
import vn.com.unit.ep2p.service.RoleForCompanyService;

/**
 * RoleForCompanyServiceImp
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForCompanyServiceImp extends AbstractCommonService implements RoleForCompanyService {

    @Autowired
    JcaRoleForCompanyService jcaRoleForCompanyService;
    
    @Autowired
    JcaCompanyService jcaCompanyService;
    
    @Autowired
    JcaOrganizationService jcaOrganizationService;
    
    @Autowired
    @Qualifier("jcaRoleServiceImpl")
    JcaRoleService jcaRoleService;

    @Override
    public RoleForCompanyInfoRes getRoleForCompanyInfoResById(Long roleId, Long companyId) throws DetailException {
        RoleForCompanyInfoRes result = new RoleForCompanyInfoRes();
        try {
            List<JcaRoleForCompanyDto> jcaRoleForCompanyDtos = jcaRoleForCompanyService.getJcaRoleForCompanyByRoleId(roleId, companyId);
            result.setJcaRoleForCompanyDtos(jcaRoleForCompanyDtos);
            result.setRoleId(roleId);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022606_APPAPI_ROLE_FOR_COMPANY_INFO_ERROR);
        }
        return result;
    }
    
    private void setOrgId(Long orgId, JcaRoleForCompanyDto jcaRoleForCompanyDto) throws DetailException {
        if(null != orgId) {
            JcaOrganizationDto dto = jcaOrganizationService.getJcaOrganizationDtoById(orgId);
            if(null != dto) {
                jcaRoleForCompanyDto.setOrgId(orgId);
            }else {
                throw new DetailException(AppApiExceptionCodeConstant.E4022607_APPAPI_ROLE_FOR_COMPANY_NOT_FOUND_ORG_ERROR, new String[] { orgId.toString() }, true);
            }
        }
    }
    
    private void setRoleId(Long roleId, JcaRoleForCompanyDto jcaRoleForCompanyDto) throws DetailException {
        if(null != roleId) {
            JcaRoleDto dto = jcaRoleService.getJcaRoleDtoById(roleId);
            if(null != dto) {
                jcaRoleForCompanyDto.setRoleId(roleId);
            }else {
                throw new DetailException(AppApiExceptionCodeConstant.E4022507_APPAPI_ROLE_FOR_TEAM_NOT_FOUND_ROLE_ERROR, new String[] { roleId.toString()}, true);
            }
        }
    }
    
    private void setCompanyId(Long companyId, JcaRoleForCompanyDto jcaRoleForCompanyDto) throws DetailException {
        if(null != companyId) {
            JcaCompanyDto dto = jcaCompanyService.getJcaCompanyDtoById(companyId);
            if(null != dto) {
                jcaRoleForCompanyDto.setCompanyId(companyId);
            }else {
                throw new DetailException(AppApiExceptionCodeConstant.E4021203_APPAPI_COMPANY_NOT_FOUND,  new String[] { companyId.toString()}, true);
            }
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveListRoleForList(RoleForCompanyAddListReq roleForCompanyAddListReq) throws DetailException {
        if(CommonCollectionUtil.isNotEmpty(roleForCompanyAddListReq.getListRoleForCompanyAddReq())) {
            for (RoleForCompanyAddReq roleForCompanyAddReq : roleForCompanyAddListReq.getListRoleForCompanyAddReq()) {
                this.saveRoleForCompany(roleForCompanyAddReq);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleForCompany(RoleForCompanyAddReq roleForCompanyAddReq) throws DetailException {
        List<RoleForCompanyInforReq> listRoleForCompanyInforReq = roleForCompanyAddReq.getListRoleForCompanyInforReq();
        // delete all role by team id
        Long roleId = roleForCompanyAddReq.getRoleId();
        //jcaRoleForCompanyService.deleteAllRoleByRoleId(roleId);
        try {
            if(CommonCollectionUtil.isNotEmpty(listRoleForCompanyInforReq)) {
                for (RoleForCompanyInforReq roleForCompanyInforReq : listRoleForCompanyInforReq) {
                    JcaRoleForCompanyDto jcaRoleForCompanyDto = new JcaRoleForCompanyDto();
                    setCompanyId(roleForCompanyInforReq.getCompanyId(), jcaRoleForCompanyDto);
                    setRoleId(roleId, jcaRoleForCompanyDto);
                    setOrgId(roleForCompanyInforReq.getOrgId(), jcaRoleForCompanyDto);
                    
                    jcaRoleForCompanyService.deleteJcaRoleForCompanyById(jcaRoleForCompanyDto.getOrgId(), roleId, jcaRoleForCompanyDto.getCompanyId());
                    
                    if(null != roleForCompanyInforReq.getIsAdmin()) {
                        jcaRoleForCompanyDto.setIsAdmin(roleForCompanyInforReq.getIsAdmin());
                    }else {
                        jcaRoleForCompanyDto.setIsAdmin(false);
                    }
                    jcaRoleForCompanyService.saveJcaRoleForCompanyDto(jcaRoleForCompanyDto);
                }
            }
            
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022502_APPAPI_ROLE_FOR_TEAM_ADD_ERROR);
        }
    }

}
