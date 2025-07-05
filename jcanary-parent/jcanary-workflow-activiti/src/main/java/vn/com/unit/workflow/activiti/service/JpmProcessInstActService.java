/*******************************************************************************
 * Class        ：JpmProcessInstActService
 * Created date ：2021/03/05
 * Lasted date  ：2021/03/05
 * Author       ：tantm
 * Change log   ：2021/03/05：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.service;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.entity.JpmProcessInstAct;

/**
 * JpmProcessInstActService.
 *
 * @author tantm
 * @version 01-00
 * @since 01-00
 */
public interface JpmProcessInstActService extends DbRepositoryService<JpmProcessInstAct, Long>{

    /**
     * <p>
     * Save jpm process inst act.
     * </p>
     *
     * @author tantm
     * @param en
     *            type {@link JpmProcessInstAct}
     * @return {@link JpmProcessInstAct}
     */
    JpmProcessInstAct saveJpmProcessInstAct(JpmProcessInstAct en);
    
    /**
     * <p>
     * Save jpm process inst act dto.
     * </p>
     *
     * @author tantm
     * @param dto
     *            type {@link JpmProcessInstActDto}
     * @return {@link JpmProcessInstAct}
     */
    JpmProcessInstAct saveJpmProcessInstActDto(JpmProcessInstActDto dto);
    
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
    int deleteJpmProcessInstActById(Long id);
    
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
    JpmProcessInstActDto getJpmProcessInstActDtoByReference(Long refId, Integer refType);
    
}
