/*******************************************************************************
 * Class        ：RoleForItemServiceImpl
 * Created date ：2020/12/24
 * Lasted date  ：2020/12/24
 * Author       ：ngannh
 * Change log   ：2020/12/24：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.dto.JcaItemDto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.service.JcaAuthorityService;
import vn.com.unit.core.service.JcaItemService;
import vn.com.unit.core.service.JcaRoleService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.req.dto.RoleForItemInfoReq;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.RoleForItemAddListReq;
import vn.com.unit.ep2p.dto.req.RoleForItemAddReq;
import vn.com.unit.ep2p.dto.res.RoleForItemInfoRes;
import vn.com.unit.ep2p.service.RoleForItemService;

/**
 * RoleForItemServiceImpl.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForItemServiceImpl extends AbstractCommonService  implements RoleForItemService {
    
    /** The jca authority service. */
    @Autowired 
    JcaAuthorityService jcaAuthorityService;

    @Autowired
    JcaItemService jcaItemService;
    
    @Autowired
    @Qualifier("jcaRoleServiceImpl")
    JcaRoleService jcaRoleService;
    
    private void setRoleId(Long roleId, JcaAuthorityDto jcaAuthorityDto) throws DetailException {
        if(null != roleId) {
            JcaRoleDto dto = jcaRoleService.getJcaRoleDtoById(roleId);
            if(null != dto) {
                jcaAuthorityDto.setRoleId(roleId);
            }else {
                throw new DetailException(AppApiExceptionCodeConstant.E4022507_APPAPI_ROLE_FOR_TEAM_NOT_FOUND_ROLE_ERROR, new String[] { roleId.toString()}, true);
            }
        }
    }
    
    private void setItemId(Long itemId, JcaAuthorityDto jcaAuthorityDto) throws DetailException {
        if(null != itemId) {
            JcaItemDto dto = jcaItemService.getJcaItemDtoById(itemId);
            if(null != dto) {
                jcaAuthorityDto.setItemId(itemId);
            }else {
                throw new DetailException(AppApiExceptionCodeConstant.E4023006_APPAPI_ROLE_FOR_ITEM_NOT_FOUND_ITEM_ERROR, new String[] { itemId.toString()}, true);
            }
        }
    }
    
    @Override
    public List<JcaAuthorityDto> authorityDtoListToAuthorityList(Long roleId, List<RoleForItemInfoReq> listRoleForItemInfoReq) throws DetailException {
        List<JcaAuthorityDto> authorityLst = new ArrayList<>();
        if (CommonCollectionUtil.isNotEmpty(listRoleForItemInfoReq)) {
            for (RoleForItemInfoReq authorityDto : listRoleForItemInfoReq) {
                // Check can access
                boolean canAccessFlg = authorityDto.getCanAccessFlg();
                if (canAccessFlg) {
                    JcaAuthorityDto authority = new JcaAuthorityDto();
                    this.setItemId(authorityDto.getItemId(), authority);
                    this.setRoleId(roleId, authority);
                    authority.setAccessFlg(AppApiConstant.STR_ZERO);
                    authorityLst.add(authority);
                }

                // Check can disp
                boolean canDispFlg = authorityDto.getCanDispFlg();
                if (canDispFlg) {
                    JcaAuthorityDto authority = new JcaAuthorityDto();
                    this.setItemId(authorityDto.getItemId(), authority);
                    this.setRoleId(roleId, authority);
                    authority.setAccessFlg(AppApiConstant.STR_ONE);
                    authorityLst.add(authority);
                }

                // Check can edit
                boolean canEditFlg = authorityDto.getCanEditFlg();
                if (canEditFlg) {
                    JcaAuthorityDto authority = new JcaAuthorityDto();
                    authority.setItemId(authorityDto.getItemId());
                    authority.setRoleId(roleId);
                    authority.setAccessFlg(AppApiConstant.STR_TWO);
                    authorityLst.add(authority);
                }
            }
        }

        return authorityLst;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleForItemAddReq(RoleForItemAddReq roleForItemAddReq) throws DetailException {
        Long roleId = roleForItemAddReq.getRoleId();
        List<JcaAuthorityDto> authorityList = this.authorityDtoListToAuthorityList(roleId, roleForItemAddReq.getListRoleForItemInfoReq());
        if (roleId != null) {
            jcaAuthorityService.deleteAuthorityDtoByRoleIdAndFunctionType(roleId, AppApiConstant.STR_ONE);
        }
        List<JcaAuthorityDto> saveList = new ArrayList<>();
        try {
            if (!authorityList.isEmpty()) {
                for (JcaAuthorityDto authority : authorityList) {
                    this.save(authority);
                    saveList.add(authority);
                }
            }
            
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023002_APPAPI_ROLE_FOR_ITEM_ADD_ERROR);
        }
    }
    
    public JcaAuthorityDto save(JcaAuthorityDto objectDto){
        return objectMapper.convertValue(jcaAuthorityService.saveJcaAuthorityDto(objectDto), JcaAuthorityDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveListRoleForItem(RoleForItemAddListReq roleForItemAddListReq) throws DetailException {
        if(CommonCollectionUtil.isNotEmpty(roleForItemAddListReq.getData())) {
            for (RoleForItemAddReq roleForItemAddReq : roleForItemAddListReq.getData()) {
                this.saveRoleForItemAddReq(roleForItemAddReq);
            }
        }
    }

    @Override
    public RoleForItemInfoRes getRoleForItemInfoResByRoleId(Long roleId, Long companyId) throws DetailException {
        RoleForItemInfoRes roleForItemInfoRes = new RoleForItemInfoRes();
        List<JcaAuthorityDto> listAuthorityDto = jcaAuthorityService.getJcaRoleForItemByRoleId(roleId, companyId);
        roleForItemInfoRes.setListJcaAuthorityDto(listAuthorityDto);
        roleForItemInfoRes.setRoleId(roleId);
        return roleForItemInfoRes;
    }


}
