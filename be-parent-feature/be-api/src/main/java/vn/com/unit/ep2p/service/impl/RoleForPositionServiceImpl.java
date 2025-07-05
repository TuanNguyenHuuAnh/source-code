/*******************************************************************************
 * Class        ：RoleForProcessServiceImpl
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：KhuongTH
 * Change log   ：2021/01/19：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaPositionAuthorityDto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleForPositionDto;
import vn.com.unit.core.service.JcaPositionService;
import vn.com.unit.core.service.JcaRoleForPositionService;
import vn.com.unit.core.service.JcaRoleService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.RoleForPositionAddInforReq;
import vn.com.unit.ep2p.dto.req.RoleForPositionAddListReq;
import vn.com.unit.ep2p.dto.req.RoleForPositionAddReq;
import vn.com.unit.ep2p.dto.res.RoleForPositionInfoRes;
import vn.com.unit.ep2p.service.RoleForPositionService;

/**
 * <p>
 * RoleForPositionServiceImpl
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForPositionServiceImpl extends AbstractCommonService implements RoleForPositionService {

    /** The jca role for position service. */
    @Autowired
    JcaRoleForPositionService jcaRoleForPositionService;
    
    /** The jca position service. */
    @Autowired
    JcaPositionService jcaPositionService;
    
    @Autowired
    @Qualifier("jcaRoleServiceImpl")
    JcaRoleService jcaRoleService;
    
    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.RoleForPositionService#detail(java.lang.Long)
     */
    @Override
    public RoleForPositionInfoRes detail(Long positionId, Long companyId) throws DetailException {
        RoleForPositionInfoRes result = new RoleForPositionInfoRes();
        List<JcaRoleDto> jcaRoleDtos = new ArrayList<>();
        try {
            jcaRoleDtos = jcaRoleForPositionService.getJcaRoleDtoByPositionIdAndCompanyId(positionId, companyId);
            result.setRoleDtos(jcaRoleDtos);
            result.setPositionId(positionId);
            result.setCompanyId(companyId);
            
        }catch (Exception e) {
            throw new DetailException(AppApiExceptionCodeConstant.E4024604_APPAPI_ROLE_FOR_POSITION_LIST_ERROR, true); 
        }
        return result;
    }

    /**
     * <p>
     * Role for position add req dtos to jca role for position dto.
     * </p>
     *
     * @param roleForPositionAddReqList
     *            type {@link List<RoleForPositionAddReq>}
     * @param positionId
     *            type {@link Long}
     * @return {@link List<JcaRoleForPositionDto>}
     * @author taitt
     * @throws DetailException 
     */
    private List<JcaRoleForPositionDto> roleForPositionAddReqDtosToJcaRoleForPositionDto(List<RoleForPositionAddReq> roleForPositionAddReqList, Long positionId) throws DetailException {
        List<JcaRoleForPositionDto> authorityLst = new ArrayList<>();
        if (CommonCollectionUtil.isNotEmpty(roleForPositionAddReqList)) {
            for (RoleForPositionAddReq roleForPositionAddReq : roleForPositionAddReqList) {
                JcaRoleForPositionDto  authority = new JcaRoleForPositionDto();
                authority.setPositionId(positionId);
                this.setRoleId(roleForPositionAddReq.getRoleId(), authority);
                if (null!= roleForPositionAddReq.getAuthorityFlag() && roleForPositionAddReq.getAuthorityFlag()) {
                    authority.setChecked(roleForPositionAddReq.getAuthorityFlag());
                }

                authorityLst.add(authority);
            }
        }
        return authorityLst;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.RoleForPositionService#create(vn.com.unit.mbal.api.req.dto.RoleForPositionAddListReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create( RoleForPositionAddInforReq roleForPositionAddInforReq) throws DetailException {
        try {
            Long positionId = roleForPositionAddInforReq.getPositionId();
            this.validPosition(positionId);
            
            List<RoleForPositionAddReq> roleForPositionAddReq =  roleForPositionAddInforReq.getData();
            List<JcaRoleForPositionDto> jcaRoleForPositionDto = this.roleForPositionAddReqDtosToJcaRoleForPositionDto(roleForPositionAddReq, positionId);
            JcaPositionAuthorityDto jcaPositionAuthorityDto = new JcaPositionAuthorityDto();
            jcaPositionAuthorityDto.setData(jcaRoleForPositionDto);
            jcaPositionAuthorityDto.setPositionId(positionId);
            
            this.save(jcaPositionAuthorityDto);
        }catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024601_APPAPI_ROLE_FOR_POSITION_ADD_ERROR);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRoleForPosition(RoleForPositionAddListReq roleForPositionAddListReq) throws DetailException {
        if(CommonCollectionUtil.isNotEmpty(roleForPositionAddListReq.getListRoleForPositionAddInforReqs())) {
            for (RoleForPositionAddInforReq roleForPositionAddInforReq : roleForPositionAddListReq.getListRoleForPositionAddInforReqs()) {
                this.create(roleForPositionAddInforReq);
            }
        }
    }


    /* (non-Javadoc)
     * @see vn.com.unit.mbal.service.RoleForPositionService#save(vn.com.unit.core.dto.JcaPositionAuthorityDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(JcaPositionAuthorityDto jcaPositionAuthorityDto) throws DetailException {
        Long positionId = jcaPositionAuthorityDto.getPositionId();
        try {
            jcaRoleForPositionService.saveJcaPositionAuthorityDto(jcaPositionAuthorityDto);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024601_APPAPI_ROLE_FOR_POSITION_ADD_ERROR);
        }
        
    }
    
    /**
     * <p>
     * Valid position.
     * </p>
     *
     * @param positionId
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author taitt
     */
    private void validPosition(Long positionId) throws DetailException {
        if (null == jcaPositionService.getPositionById(positionId)){
            throw new DetailException(AppApiExceptionCodeConstant.E4024603_APPAPI_ROLE_FOR_POSITION_NOT_FOUND_ERROR, new String[] {positionId.toString()}, true);
        }
    }
    
    private void setRoleId(Long roleId, JcaRoleForPositionDto jcaRoleForPositionDto) throws DetailException {
        if(null != roleId) {
            JcaRoleDto dto = jcaRoleService.getJcaRoleDtoById(roleId);
            if(null != dto) {
                jcaRoleForPositionDto.setRoleId(roleId);
            }else {
                throw new DetailException(AppApiExceptionCodeConstant.E4022507_APPAPI_ROLE_FOR_TEAM_NOT_FOUND_ROLE_ERROR, new String[] { roleId.toString() }, true);
            }
        }
    }

}
