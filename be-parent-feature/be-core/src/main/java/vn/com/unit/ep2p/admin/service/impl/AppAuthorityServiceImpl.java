/*******************************************************************************
 * Class        AuthorityServiceImpl
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.entity.JcaAuthority;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.impl.AuthorityServiceImpl;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.repository.AppAuthorityRepository;
import vn.com.unit.ep2p.admin.service.AppAuthorityService;
import vn.com.unit.ep2p.admin.service.ManualService;

/**
 * AuthorityServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Primary
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppAuthorityServiceImpl extends AuthorityServiceImpl implements AppAuthorityService {

    /** AuthorityRepository */
    @Autowired
    private AppAuthorityRepository authorityRepository;

    @Autowired
    ManualService manualService;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private CommonService comService;

    /** modelMapper */
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<JcaAuthorityDto> findAuthorityDtoListByRoleIdAndFunctionType(Long roleId, List<String> functionTypes,
            Long companyId) {
        List<JcaAuthorityDto> authorityDtoLst = new ArrayList<JcaAuthorityDto>();

        if (null != roleId) {
            authorityDtoLst = authorityRepository.findAuthorityDtoListByRoleIdAndFunctionType(roleId, functionTypes,
                    companyId);
        }
        return authorityDtoLst;
    }

    @Override
    public List<JcaAuthority> authorityDtoListToAuthorityList(List<JcaAuthorityDto> authorityDtoLst) {
        List<JcaAuthority> authorityLst = new ArrayList<JcaAuthority>();

        if (null != authorityDtoLst && !authorityDtoLst.isEmpty()) {
            for (JcaAuthorityDto authorityDto : authorityDtoLst) {

                // Check can access
                boolean canAccessFlg = authorityDto.getCanAccessFlag();
                if (canAccessFlg) {
                    JcaAuthority authority = new JcaAuthority();
                    authority.setItemId(authorityDto.getItemId());
                    authority.setRoleId(authorityDto.getRoleId());
                    authority.setAccessFlag(0l);
                    authorityLst.add(authority);
                }

                // Check can disp
                boolean canDispFlg = authorityDto.getCanDispFlg();
                if (canDispFlg) {
                    JcaAuthority authority = new JcaAuthority();
                    authority.setItemId(authorityDto.getItemId());
                    authority.setRoleId(authorityDto.getRoleId());
                    authority.setAccessFlag(1l);
                    authorityLst.add(authority);
                }

                // Check can edit
                boolean canEditFlg = authorityDto.getCanEditFlg();
                if (canEditFlg) {
                    JcaAuthority authority = new JcaAuthority();
                    authority.setItemId(authorityDto.getItemId());
                    authority.setRoleId(authorityDto.getRoleId());
                    authority.setAccessFlag(2l);
                    authorityLst.add(authority);
                }
            }
        }

        return authorityLst;
    }

    @Transactional
    @Override
    public void saveAuthority(List<JcaAuthority> authorityList, Long roleId) {
        // Delete all authority by roleId
        if (null != roleId) {
            manualService.deleteAuthorityDtoByRoleIdAndFunctionType(roleId, ConstantCore.STR_ONE);
        }

        if (!authorityList.isEmpty()) {
            // Save list authority
            List<JcaAuthority> saveList = new ArrayList<>();
            for (JcaAuthority authority : authorityList) {

                // Set created by
                authority.setCreatedDate(comService.getSystemDateTime());
                // String usernameLogin = UserProfileUtils.getUserNameLogin();
                authority.setCreatedId(UserProfileUtils.getAccountId());
                saveList.add(authority);
            }
            if (!saveList.isEmpty()) {
                saveListAuthority(saveList);
            }
        }
    }

    public void saveListAuthority(List<JcaAuthority> saveList) {
        for (JcaAuthority jcaAuthority : saveList) {
//            if (jcaAuthority.getId()!=null) {
//                authorityRepository.update(jcaAuthority);
//            } else {
            authorityRepository.create(jcaAuthority);
//            }
        }
    }

    @Transactional
    @Override
    public void saveAuthorityWithRoleType(List<JcaAuthority> authorityList, Long roleId) {
        // Delete all authority by roleId

        if (null != roleId) {
//            JcaRole role = roleRepository.findOne(roleId);
//            if (role != null) {
//                manualService.deleteAuthorityDtoByRoleIdAndFunctionByTypeRole(roleId, role.getRoleType());
//            }
        }

        if (!authorityList.isEmpty()) {
            // Save list authority
            for (JcaAuthority authority : authorityList) {

                // Set created by
                authority.setCreatedDate(comService.getSystemDateTime());
                // String usernameLogin = UserProfileUtils.getUserNameLogin();
                authority.setCreatedId(UserProfileUtils.getAccountId());
//                if (authority.getId() != null ) {
//                    authorityRepository.update(authority);
//                } else {
                authorityRepository.create(authority);
//                }

            }
        }
    }

    @Override
    public List<JcaAuthorityDto> findAuthorityListByRoleIdAndFunctionType(Long roleId, String functionType) {
        List<JcaAuthorityDto> authorityDtoLst = new ArrayList<JcaAuthorityDto>();
        if (null != roleId) {
            authorityDtoLst = authorityRepository.findAuthorityListByRoleIdAndFunctionType(roleId, functionType);
        }
        return authorityDtoLst;
    }

    @Override
    public List<JcaAuthorityDto> findAuthorityListByRoleIdAndProcessIdForProcess(Long roleId, Long processId) {
        List<JcaAuthorityDto> authorityDtoLst = new ArrayList<JcaAuthorityDto>();
        if (null != roleId) {
            String subType = "PERM";
            authorityDtoLst = authorityRepository.findAuthorityListByRoleIdAndProcessIdForProcess(roleId, processId,
                    ConstantCore.PROCESS_FUNCTION_TYPE, subType);
        }
        return authorityDtoLst;
    }

    @Transactional
    @Override
    public void saveAuthorityProcess(List<JcaAuthority> authorityList, Long roleId, Long processId, Long businessId)
            throws AppException {
        // Delete all authority by roleId
        if (null != roleId) {
            this.deleteAuthorityDtoByRoleIdAndFunctionTypeAndProcessId(roleId, ConstantCore.PROCESS_FUNCTION_TYPE,
                    processId, businessId);
        }

        if (!authorityList.isEmpty()) {
            // Save list authority
            List<JcaAuthority> saveList = new ArrayList<>();
            for (JcaAuthority authority : authorityList) {

                // Set created by
                authority.setCreatedDate(comService.getSystemDateTime());
                // String usernameLogin = UserProfileUtils.getUserNameLogin();
                authority.setCreatedId(UserProfileUtils.getAccountId());
                saveList.add(authority);
            }

            if (!saveList.isEmpty()) {
                saveListAuthority(saveList);
            }
        }
    }

    private void deleteAuthorityDtoByRoleIdAndFunctionTypeAndProcessId(Long roleId, String functionType, Long processId,
            Long businessId) {
        authorityRepository.deleteAuthorityDtoByRoleIdAndFunctionTypeForProcess(roleId, functionType, processId,
                businessId);
    }

    @Override
    public List<JcaAuthorityDto> findAuthorityListByRoleIdAndBusinessIdForService(Long roleId, Long businessId) {
        List<JcaAuthorityDto> authorityDtoLst = new ArrayList<>();
        if (null != roleId) {
            String subType = "LINK";
            authorityDtoLst = authorityRepository.findAuthorityListByRoleIdAndBusinessIdForService(roleId, businessId,
                    ConstantCore.PROCESS_FUNCTION_TYPE, subType);
        }
        return authorityDtoLst;
    }
}
