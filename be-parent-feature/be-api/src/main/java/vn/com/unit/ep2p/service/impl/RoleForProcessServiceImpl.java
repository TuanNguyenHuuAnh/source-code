/*******************************************************************************
 * Class        ：RoleForProcessServiceImpl
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：KhuongTH
 * Change log   ：2021/01/19：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
//import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.dto.JcaAuthorityProcessSearchDto;
import vn.com.unit.core.entity.JcaAuthority;
import vn.com.unit.core.enumdef.FunctionType;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaAuthorityService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.RoleForProcessAddListReq;
import vn.com.unit.ep2p.dto.req.RoleForProcessAddReq;
import vn.com.unit.ep2p.dto.res.RoleForProcessInfoRes;
import vn.com.unit.ep2p.service.RoleForProcessService;
import vn.com.unit.workflow.service.JpmAuthorityService;

/**
 * <p>
 * RoleForProcessServiceImpl
 * </p>
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForProcessServiceImpl extends AbstractCommonService implements RoleForProcessService {

    /** The jca authority service. */
    @Autowired
    JcaAuthorityService jcaAuthorityService;
 
    @Autowired
    JpmAuthorityService jpmAuthorityService;

    @Override
    public ObjectDataRes<JcaAuthorityDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {

        ObjectDataRes<JcaAuthorityDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaAuthority.class, JcaAuthorityService.TABLE_ALIAS_JCA_AUTHORITY);
            /** init param search repository */
            JcaAuthorityProcessSearchDto reqSearch = this.buildJcaRoleForCompanySearchDto(commonSearch);
            int totalData = this.countRoleForProcessDtoByCondition(reqSearch);
            List<JcaAuthorityDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getRoleForProcessDtoByCondition(reqSearch, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024301_APPAPI_ROLE_FOR_PROCESS_LIST_ERROR);
        }
        return resObj;

    }

    /**
     * <p>
     * Gets the role for process dto by condition.
     * </p>
     *
     * @param reqSearch
     *            type {@link JcaAuthorityProcessSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the role for process dto by condition
     * @author KhuongTH
     */
    private List<JcaAuthorityDto> getRoleForProcessDtoByCondition(JcaAuthorityProcessSearchDto reqSearch, Pageable pageable) {
        return jcaAuthorityService.getAuthorityByProcess(reqSearch, pageable);
    }

    /**
     * <p>
     * Count role for process dto by condition.
     * </p>
     *
     * @param reqSearch
     *            type {@link JcaAuthorityProcessSearchDto}
     * @return {@link int}
     * @author KhuongTH
     */
    private int countRoleForProcessDtoByCondition(JcaAuthorityProcessSearchDto reqSearch) {
        return jcaAuthorityService.countAuthorityByProcess(reqSearch);
    }

    /**
     * <p>
     * Builds the jca role for company search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaAuthorityProcessSearchDto}
     * @author KhuongTH
     */
    private JcaAuthorityProcessSearchDto buildJcaRoleForCompanySearchDto(MultiValueMap<String, String> commonSearch) {
        JcaAuthorityProcessSearchDto reqSearch = new JcaAuthorityProcessSearchDto();

        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Long roleId = null != commonSearch.getFirst("roleId") ? Long.valueOf(commonSearch.getFirst("roleId")) : null;
        Long processDeployId = null != commonSearch.getFirst("processDeployId") ? Long.valueOf(commonSearch.getFirst("processDeployId"))
                : null;

        reqSearch.setRoleId(roleId);
        reqSearch.setCompanyId(companyId);
        reqSearch.setProcessDeployId(processDeployId);
        reqSearch.setFunctionTypes(Arrays.asList(FunctionType.PROCESS.getValue()));
        return reqSearch;
    }

    @Override
    public void delete(Long id) throws DetailException {

    }

    @Override
    public JcaAuthorityDto detail(Long id) throws DetailException {
        JcaAuthorityDto jcaAuthorityDto = jcaAuthorityService.getJcaAuthorityDtoById(id);
        if (null == jcaAuthorityDto) {
            throw new DetailException(AppApiExceptionCodeConstant.E4024304_APPAPI_ROLE_FOR_PROCESS_NOT_FOUND, true);
        }
        return jcaAuthorityDto;
    }

    private List<JcaAuthorityDto> roleForProcessDtosToAuthorityDtos(List<RoleForProcessAddReq> roleForProcessAddReqs, Long roleId) {
        List<JcaAuthorityDto> authorityLst = new ArrayList<>();
        if (CommonCollectionUtil.isNotEmpty(roleForProcessAddReqs)) {
            for (RoleForProcessAddReq authorityDto : roleForProcessAddReqs) {
                // Check can access
                boolean canAccessFlg = authorityDto.isCanAccessFlg();
                if (canAccessFlg) {
                    JcaAuthorityDto authority = new JcaAuthorityDto();
                    authority.setItemId(authorityDto.getItemId());
                    authority.setRoleId(roleId);
                    authority.setAccessFlg(AppApiConstant.STR_ZERO);
                    authorityLst.add(authority);
                }

                // Check can disp
                boolean canDispFlg = authorityDto.isCanDispFlg();
                if (canDispFlg) {
                    JcaAuthorityDto authority = new JcaAuthorityDto();
                    authority.setItemId(authorityDto.getItemId());
                    authority.setRoleId(roleId);
                    authority.setAccessFlg(AppApiConstant.STR_ONE);
                    authorityLst.add(authority);
                }

                // Check can edit
                boolean canEditFlg = authorityDto.isCanEditFlg();
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

    @Transactional
    @Override
    public List<RoleForProcessInfoRes> create(RoleForProcessAddListReq roleForProcessAddListReq) throws Exception {
        Long roleId = roleForProcessAddListReq.getRoleId();
        if (Objects.nonNull(roleId)) {
            jcaAuthorityService.deleteAuthorityDtoByRoleIdAndFunctionType(roleForProcessAddListReq.getRoleId(), FunctionType.PROCESS.getValue());
        }
        List<JcaAuthorityDto> authorityList = this.roleForProcessDtosToAuthorityDtos(roleForProcessAddListReq.getData(), roleId);
        List<JcaAuthorityDto> saveList = new ArrayList<>();
        try {
            if (CommonCollectionUtil.isNotEmpty(authorityList)) {
                for (JcaAuthorityDto authority : authorityList) {
                    saveList.add(this.save(authority));
                }
            }

        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4024302_APPAPI_ROLE_FOR_PROCESS_ADD_ERROR);
        }
        return mapperJcaDtoToInfoRes(saveList);
    }

    /**
     * <p>
     * Mapper jca dto to info res.
     * </p>
     *
     * @param saveList
     *            type {@link List<JcaAuthorityDto>}
     * @return {@link List<RoleForProcessInfoRes>}
     * @author KhuongTH
     */
    private List<RoleForProcessInfoRes> mapperJcaDtoToInfoRes(List<JcaAuthorityDto> saveList) {
        List<RoleForProcessInfoRes> mappedList = new ArrayList<>();
        if (CommonCollectionUtil.isNotEmpty(saveList)) {
            for (JcaAuthorityDto authority : saveList) {
                mappedList.add(objectMapper.convertValue(authority, RoleForProcessInfoRes.class));
            }
        }
        return mappedList;
    }

    @Override
    public JcaAuthorityDto save(JcaAuthorityDto objectDto) throws DetailException {
        return objectMapper.convertValue(jcaAuthorityService.saveJcaAuthorityDto(objectDto), JcaAuthorityDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cloneRoleForProcess(Long oldProcessDeployId, Long newProcessDeployId) {
        jpmAuthorityService.cloneRoleForProcess(oldProcessDeployId, newProcessDeployId);
    }

}
