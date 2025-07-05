/*******************************************************************************
 * Class        OrganizationServiceImpl
 * Created date 2017/02/28
 * Lasted date  2017/02/28
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/02/2801-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.entity.JcaConstant;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JcaOrganizationPathService;
import vn.com.unit.core.service.impl.JcaOrganizationServiceImpl;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.OrgNode;
import vn.com.unit.ep2p.admin.enumdef.OrgType;
import vn.com.unit.ep2p.admin.exception.BusinessException;
import vn.com.unit.ep2p.admin.repository.OrgInfoRepository;
import vn.com.unit.ep2p.admin.service.AccountOrgService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.OrganizationService;
import vn.com.unit.ep2p.dto.CompanyDto;

/**
 * OrganizationServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
@Service
@Primary
@Transactional
public class OrganizationServiceImpl extends JcaOrganizationServiceImpl implements OrganizationService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    
    @Autowired
    private OrgInfoRepository orgInfoRepository;
    
    @Autowired
    private CommonService comService;
    
    @Autowired
    private AccountOrgService accountOrgService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private JcaOrganizationPathService jcaOrganizationPathService;


//    private static final String MODE_EDIT = "EDIT";
//    private static final String MODE_CREATE = "CREATE";
    private static final String INTEG_API = "INTEG_API";

    /**
     * Build full tree organization
     * 
     * @return OrganizationNodeModel
     * @author trieunh <trieunh@unit.com.vn>
     */
//    public OrgNode buildOrg() {
//        List<OrgInfo> listOrg = orgInfoRepository.getByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
//        OrgNode root = buildOrgTreeModel(listOrg);
//
//        return root;
//    }

    /**
     * Build organization tree model by list.
     * 
     * @param listOrg
     * @return OrganizationNodeModel
     * @author trieunh <trieunh@unit.com.vn>
     */
    public OrgNode buildOrgTreeModel(List<JcaOrganizationDto> listOrg) {
        Hashtable<Long, OrgNode> tableOrgModel = new Hashtable<>();

        for (int i = 0; i < listOrg.size(); i++) {
            JcaOrganizationDto org = listOrg.get(i);

            List<OrgNode> listChildrenNode = new ArrayList<>();
            for (int j = 0; j < listOrg.size(); j++) {
                JcaOrganizationDto subOrg = listOrg.get(j);
                if (org.getOrgId().equals(subOrg.getOrgParentId())) { //getParentOrgId
                    OrgNode subNode = tableOrgModel.get(subOrg.getOrgId());
                    if (subNode == null) {
                        subNode = new OrgNode();
                        subNode.setId(subOrg.getOrgId());
                        subNode.setText(subOrg.getName());
                        subNode.setIconCls(getOrgIcon(subOrg.getOrgType()));
//                        subNode.setState(getOrgState(subOrg.getOrgLevel()));
                        tableOrgModel.put(subOrg.getOrgId(), subNode);
                    }
                    listChildrenNode.add(subNode);
                }
            }

            OrgNode node = tableOrgModel.get(org.getOrgId());
            if (node == null) {
                node = new OrgNode();
                node.setId(org.getOrgId());
                node.setText(org.getName());
                node.setIconCls(getOrgIcon(org.getOrgType()));
                node.setState(getOrgState(org.getOrgParentId()));
            }
            node.setChildren(listChildrenNode);
            tableOrgModel.remove(org.getOrgId());
            tableOrgModel.put(org.getOrgId(), node);

        }

        OrgNode root = null;
        for (JcaOrganizationDto orgTmp : listOrg) {
            if (orgTmp.getOrgParentId().equals(0L)) {
                root = tableOrgModel.get(orgTmp.getOrgId());
                break;
            }
        }
        return root;
    }
    
    public OrgNode buildOrgTreeModelNew(List<JcaOrganizationDto> jcaOrgList) {
        Hashtable<Long, OrgNode> tableOrgModel = new Hashtable<>();

        for (int i = 0; i < jcaOrgList.size(); i++) {
            JcaOrganizationDto org = jcaOrgList.get(i);

            List<OrgNode> listChildrenNode = new ArrayList<>();
            for (int j = 0; j < jcaOrgList.size(); j++) {
                JcaOrganizationDto subOrg = jcaOrgList.get(j);
                if (org.getOrgId().equals(subOrg.getOrgParentId())) { //getParentOrgId
                    OrgNode subNode = tableOrgModel.get(subOrg.getOrgId());
                    if (subNode == null) {
                        subNode = new OrgNode();
                        subNode.setId(subOrg.getOrgId());
                        subNode.setText(subOrg.getName());
                        subNode.setIconCls(getOrgIcon(subOrg.getOrgType()));
                        subNode.setState(getOrgState(subOrg.getOrgParentId()));
                        tableOrgModel.put(subOrg.getOrgId(), subNode);
                    }
                    listChildrenNode.add(subNode);
                }
            }

            OrgNode node = tableOrgModel.get(org.getOrgId());
            if (node == null) {
                node = new OrgNode();
                node.setId(org.getOrgId());
                node.setText(org.getName());
                node.setIconCls(getOrgIcon(org.getOrgType()));
                node.setState(getOrgState(org.getOrgParentId()));
            }
            node.setChildren(listChildrenNode);
            tableOrgModel.remove(org.getOrgId());
            tableOrgModel.put(org.getOrgId(), node);

        }

        OrgNode root = null;
        for (JcaOrganizationDto orgTmp : jcaOrgList) {
            if (orgTmp.getOrgParentId() == null || orgTmp.getOrgParentId().equals(0L)) {
                root = tableOrgModel.get(orgTmp.getOrgId());
                break;
            }
        }
        return root;
    }

    /**
     * Build organization by orgId. This is detail for organization.
     * 
     * @param orgId
     * @param editable
     *            : true is edit and detail, false is new
     * @return OrganizationDto
     * @author trieunh <trieunh@unit.com.vn>
     */
    public JcaOrganizationDto buildOrgModel(long orgId, boolean editable) {
        JcaOrganizationDto organizationDto = new JcaOrganizationDto();
        JcaOrganizationDto orgInfo = this.getJcaOrganizationDtoById(orgId);
        // For detail and edit organization
        if (editable) {
            if (orgInfo != null) {
                organizationDto = objectMapper.convertValue(orgInfo, JcaOrganizationDto.class);
                // companyName
                CompanyDto companyDto = companyService.findById(orgInfo.getCompanyId());
                if (companyDto != null) {
                    organizationDto.setCompanyName(companyDto.getName());
                }
            }
        } else {
            // For new organization
            organizationDto.setOrgParentId(orgId);
            if (orgId != 1) {
                organizationDto.setCompanyId(orgInfo.getCompanyId());
            }

        }
        
        return organizationDto;
    }

    /**
     * Build role for organization
     * 
     * @param organizationDto
     * @author trieunh <trieunh@unit.com.vn>
     */

    /**
     * Update organization. If orgId equals zero, it will create new
     * organization. Else it will update current organization.
     * 
     * @param organizationDto
     * @return orgId
     * @author trieunh <trieunh@unit.com.vn>
     */
    public long updateOrg(JcaOrganizationDto organizationDto) {
        @SuppressWarnings("unused")
		String userNameLogin = UserProfileUtils.getUserNameLogin() != null ? UserProfileUtils.getUserNameLogin() : INTEG_API;
        Long userId = UserProfileUtils.getAccountId();
        
        Long companyId = null;
        long orgId = 0;
        JcaOrganization orgInfo = null;
        if (organizationDto != null /* && organizationDto.getMode() == PageMode.CREATE */) {
            // Add new organization
            // orgId of Model is current org. This field support for create
            // structure org.
            orgInfo = new JcaOrganization();
            orgInfo.setCode(organizationDto.getCode());
            orgInfo.setName(organizationDto.getName());
            orgInfo.setNameAbv(organizationDto.getNameAbv());
            orgInfo.setActived(organizationDto.isActived());
//            orgInfo.setEffectivedDate(organizationDto.getEffectivedDate());
//            orgInfo.setExpiredDate(organizationDto.getExpiredDate());
            //orgInfo.setOrgType(organizationDto.getOrgType()); //orgType = orgLevel
//            orgInfo.setOrgSubType1(organizationDto.getOrgSubType1());
            orgInfo.setEmail(organizationDto.getEmail());
            orgInfo.setPhone(organizationDto.getPhone());
            orgInfo.setAddress(organizationDto.getAddress());
//            orgInfo.setSurrogate(organizationDto.getSurrogate());
//            orgInfo.setDepartment(organizationDto.getDepartment());
//            orgInfo.setUnitCode(organizationDto.getUnitCode());
//            orgInfo.setLatitude(organizationDto.getLatitude());
//            orgInfo.setLongtitude(organizationDto.getLongtitude());
            orgInfo.setCreatedId(userId);
            orgInfo.setCreatedDate(comService.getSystemDateTime());
            // Set other fields: parentOrgId; orgLevel; orderBy; orgTreeId;
            // Get parrentOrg
            JcaOrganization parrentOrg = orgInfoRepository.findOne(organizationDto.getOrgId());
            if (parrentOrg != null) {
//                orgInfo.setParentOrgId(parrentOrg.getId());
//                orgInfo.setOrgLevel(parrentOrg.getOrgLevel() + 1);
//                orgInfo.setOrgType(orgInfo.getOrgLevel().toString()); //orgType = orgLevel
                //set companyId
                companyId = null != organizationDto.getCompanyId()? organizationDto.getCompanyId(): parrentOrg.getCompanyId();
                orgInfo.setCompanyId(companyId);
                // Set order_by
                int maxOrder = orgInfoRepository.findMaxOrderByParrentId(parrentOrg.getId());
                if (maxOrder > 0) {
                    orgInfo.setDisplayOrder(maxOrder + 1);
                } else {
                    orgInfo.setDisplayOrder(1);
                }
                orgInfoRepository.save(orgInfo);
                orgId = orgInfo.getId();
                
                // Update role for organization
                organizationDto.setOrgId(orgId);

//                String orgTreeId = parrentOrg.getOrgTreeId().concat(orgInfo.getId().toString() + ";");
//                orgInfoRepository.updateOrgTreeId(orgTreeId, orgId);
            }
        }
        if (organizationDto != null /* && organizationDto.getMode() == PageMode.EDIT */) {
            // Update organization
            orgInfo = orgInfoRepository.findOne(organizationDto.getOrgId());
            orgInfo.setCode(organizationDto.getCode());
            orgInfo.setName(organizationDto.getName());
            orgInfo.setNameAbv(organizationDto.getNameAbv());
            orgInfo.setActived(organizationDto.isActived());
//            orgInfo.setEffectivedDate(organizationDto.getEffectivedDate());
//            orgInfo.setExpiredDate(organizationDto.getExpiredDate());
//            orgInfo.setOrgSubType1(organizationDto.getOrgSubType1());
            orgInfo.setEmail(organizationDto.getEmail());
            orgInfo.setPhone(organizationDto.getPhone());
            orgInfo.setAddress(organizationDto.getAddress());
//            orgInfo.setSurrogate(organizationDto.getSurrogate());
//            orgInfo.setDepartment(organizationDto.getDepartment());
//            orgInfo.setUnitCode(organizationDto.getUnitCode());
//            orgInfo.setLatitude(organizationDto.getLatitude());
//            orgInfo.setLongtitude(organizationDto.getLongtitude());
            orgInfo.setUpdatedId(userId);
            orgInfo.setUpdatedDate(comService.getSystemDateTime());
            //update org Parent
            if(organizationDto.getOrgParentId()!=null) {
//                orgInfo.setOrgParentId(organizationDto.getOrgParentId());
            }
            
            orgInfoRepository.save(orgInfo);
            orgId = orgInfo.getId();
        }
        //update OrgId accountOrg
        accountOrgService.updateOrgIdByOrgCode(orgId, organizationDto.getCode(), companyId);
        
        return orgId;
    }


    /**
     * Delete organization
     * 
     * @param orgId
     * @author trieunh <trieunh@unit.com.vn>
     */
    public void deleteOrg(Long orgId) {
        if(orgId.equals(0L)) {
            throw new BusinessException("Not found Org Id =" + orgId);
        }
        
        List<JcaOrganizationDto> count = orgInfoRepository.getJcaOrganizationDtoChildByParentIdAndDepth(orgId, 1L);
        if(count != null && !count.isEmpty()) {
            throw new BusinessException("Can not delete this Org");
        }else {
            deleteJcaOrganizationById(orgId);
            jcaOrganizationPathService.deleteOrganizationPathByDescendantId(orgId);
        }
        /*OrganizationDto orgDto = new OrganizationDto();
        String list_org_tree = orgInfo.getOrgTreeId().split(";0")[0];
        orgDto.setOrgTreeId(list_org_tree);
        orgDto.setDeletedBy(usernameLogin);
        orgDto.setDeletedDate(comService.getSystemDateTime());        
        try{
            String databaseType = systemConfig.getConfig(SystemConfig.DBTYPE);
            if (DatabaseTypeEnum.ORACLE.toString().equals(databaseType)) {
                orgInfoRepository.updateDeletedDateOracle(orgDto);
            } else {
                orgInfoRepository.updateDeletedDate(orgDto);
            }           
            
         }catch (Exception ex){
             throw new SystemException(ex);
         }*/
    }

    /**
     * Get organization icon dependence orgType
     * 
     * @param orgType
     * @return icon
     * @author trieunh <trieunh@unit.com.vn>
     */
    private String getOrgIcon(String orgType) {
        String result = "";
        if(orgType == null) {
            return result;
        }
        if (orgType.equals(OrgType.REGION.toString())) {
            result = "icon-org";
        } else if (orgType.equals(OrgType.BRANCH.toString())) {
            result = "icon-branch";
        } else if (orgType.equals(OrgType.SECTION.toString())) {
            result = "icon-section";
        } else if (orgType.equals(OrgType.TEAM.toString())) {
            result = "icon-team";
        } else {
            result = "icon-org";
        }
        return result;
    }

    /**
     * Get organization state by orgType.
     * 
     * @param orgType
     * @return open/closed
     * @author trieunh <trieunh@unit.com.vn>
     */
    private String getOrgState(Long parentOgrID) {
        /*if (orgType.equals(OrgType.SECTION.toString())) {
            return "closed";
        } else {
            return "open";
        }*/
        return "open";
    }


    @Override
    public JcaOrganizationDto findByOrgCondition(String orgCode, Long companyId) {
        return orgInfoRepository.findByOrgCondition(orgCode, companyId);
    }
    
    @Override
    public List<JcaConstant> listDisplayOrgType (){
        // Build list constant display
//        List<ConstantDisplay> listContantDisplay = constantDisplayRepository.findByType(CONSTANT_DISPLAY_ORG_TYPE);
        return null;
    }
    
    @Override
    public List<JcaConstant> listDisplaySubOrgType (){
//        List<ConstantDisplay> listContantDisplaySubType1 = constantDisplayRepository
//                .findByType(CONSTANT_DISPLAY_ORG_SUB_TYPE_1);
        return null;
    }

    @Override
    public OrgNode findOrgByListCompanyId(List<Long> companyIds, Boolean isAdmin) {
//        List<OrgInfo> listOrg = orgInfoRepository.getByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
//        OrgNode root = buildOrgTreeModel(listOrg);
//        return root;
        return null;
    }

    @Override
    public List<OrgNode> findOrgByOrgParent(Long orgId) {
        List<OrgNode> listNode = orgInfoRepository.findNodeByOrgParent(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin(), orgId);
        for (OrgNode organizationNode : listNode) {
            organizationNode.setState(checkStateNode(organizationNode.getId()));
        }
        return listNode;
    }
    
    @Override
    public List<OrgNode> buildOrgTreeByNodeSelect(Long orgId, Long companyId) {
        List<JcaOrganization> listOrg = new ArrayList<JcaOrganization>();
        if(0L == orgId || orgId == null) {
            JcaOrganization orgInfo = orgInfoRepository.findByParentId(0L);
             listOrg = orgInfoRepository.findByParentIdOrgTypeCompanyId(orgInfo.getId(), null, companyId);
        }else {
            boolean check = true;
            while (check) {
                JcaOrganization orgInfo = orgInfoRepository.getJcaOrganizationById(orgId);
                if(orgInfo.getParentOrgId()!= 0L) {
                    List<JcaOrganization> listTamp = orgInfoRepository.findByParentIdOrgTypeCompanyId(orgInfo.getParentOrgId(), null, companyId);
                    listOrg.addAll(new ArrayList<JcaOrganization>(listTamp));
                    orgId = orgInfo.getParentOrgId();
                }else {
                    check = false; 
                }
            }
        }
        Collections.reverse(listOrg);
        List<OrgNode> tree = this.buildOrgTree(listOrg);

        return tree;
    }
    
    private List<OrgNode> buildOrgTree(List<JcaOrganization> listOrg) {
        List<OrgNode> nodeRootLst = null;

        if (listOrg != null && !listOrg.isEmpty()) {
            // Use to build tree
            HashMap<Long, OrgNode> tableOrgModel = new HashMap<>();
            // List rootId
            List<Long> rootIdLst = new ArrayList<Long>();

            for (int i = 0; i < listOrg.size(); i++) {
                JcaOrganization org = listOrg.get(i);
                Long orgId = org.getId();
                //Long parentOrgId = org.getParentOrgId();

                // Find list children
                List<OrgNode> listChildrenNode = new ArrayList<>();
                for (int j = i + 1; j < listOrg.size(); j++) {
                    JcaOrganization subOrg = listOrg.get(j);

                    // Check node child
                    if (orgId.equals(subOrg.getParentOrgId())) {

                        Long subOrgId = subOrg.getId();
                        OrgNode subNode = tableOrgModel.get(subOrgId);
                        if (subNode == null) {
                            subNode = new OrgNode();
                            subNode.setId(subOrgId);
                            subNode.setText(subOrg.getName());
                            subNode.setState(checkStateNode(subOrgId));
                            subNode.setCode(subOrg.getCode());
                            tableOrgModel.put(subOrgId, subNode);
                        }

                        listChildrenNode.add(subNode);
                    }
                }

                // Node parent of list children
                OrgNode node = tableOrgModel.get(orgId);
                if (node == null) {
                    node = new OrgNode();
                    node.setId(orgId);
                    node.setText(org.getName());
                    node.setState(checkStateNode(orgId));
                    node.setCode(org.getCode());
                }
                
                if(!rootIdLst.contains(orgId) && !tableOrgModel.containsKey(orgId))
                    rootIdLst.add(orgId);
                
                node.setChildren(listChildrenNode);
                
                if(!node.getChildren().isEmpty()) {
                    node.setState("open"); 
                }
                tableOrgModel.put(orgId, node);
                
                /*rootIdLst.add(orgId);
                if (parentOrgId != null) {
                    if (rootIdLst.contains(parentOrgId)) {
                        rootIdLst.remove(orgId);
                    }
                }*/
                
            }

            // Find list nodeRoot
            nodeRootLst = new ArrayList<OrgNode>();
            for (Long orgId : rootIdLst) {
                OrgNode node = tableOrgModel.get(orgId);
                nodeRootLst.add(node);
            }
        }

        return nodeRootLst;
    }
    
    private String checkStateNode(Long nodeId) {
        return (nodeId!= null && orgInfoRepository.countByParentId(nodeId)>0) ? "closed": "open";
            
    }
    
    @Override
    public List<String> getCodesByList(List<String> codes, Long companyId) throws SQLException {
        return orgInfoRepository.findCodesByList(codes, companyId);
        
    }

    @Override
    public boolean checkExistedOrg(List<String> list, String code) {
        try {
            if(CollectionUtils.isEmpty(list)) {
                return Boolean.FALSE;
            } else {
                Set<String> codes = list.stream().filter(da -> code.equals(da)).collect(Collectors.toSet());
                if(CollectionUtils.isNotEmpty(codes)) {
                    return Boolean.TRUE;
                }
            }
        } catch (Exception e) {
            logger.error("##checkExistedOrg## - code : " + code, e);
        }
        return Boolean.FALSE;
        
    }

    @Override
    public Long getIdByCode(String code, Long companyId) throws SQLException {
        return orgInfoRepository.findIdByCode(code, companyId);
    }

    @Override
    public JcaOrganizationDto findRootParent(String code, Long companyId) {
        return orgInfoRepository.findRootParent(code, companyId);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.admin.service.OrganizationService#buildOrg()
     */
    @Override
    public OrgNode buildOrg() {
        List<JcaOrganizationDto> listOrg = this.getJcaOrganizationDto();// orgInfoRepository.getByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        OrgNode root = buildOrgTreeModel(listOrg);

        return root;
    }
    
    @Override
    public OrgNode buildOrg(Long orgId) {
        List<JcaOrganizationDto> jcaOrgList = new ArrayList<>();
        if (null == orgId) {
            // orgId = 1L;
//            try {
                jcaOrgList.addAll(this.getJcaOrganizationDto());
                
//            } catch (DetailException e) {
//                logger.error("Exception ", e);
//            }
        }else {
            jcaOrgList = getJcaOrganizationDtoChildByParentIdAndDepth(orgId, 1L);
        }
        OrgNode root = buildOrgTreeModelNew(jcaOrgList);

        return root;
    }
    
    @Override
    public JcaOrganizationDto getDetailDto(Long orgId) throws DetailException {
        JcaOrganizationDto jcaOrganizationDto = getJcaOrganizationDtoById(orgId);
        if (null != jcaOrganizationDto) {
            return jcaOrganizationDto;
        } else {
            throw new DetailException("E4021802_APPAPI_ORGANIZATION_NOT_FOUND", true);
        }
    }

}
