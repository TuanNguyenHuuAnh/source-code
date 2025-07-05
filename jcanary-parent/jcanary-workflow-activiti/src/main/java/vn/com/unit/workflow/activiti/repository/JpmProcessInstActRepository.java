/*******************************************************************************
 * Class        ：JpmProcessInstActRepository
 * Created date ：2021/03/05
 * Lasted date  ：2021/03/05
 * Author       ：tantm
 * Change log   ：2021/03/05：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.repository;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.entity.JpmProcessInstAct;

/**
 * JpmProcessInstActRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JpmProcessInstActRepository extends DbRepository<JpmProcessInstAct, Long> {

    /**
     * <p>
     * Delete jpm process inst act by id.
     * </p>
     *
     * @author tantm
     * @param id
     *            type {@link Long}
     * @return {@link int}
     */
    @Modifying
    int deleteJpmProcessInstActById(@Param("id") Long id);

    /**
     * <p>
     * Get jpm process inst act dto by reference.
     * </p>
     *
     * @author tantm
     * @param refId
     *            type {@link Long}
     * @param refType
     *            type {@link Long}
     * @return {@link JpmProcessInstActDto}
     */
    JpmProcessInstActDto getJpmProcessInstActDtoByReference(@Param("refId") Long refId, @Param("refType") Integer refType);

}
