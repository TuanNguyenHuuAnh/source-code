/*******************************************************************************
 * Class        JpmIdentityServiceImpl
 * Created date 2019/06/21
 * Lasted date  2019/06/21
 * Author       KhoaNA
 * Change log   2016/06/21 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.admin.service.JpmIdentityService;
import vn.com.unit.ep2p.workflow.enumdef.JpmGroupTypeEnum;

/**
 * JpmIdentityServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmIdentityServiceImpl implements JpmIdentityService {

    @Autowired
    IdentityService identityService;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(JpmIdentityServiceImpl.class);

    @Override
    public boolean createGroup(String groupId, String groupName, JpmGroupTypeEnum type) {
        boolean result = false;

        if (identityService.createGroupQuery().groupId(groupId).count() == 0) {
            Group newGroup = identityService.newGroup(groupId);
            newGroup.setName(groupName);
            newGroup.setType(type.toString());
            identityService.saveGroup(newGroup);
            result = true;
        } else {
            logger.error("Method:createGroup - Group: {} already exists - not creating", groupId);
        }

        return result;
    }

    @Override
    public boolean createUser(String userId, String firstName, String lastName, String password, String email, byte[] pictureBytes,
            List<String> groups, List<String> userInfo) {
    	
    	logger.info("JpmIdentityServiceImpl createUser(), userId", userId);
    	
        boolean result = false;
    	
    	Long countByUserId = 0L;
    	
    	try {
//    		countByUserId = identityService.createUserQuery().userId(userId).count();
        	logger.info("MBAL LOG countByUserId start");
    		UserQuery userQueryCreateUserQuery = identityService.createUserQuery();
        	logger.info("MBAL LOG userQueryCreateUserQuery");
    		UserQuery userQueryUserId = userQueryCreateUserQuery.userId(userId);
    		logger.info("MBAL LOG userQueryUserId");
			countByUserId = userQueryUserId.count();
			logger.info("MBAL LOG countByUserId end");
		} catch (Exception e) {
			logger.error("MBAL ERROR countByUserId: ", e);
			throw e;
		}

        if (countByUserId == 0) {
            User user = identityService.newUser(userId);

        	logger.info("JpmIdentityServiceImpl createUser() newUser");

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(password);
            user.setEmail(email);
            identityService.saveUser(user);
            
        	logger.info("JpmIdentityServiceImpl createUser() saveUser");

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

}
