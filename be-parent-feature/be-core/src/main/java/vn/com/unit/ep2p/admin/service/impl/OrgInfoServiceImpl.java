/*******************************************************************************
 * Class        OrgInfoServiceImpl
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.OrgNode;
import vn.com.unit.ep2p.admin.enumdef.OrgType;
import vn.com.unit.ep2p.admin.repository.OrgInfoRepository;
//import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.OrgInfoService;

/**
 * OrgInfoServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OrgInfoServiceImpl implements OrgInfoService {

    /** orgRepository */
    @Autowired
    private OrgInfoRepository orgRepository;
    
//    @Autowired
//    private CommonService comService;

    @Override
    public List<JcaOrganization> findListByParentIdAndOrgType(Long parentId, OrgType orgType) {
        String parentIdStr = null;
        if (parentId != null) {
            parentIdStr = String.valueOf(parentId);
            parentIdStr = ConstantCore.SEMI_COLON.concat(parentIdStr).concat(ConstantCore.SEMI_COLON);
        }

        return null; //orgRepository.findListByParentIdAndOrgType(("%" + parentIdStr +"%"), orgType.toString());
    }

    @Override
    public List<JcaOrganization> findDepartmentListByAccountId(Long accountId) {
        return null; //orgRepository.findDepartmentListByAccountId(accountId, comService.getSystemDateTime());
    }

    @Override
    public List<OrgNode> findOrgNodeTreeByOrgType(OrgType orgType) {
        // Get list OrgInfo
        List<JcaOrganization> orgInfoList = this.findListByOrgType(orgType);

        // Build tree
        List<OrgNode> orgTree = null;
        if (orgInfoList != null && !orgInfoList.isEmpty()) {
            orgTree = this.buildOrgTree(orgInfoList);
        }

        return orgTree;
    }

    @Override
    public List<OrgNode> findOrgNodeTreeByParentIdAndOrgType(Long parentId, OrgType orgType) {

        // Get list OrgInfo
        List<JcaOrganization> orgInfoList = this.findListByParentIdAndOrgType(parentId, orgType);

        // Build tree
        List<OrgNode> orgTree = null;
        if (orgInfoList != null && !orgInfoList.isEmpty()) {
            orgTree = buildOrgTree(orgInfoList);
        }

        return orgTree;
    }

    @Override
    public List<JcaOrganization> findListByOrgType(OrgType orgType) {
        List<JcaOrganization> orgInfoList = null;

        if (orgType != null) {
            orgInfoList = null; //orgRepository.findListByOrgType(orgType.toString());
        }

        return orgInfoList;
    }

    /**
     * Build Org Tree
     * 
     * @param listOrg
     *            type List<OrgInfo>
     * @return List<OrgNode>
     * @author KhoaNA
     */
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
                            subNode.setState(ConstantCore.OPEN);
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
                    node.setState(ConstantCore.OPEN);
                    node.setCode(org.getCode());
                }
                
                if(!rootIdLst.contains(orgId) && !tableOrgModel.containsKey(orgId))
                    rootIdLst.add(orgId);
                
                node.setChildren(listChildrenNode);
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

    @SuppressWarnings("unused")
	@Override
    public List<OrgNode> getNodeByOrgTypeCompanyId(OrgType orgType, Long companyId) {
        // Get list OrgInfo
        List<JcaOrganization> orgInfoList = null;// orgRepository.findByOrgTypeCompanyId(orgType.toString(), companyId);
        // Build tree
        List<OrgNode> orgTree = null;
        if (orgInfoList != null && !orgInfoList.isEmpty()) {
            orgTree = buildOrgTree(orgInfoList);
        }
        return orgTree;
    }

    @SuppressWarnings("unused")
	@Override
    public List<OrgNode> getNodeByParentIdOrgTypeCompanyId(Long parentId, OrgType orgType, Long companyId) {
        // Get list OrgInfo
        List<JcaOrganization> orgInfoList = null; //orgRepository.findByParentIdOrgTypeCompanyId(parentId, orgType.toString(), companyId);
        // Build tree
        List<OrgNode> orgTree = null;
        if (orgInfoList != null && !orgInfoList.isEmpty()) {
            orgTree = buildOrgTree(orgInfoList);
        }
        return orgTree;
    }

    @SuppressWarnings("unused")
	@Override
    public List<OrgNode> getNodeByCompanyIdList(List<Long> companyIds) {
        List<JcaOrganization> orgInfoList = null; //orgRepository.findByCompanyIdList(companyIds);
        // Build tree
        List<OrgNode> orgTree = null;
        if (orgInfoList != null && !orgInfoList.isEmpty()) {
            orgTree = buildOrgTree(orgInfoList);
        }
        return orgTree;
    }

	@Override
	public JcaOrganization findOrgInfoById(Long id) {
		return null; //orgRepository.findOne(id);
	}

    @SuppressWarnings("unused")
	@Override
    public List<OrgNode> getNodeByCompanyId(Long companyId) {
        // Get list OrgInfo
        List<JcaOrganization> orgInfoList = null; //orgRepository.findByOrgTypeCompanyId(null, companyId);
        // Build tree
        List<OrgNode> orgTree = null;
        if (orgInfoList != null && !orgInfoList.isEmpty()) {
            orgTree = buildOrgTree(orgInfoList);
        }
        return orgTree;
    }

	@Override
	public JcaOrganizationDto getByCodeAndCompanyId(String code,Long companyId) {
		
		return orgRepository.findByOrgCondition(code,companyId);
	}
}
