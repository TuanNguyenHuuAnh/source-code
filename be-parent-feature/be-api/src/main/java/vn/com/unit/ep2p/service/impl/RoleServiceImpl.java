/*******************************************************************************
 * Class        ：RoleServiceImpl
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：ngannh
 * Change log   ：2020/12/02：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.core.entity.JcaRole;
import vn.com.unit.core.enumdef.param.JcaRoleSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaRoleService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.req.RoleAddReq;
import vn.com.unit.ep2p.dto.req.RoleUpdateReq;
import vn.com.unit.ep2p.dto.res.RoleInfoRes;
import vn.com.unit.ep2p.service.RoleService;

/**
 * RoleServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends AbstractCommonService implements RoleService {

    @Autowired
    @Qualifier("jcaRoleServiceImpl")
    private JcaRoleService jcaRoleService;

    @Autowired
    private MasterCommonService masterCommonService;

    private RoleInfoRes mapperJcaRoleDtoToRoleInfoRes(JcaRoleDto jcaRoleDto) {
        return objectMapper.convertValue(jcaRoleDto, RoleInfoRes.class);
    }

    @Override
    public int countRoleDtoByCondition(JcaRoleSearchDto jcaRoleSearchDto) {
        return jcaRoleService.countRoleByCondition(jcaRoleSearchDto);
    }

    @Override
    public List<JcaRoleDto> getRoleDtoByCondition(JcaRoleSearchDto jcaRoleSearchDto, Pageable pageable) {
        return jcaRoleService.getRoleByCondition(jcaRoleSearchDto, pageable);
    }


    @Override
    public JcaRoleDto save(JcaRoleDto objectDto) throws DetailException {
        JcaRole jcaRole = jcaRoleService.saveJcaRoleDto(objectDto);
        objectDto.setRoleId(jcaRole.getId());
        return objectDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws DetailException {
        JcaRoleDto jcaRoleDto = jcaRoleService.getJcaRoleDtoById(id);
        if (null != jcaRoleDto) {
            try {
                // hardcode
                jcaRoleService.deletedJcaRoleById(id);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021002_APPAPI_ROLE_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021003_APPAPI_ROLE_NOT_FOUND);
        }
    }

    @Override
    public JcaRoleDto detail(Long id) throws DetailException {
        JcaRoleDto item = jcaRoleService.getJcaRoleDtoById(id);
        if (null == item) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021003_APPAPI_ROLE_NOT_FOUND);
        }
        return item;
    }



    @Override
    public RoleInfoRes getItemInfoById(Long id) throws Exception {
        JcaRoleDto item = this.detail(id);
        return this.mapperJcaRoleDtoToRoleInfoRes(item);
    }
    
    private void setRoleCode(String roleCode, JcaRoleDto jcaRoleDto) throws DetailException {
        int count  = jcaRoleService.countJcaRoleDtoByRoleCode(roleCode);
        if (count > 0 ) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021006_APPAPI_ROLE_CODE_DUPLICATED, true);
        }else {
            jcaRoleDto.setCode(roleCode);
        }
    }
    
    private void setRoleName(String roleName, JcaRoleDto jcaRoleDto, Long roleId) throws DetailException {
        int count  = jcaRoleService.countJcaRoleDtoByRoleName(roleName, roleId);
        if (count > 0 ) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021007_APPAPI_ROLE_NAME_DUPLICATED,true);
        }else {
            jcaRoleDto.setName(roleName);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleInfoRes create(RoleAddReq roleAddReq) throws Exception {
        RoleInfoRes resObj = new RoleInfoRes();
        JcaRoleDto jcaRoleDto = new JcaRoleDto();
        try {
            if(CommonStringUtil.isNotBlank(roleAddReq.getCode())) {
                setRoleCode(roleAddReq.getCode(), jcaRoleDto);
            }
            if (CommonStringUtil.isNotBlank(roleAddReq.getName())) {
                setRoleName(roleAddReq.getName(), jcaRoleDto, null);
            }
            if (CommonStringUtil.isNotBlank(roleAddReq.getDescription())) {
                jcaRoleDto.setDescription(roleAddReq.getDescription());
            }
            jcaRoleDto.setActive(roleAddReq.getActive());
            jcaRoleDto.setCompanyId(roleAddReq.getCompanyId());
            this.save(jcaRoleDto);
            resObj = this.mapperJcaRoleDtoToRoleInfoRes(jcaRoleDto);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021001_APPAPI_ROLE_ADD_ERROR);
        }
        return resObj;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleUpdateReq roleUpdateReq) throws Exception {
        JcaRoleDto jcaRoleDto = jcaRoleService.getJcaRoleDtoById(roleUpdateReq.getRoleId());
        if (null != jcaRoleDto) {
            try {
                if (CommonStringUtil.isNotBlank(roleUpdateReq.getName())) {
                    setRoleName(roleUpdateReq.getName(), jcaRoleDto, roleUpdateReq.getRoleId());
                }
                if (CommonStringUtil.isNotBlank(roleUpdateReq.getDescription())) {
                    jcaRoleDto.setDescription(roleUpdateReq.getDescription());
                }
                jcaRoleDto.setActive(roleUpdateReq.getActive());

                this.save(jcaRoleDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021004_APPAPI_ROLE_UPDATE_INFO_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021003_APPAPI_ROLE_NOT_FOUND);
        }
    }


    @Override
    public ObjectDataRes<JcaRoleDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {

        ObjectDataRes<JcaRoleDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaRole.class,JcaRoleService.TABLE_ALIAS_JCA_ROLE);
            /** init param search repository */
            JcaRoleSearchDto reqSearch = this.buildJcaRoleSearchDto(commonSearch);

            int totalData = this.countRoleDtoByCondition(reqSearch);
            List<JcaRoleDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getRoleDtoByCondition(reqSearch, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021000_APPAPI_ROLE_LIST_ERROR);
        }
        return resObj;
    }

    /**
     * <p>
     * Builds the jca role search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaRoleSearchDto}
     * @author ngannh
     */
    private JcaRoleSearchDto buildJcaRoleSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaRoleSearchDto reqSearch = new JcaRoleSearchDto();

        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Boolean active = null != commonSearch.getFirst("active") ? Boolean.valueOf(commonSearch.getFirst("active")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;

        reqSearch.setActive(active);
        reqSearch.setCompanyId(companyId);
        
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaRoleSearchEnum.valueOf(enumValue)) {
                case CODE:
                    reqSearch.setCode(keySearch);
                    break;
                case NAME:
                    reqSearch.setName(keySearch);
                    break;
                    
                case DESCRIPTION:
                    reqSearch.setDescription(keySearch);
                    break;

                default:
                    reqSearch.setCode(keySearch);
                    reqSearch.setName(keySearch);
                    reqSearch.setDescription(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setCode(keySearch);
            reqSearch.setName(keySearch);
            reqSearch.setDescription(keySearch);
        }
        
        return reqSearch;
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaRoleSearchEnum.values());
    }
}
