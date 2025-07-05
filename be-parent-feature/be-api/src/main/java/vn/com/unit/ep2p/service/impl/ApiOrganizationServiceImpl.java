/*******************************************************************************
 * Class        ：OrganizationServiceImpl
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：SonND
 * Change log   ：2020/12/15：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.tree.JSTree;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.tree.TreeObject;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.core.service.JcaOrganizationService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.TreeNodeDto;
import vn.com.unit.ep2p.dto.req.OrganizationAddReq;
import vn.com.unit.ep2p.dto.req.OrganizationUpdateReq;
import vn.com.unit.ep2p.dto.res.OrganizationInfoRes;
import vn.com.unit.ep2p.service.OrganizationPathService;
import vn.com.unit.ep2p.service.OrganizationService;

/**
 * OrganizationServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiOrganizationServiceImpl extends AbstractCommonService implements OrganizationService {

    private static final Long ROOT_ID = 1L;

    @Autowired
    private JcaOrganizationService jcaOrganizationService;

    @Autowired
    private OrganizationPathService organizationPathService;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<TreeObject<OrganizationInfoRes>> getListOrg() throws DetailException {
        
        List<JcaOrganizationDto> datas = new ArrayList<>();
        List<TreeObject<OrganizationInfoRes>> listTreeObject = new ArrayList<>();
        try {
            datas = jcaOrganizationService.getJcaOrganizationDto();
            TreeBuilder<OrganizationInfoRes> builder = new TreeBuilder(datas);
            builder.sortWithTree();
            listTreeObject = builder.buildTree();
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021803_APPAPI_ORGANIZATION_LIST_ERROR);
        }
        return listTreeObject;
    }
    
    private void setOrgCode(String orgCode, JcaOrganizationDto jcaOrganizationDto) throws DetailException{
        int count  = jcaOrganizationService.countJcaOrganizationDtoByOrgCode(orgCode);
        if(count > 0) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021811_APPAPI_ORGANIZATION_CODE_DUPLICATED, true);
        }else {
            jcaOrganizationDto.setCode(orgCode);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaOrganizationDto create(OrganizationAddReq organizationAddReq) throws DetailException {
        JcaOrganizationDto jcaOrganizationDto = new JcaOrganizationDto();
        try {
            // save organization
            if(CommonStringUtil.isNotBlank(organizationAddReq.getCode())){
                setOrgCode(organizationAddReq.getCode(), jcaOrganizationDto);
            }
            jcaOrganizationDto.setName(organizationAddReq.getName());
            jcaOrganizationDto.setNameAbv(organizationAddReq.getNameAbv());
//            if (null != organizationAddReq.getEffectivedDate()) {
//                jcaOrganizationDto.setEffectivedDate(
//                        CommonDateUtil.parseDate(organizationAddReq.getEffectivedDate(), AppCoreConstant.YYYYMMDD));
//            }
//
//            if (null != organizationAddReq.getExpiredDate()) {
//                jcaOrganizationDto
//                        .setExpiredDate(CommonDateUtil.parseDate(organizationAddReq.getExpiredDate(), AppCoreConstant.YYYYMMDD));
//            }
            jcaOrganizationDto.setDisplayOrder(organizationAddReq.getOrgOrder());
            if (null != organizationAddReq.getOrgParentId() && organizationAddReq.getOrgParentId() != CommonConstant.NUMBER_ZERO_L ) {
                OrganizationInfoRes organizationInfoRes = this.getOrganizationInfoResById(organizationAddReq.getOrgParentId());
                if (null != organizationInfoRes) {
                    jcaOrganizationDto.setOrgParentId(organizationAddReq.getOrgParentId());
                }

            }
            
            if(organizationAddReq.getOrgParentId() == CommonConstant.NUMBER_ZERO_L) {
                jcaOrganizationDto.setOrgParentId(organizationAddReq.getOrgParentId());
            }

            jcaOrganizationDto.setOrgType(organizationAddReq.getOrgType());
            jcaOrganizationDto.setEmail(organizationAddReq.getEmail());
            jcaOrganizationDto.setCompanyId(organizationAddReq.getCompanyId());
            jcaOrganizationDto.setPhone(organizationAddReq.getPhone());
            jcaOrganizationDto.setAddress(organizationAddReq.getAddress());
            jcaOrganizationDto.setActived(organizationAddReq.isActived());
            this.save(jcaOrganizationDto);
            
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021804_APPAPI_ORGANIZATION_ADD_ERROR);
        }
        return jcaOrganizationDto;
    }

    @Override
    public JcaOrganizationDto save(JcaOrganizationDto jcaOrganizationDto) {
        JcaOrganization jcaOrganization = jcaOrganizationService.saveJcaOrganizationDto(jcaOrganizationDto);
        jcaOrganizationDto.setOrgId(jcaOrganization.getId());
        return jcaOrganizationDto;
    }

    @Override
    public OrganizationInfoRes getOrganizationInfoResById(Long orgId) throws DetailException {
        JcaOrganizationDto jcaOrganizationDto = this.getDetailDto(orgId);
        OrganizationInfoRes organizationInfoRes = objectMapper.convertValue(jcaOrganizationDto, OrganizationInfoRes.class);
//        if (null != organizationInfoRes.getEffectivedDate()) {
//            organizationInfoRes.setStrEffectivedDate(
//                    CommonDateUtil.formatDateToString(organizationInfoRes.getEffectivedDate(), AppCoreConstant.DDMMYYYY_SLASH));
//        }
//        if (null != organizationInfoRes.getExpiredDate()) {
//            organizationInfoRes.setStrExpiredDate(
//                    CommonDateUtil.formatDateToString(organizationInfoRes.getExpiredDate(), AppCoreConstant.DDMMYYYY_SLASH));
//        }

        return organizationInfoRes;
    }

    @Override
    public JcaOrganizationDto getDetailDto(Long orgId) throws DetailException {
        JcaOrganizationDto jcaOrganizationDto = jcaOrganizationService.getJcaOrganizationDtoById(orgId);
        if (null != jcaOrganizationDto) {
            return jcaOrganizationDto;
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021802_APPAPI_ORGANIZATION_NOT_FOUND, true);
        }
    }
    
    private void setOrgParentId(Long orgParentId, JcaOrganizationDto jcaOrganizationDto) throws DetailException {
        OrganizationInfoRes organizationInfoRes = this.getOrganizationInfoResById(orgParentId);
        if (null != organizationInfoRes) {
            jcaOrganizationDto.setOrgParentId(orgParentId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OrganizationUpdateReq organizationUpdateReq) throws DetailException {
        Long id = organizationUpdateReq.getOrgId();
        JcaOrganizationDto jcaOrganizationDto = jcaOrganizationService.getJcaOrganizationDtoById(id);
        if (null != jcaOrganizationDto) {
            try {
                // save organization
                jcaOrganizationDto.setName(organizationUpdateReq.getName());
                jcaOrganizationDto.setNameAbv(organizationUpdateReq.getNameAbv());
//                if (null != organizationUpdateReq.getEffectivedDate()) {
//                    jcaOrganizationDto.setEffectivedDate(
//                            CommonDateUtil.parseDate(organizationUpdateReq.getEffectivedDate(), AppCoreConstant.YYYYMMDD));
//                }
//                if (null != organizationUpdateReq.getExpiredDate()) {
//                    jcaOrganizationDto.setExpiredDate(
//                            CommonDateUtil.parseDate(organizationUpdateReq.getExpiredDate(), AppCoreConstant.YYYYMMDD));
//                }
                jcaOrganizationDto.setDisplayOrder(organizationUpdateReq.getOrgOrder());
                if (null != organizationUpdateReq.getOrgParentId( ) && organizationUpdateReq.getOrgParentId() != CommonConstant.NUMBER_ZERO_L) {
                   setOrgParentId(organizationUpdateReq.getOrgParentId(), jcaOrganizationDto);
                }
                if(organizationUpdateReq.getOrgParentId() == CommonConstant.NUMBER_ZERO_L) {
                    jcaOrganizationDto.setOrgParentId(organizationUpdateReq.getOrgParentId());
                }
                jcaOrganizationDto.setOrgType(organizationUpdateReq.getOrgType());
                jcaOrganizationDto.setEmail(organizationUpdateReq.getEmail());
                jcaOrganizationDto.setPhone(organizationUpdateReq.getPhone());
                jcaOrganizationDto.setAddress(organizationUpdateReq.getAddress());
                jcaOrganizationDto.setActived(organizationUpdateReq.isActived());
                this.save(jcaOrganizationDto);

            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021805_APPAPI_ORGANIZATION_UPDATE_INFO_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021802_APPAPI_ORGANIZATION_NOT_FOUND, true);
        }
    }
    

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long orgId) throws DetailException {
        JcaOrganizationDto jcaOrganizationDto = jcaOrganizationService.getJcaOrganizationDtoById(orgId);
        if (null != jcaOrganizationDto) {
            try {
                jcaOrganizationService.deleteJcaOrganizationById(orgId);
                // save organization path
                Long descendantId = orgId;
                organizationPathService.deleteOrganizationPathByDescendantId(descendantId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021807_APPAPI_ORGANIZATION_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021802_APPAPI_ORGANIZATION_NOT_FOUND, true);
        }
    }
    

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.OrganizationService#getTreeNodeList(java.lang.Long)
     */
    @Override
    public List<TreeNodeDto> getTreeNodeList(Long orgId) throws DetailException {
        List<TreeNodeDto> resultList = new ArrayList<>();
        List<JcaOrganizationDto> jcaOrgList = new ArrayList<>();
        try {
            if (null == orgId) {
                orgId = ROOT_ID;
                jcaOrgList.add(this.getDetailDto(orgId));
            }else {
                jcaOrgList = jcaOrganizationService.getJcaOrganizationDtoChildByParentIdAndDepth(orgId, 1L);
            }
            if (CommonCollectionUtil.isNotEmpty(jcaOrgList)) {
                resultList = jcaOrgList.stream().map(f -> new TreeNodeDto(f.getOrgId(), f.getName(), f.getIsLeaf()))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021812_APPAPI_ORGANIZATION_GET_NODE_CHILD_ERROR);
        }
        return resultList;
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<JSTree>  getListOrgTreeCombobox() throws DetailException {
        List<JcaOrganizationDto> datas = new ArrayList<>();
        List<JSTree> listTreeObject = new ArrayList<>();
        try {
            datas = jcaOrganizationService.getJcaOrganizationDto();
            TreeBuilder<JcaOrganizationDto> builder = new TreeBuilder(datas);
            builder.sortWithTree();
            listTreeObject = builder.buildJSTree();
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021803_APPAPI_ORGANIZATION_LIST_ERROR);
        }
        return listTreeObject;        
    }
}
