/*******************************************************************************
 * Class        ：WorkflowIdentityServiceImpl
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：KhoaNA
 * Change log   ：2020/11/30：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.core.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.workflow.activiti.repository.ActivitiMembershipRepository;
import vn.com.unit.workflow.core.WorkflowIdentityService;

/**
 * WorkflowIdentityServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ActivitiIdentityServiceImpl implements WorkflowIdentityService {

    @Autowired
    private IdentityService identityService;
    
    @Autowired
    private ActivitiMembershipRepository activitiMembershipRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(ActivitiIdentityServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.workflow.core.WorkflowIdentityService#setAuthenticatedUserId(java.lang.String)
     */
    @Override
    public void setAuthenticatedUserId(String authenticatedUserId) {
        identityService.setAuthenticatedUserId(authenticatedUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createGroup(String groupId, String groupName) {
        boolean result = false;

        if (identityService.createGroupQuery().groupId(groupId).count() == 0) {
            Group newGroup = identityService.newGroup(groupId);
            newGroup.setName(groupName);
            newGroup.setType("assignment");
            identityService.saveGroup(newGroup);
            result = true;
        } else {
            logger.error("Method:createGroup - Group: {} already exists - not creating", groupId);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createUser(String userId, String firstName, String lastName, String password, String email, byte[] pictureBytes,
            List<String> groups, List<String> userInfo) {
        boolean result = false;

        if (identityService.createUserQuery().userId(userId).count() == 0) {

            User user = identityService.newUser(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setEmail(email);
            identityService.saveUser(user);

            if (groups != null) {
                for (String group : groups) {
                    identityService.createMembership(userId, group);
                }
            }
        } else {
            logger.error("Method:createUser - User {} already exists - not creating.", userId);
        }

        // image
        if (pictureBytes != null && pictureBytes.length > 0) {
            Picture picture = new Picture(pictureBytes, "image/jpeg");
            identityService.setUserPicture(userId, picture);
        }

        // user info
        if (userInfo != null) {
            for (int i = 0; i < userInfo.size(); i += 2) {
                identityService.setUserInfo(userId, userInfo.get(i), userInfo.get(i + 1));
            }
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(String userId) {
        boolean result = false;

        try {
            identityService.deleteUser(userId);
            result = true;
        } catch (Exception e) {
            logger.error("Method:deleteUser - UserId :" + userId + " is not exists.");
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createMembership(String userId, String groupId) {
        boolean result = false;

        try {
            identityService.createMembership(userId, groupId);
            result = true;
        } catch (Exception e) {
            logger.error("Method:createMembership - Membership for userId " + userId + " - groupId " + groupId
                    + " already exists - not creating.");
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMembershipByUserIdAndGroupId(String userId, String groupId) {
        boolean result = false;

        try {
            identityService.deleteMembership(userId, groupId);
            result = true;
        } catch (Exception e) {
            logger.error("Method:deleteMembership - Membership for userId " + userId + " - groupId " + groupId + " is not exists.");
        }

        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMembershipByGroupIds(List<String> groupIds) {
        while (CommonCollectionUtil.isNotEmpty(groupIds)) {
            if(groupIds.size()>1000) {
                List<String> subGroupIds = groupIds.subList(0, 999);
                activitiMembershipRepository.deleteMembershipByGroupIds(subGroupIds);
                this.mergeGroup(null, subGroupIds);
                activitiMembershipRepository.createMembership(null, subGroupIds);
                groupIds.removeAll(subGroupIds);
            }else {
                activitiMembershipRepository.deleteMembershipByGroupIds(groupIds);
                this.mergeGroup(null, groupIds);
                activitiMembershipRepository.createMembership(null, groupIds);
                groupIds.clear();
            }
            
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void mergeGroup(List<String> userIds, List<String> groupIds) {
//        DatabaseTypeEnum dataType = DatabaseTypeEnum.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        String databaseType = "ORACLE"; // TODO hardcode
        switch (databaseType) {
        case "SQLSERVER":
            activitiMembershipRepository.mergeGroup(userIds, groupIds);
            break;
        case "ORACLE":
            activitiMembershipRepository.mergeGroupOracle(userIds, groupIds);
            break;
        default:
            activitiMembershipRepository.mergeGroup(userIds, groupIds);
            break;
        }
    }
}
