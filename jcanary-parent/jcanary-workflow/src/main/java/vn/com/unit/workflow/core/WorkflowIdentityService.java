/*******************************************************************************
 * Class        ：WorkflowIdentityService
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：KhoaNA
 * Change log   ：2020/11/30：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.workflow.core;

import java.util.List;

/**
 * WorkflowIdentityService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface WorkflowIdentityService {

    /**
     * Passes the authenticated user id for this particular thread. All service method (from any service) invocations done by the same
     * thread will have access to this authenticatedUserId.
     */
    void setAuthenticatedUserId(String authenticatedUserId);

    boolean createGroup(String groupId, String groupName);

    boolean createUser(String userId, String firstName, String lastName, String password, String email, byte[] pictureBytes,
            List<String> groups, List<String> userInfo);

    boolean deleteUser(String userId);

    boolean createMembership(String userId, String groupId);

    boolean deleteMembershipByUserIdAndGroupId(String userId, String groupId);
    
    /**
     * <p>
     * updateMembershipByGroupIds
     * </p>
     *
     * @param groupIds
     *            {@link void}
     * @author KhuongTH
     */
    void updateMembershipByGroupIds(List<String> groupIds);
}
