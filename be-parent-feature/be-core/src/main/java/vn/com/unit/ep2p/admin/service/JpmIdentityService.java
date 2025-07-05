/*******************************************************************************
 * Class        JpmIdentityService
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.ep2p.workflow.enumdef.JpmGroupTypeEnum;

/**
 * JpmIdentityService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface JpmIdentityService {
	
	/**
     * Create group for activiti core
     * 
     * @param groupId
     *          type String
     * @param groupName
     *          type String
     * @param type
     *          type JpmGroupTypeEnum
     * @return boolean
     * @author KhoaNA
     */
    boolean createGroup(String groupId, String groupName, JpmGroupTypeEnum type);
    
    /**
     * Create user for activiti core
     * 
     * @param userId
     *          type String
     * @param firstName
     *          type String
     * @param lastName
     *          type String
     * @param password
     *          type String
     * @param pictureBytes
     *          type byte[]
     * @param groups
     *          type List<String>
     * @param userInfo
     *          type List<String>
     * @return boolean
     * @author KhoaNA
     */
    boolean createUser(String userId, String firstName, String lastName, String password, 
    			String email, byte[] pictureBytes, List<String> groups, List<String> userInfo);
    
    /**
     * Delete membership
     * @param userId
     * 			type String
     * @return boolean
     * @author KhoaNA
     */
    boolean deleteUser(String userId);
    

}
