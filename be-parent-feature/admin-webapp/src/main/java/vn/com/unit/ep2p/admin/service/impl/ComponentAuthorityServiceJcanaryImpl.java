package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.service.ComponentAuthorityJcanaryService;
import vn.com.unit.ep2p.dto.ComponentAuthorityDto;
import vn.com.unit.ep2p.dto.ComponentAuthorityListDto;

@Service
@Transactional(rollbackFor = Exception.class)
public class ComponentAuthorityServiceJcanaryImpl implements ComponentAuthorityJcanaryService {

    @Autowired
    SystemConfig systemConfig;

//    @Autowired
//    ComponentAuthorityJcanaryRepository componentAuthorityRepository;
//    
//    @Autowired
//    private CommonService comService;

    // Model mapper
    ModelMapper modelMapper = new ModelMapper();

    /**
     * getItemListByForm
     * 
     * @param keySearch
     * @param companyid
     * @param isPaging
     * @param functionCodeAsId
     * @param mode
     * @return
     * @author HungHT
     */
    public List<Select2Dto> getItemListByForm(String keySearch, Long companyid, boolean isPaging, boolean functionCodeAsId, Long mode) {
//        return componentAuthorityRepository.getItemListByForm(keySearch, companyid, isPaging, functionCodeAsId, mode);
    	return null;
    }

    /**
     * getComponentAuthorityList
     * 
     * @param itemId
     * @param formId
     * @return
     * @author HungHT
     */
    public List<ComponentAuthorityDto> getComponentAuthorityList(Long itemId, Long formId) {
        if(null == formId) {
            return new ArrayList<>();
        }
//        return componentAuthorityRepository.getComponentAuthorityList(itemId, formId);
        return null;
    }

    /**
     * saveComponentAuthority
     * 
     * @param componentAuthority
     * @author HungHT
     */
    public void saveComponentAuthority(ComponentAuthorityListDto componentAuthority) {
//        // Delete by itemId
//        if (null != componentAuthority.getItemId()) {
//            componentAuthorityRepository.deleteByItemId(componentAuthority.getItemId());
//            if (!componentAuthority.getData().isEmpty()) {
//                // Save list authority
//                List<ComponentAuthority> authorityList = new ArrayList<>();
//                ComponentAuthority authority = null;
//                String user = UserProfileUtils.getUserNameLogin();
//                Date date = comService.getSystemDateTime();
//                for (ComponentAuthorityDto item : componentAuthority.getData()) {
//                    // Check can access
//                    Boolean canAccessFlg = item.isCanAccessFlg();
//                    if (canAccessFlg) {
//                        authority = new ComponentAuthority();
//                        authority.setCompId(item.getCompId());
//                        authority.setItemId(item.getItemId());
//                        authority.setAccessFlg(ConstantCore.STR_ZERO);
//                        authority.setCreatedDate(date);
//                        authorityList.add(authority);
//                    }
//
//                    // Check can disp
//                    Boolean canDispFlg = item.isCanDispFlg();
//                    if (canDispFlg) {
//                        authority = new ComponentAuthority();
//                        authority.setCompId(item.getCompId());
//                        authority.setItemId(item.getItemId());
//                        authority.setAccessFlg(ConstantCore.STR_ONE);
//                        authority.setCreatedDate(date);
//                        authorityList.add(authority);
//                    }
//
//                    // Check can edit
//                    Boolean canEditFlg = item.isCanEditFlg();
//                    if (canEditFlg) {
//                        authority = new ComponentAuthority();
//                        authority.setCompId(item.getCompId());
//                        authority.setItemId(item.getItemId());
//                        authority.setAccessFlg(ConstantCore.STR_TWO);
//                        authority.setCreatedBy(user);
//                        authority.setCreatedDate(date);
//                        authorityList.add(authority);
//                    }
//                }
//                componentAuthorityRepository.save(authorityList);
//            }
//        }
    }

    /**
     * getCompListByCurrentUserAndFormId
     * 
     * @param formId
     * @return
     * @author HungHT
     */
//    public List<ComponentByAuthority> getCompListByCurrentUserAndFormId(Long formId) {
//        List<ComponentByAuthority> compListResturn = new ArrayList<>();
//        List<ComponentByAuthority> compList = componentAuthorityRepository.getComponentListByAuthority(formId);
//        if (null != compList && !compList.isEmpty()) {
//            for (ComponentByAuthority component : compList) {
//                if (!UserProfileUtils.hasRole(component.getFunctionCode())
//                        && !UserProfileUtils.hasRole(component.getFunctionCode().concat(ConstantCore.COLON_DISP))
//                        && !UserProfileUtils.hasRole(component.getFunctionCode().concat(ConstantCore.COLON_EDIT))) {
//                    continue;
//                }
//                compListResturn.add(component);
//            }
//        }
//        return compListResturn;
//    }
}
