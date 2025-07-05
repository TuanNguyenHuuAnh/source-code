/*******************************************************************************
 * Class        ：activitiMembershipRepository
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：KhuongTH
 * Change log   ：2021/01/20：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;

/**
 * <p>
 * activitiMembershipRepository
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface ActivitiMembershipRepository extends DbRepository<Object, Long> {

    /**
     * <p>
     * deleteMembershipByGroupIds
     * </p>
     * .
     *
     * @param groupIds
     *            type {@link List<String>}
     * @author KhuongTH
     */
    @Modifying
    void deleteMembershipByGroupIds(@Param("groupIds") List<String> groupIds);

    /**
     * <p>
     * Creates the membership.
     * </p>
     *
     * @param listUserId
     *            type {@link List<String>}
     * @param listGroupId
     *            type {@link List<String>}
     * @author KhuongTH
     */
    @Modifying
    void createMembership(@Param("userIds") List<String> userIds, @Param("groupIds") List<String> groupIds);

    /**
     * <p>
     * Merge group.
     * </p>
     *
     * @param userIds
     *            type {@link List<String>}
     * @param groupIds
     *            type {@link List<String>}
     * @author KhuongTH
     */
    @Modifying
    void mergeGroup(@Param("userIds") List<String> userIds, @Param("groupIds") List<String> groupIds);

    /**
     * <p>
     * Merge group oracle.
     * </p>
     *
     * @param userIds
     *            type {@link List<String>}
     * @param groupIds
     *            type {@link List<String>}
     * @author KhuongTH
     */
    @Modifying
    void mergeGroupOracle(@Param("userIds") List<String> userIds, @Param("groupIds") List<String> groupIds);
}
