/*******************************************************************************
 * Class        ：JpmProcessDeployRepository
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDeploySearchDto;
import vn.com.unit.workflow.entity.JpmProcessDeploy;

/**
 * JpmProcessDeployRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmProcessDeployRepository extends DbRepository<JpmProcessDeploy, Long> {

    /**
     * Get JpmProcessDeploy lasted.
     *
     * @param companyId
     *            type {@link Long}: id of Company
     * @param businessId
     *            type {@link Long}: id of JpmBusiness
     * @param sysDate
     *            type {@link Date}: current time
     * @return {@link JpmProcessDeployDto}
     * @author KhuongTH
     */
    JpmProcessDeployDto getJpmProcessDeployLasted(@Param("companyId") Long companyId, @Param("businessId") Long businessId,
            @Param("sysDate") Date sysDate);

    /**
     * <p>
     * Count by search condition.
     * </p>
     *
     * @param processDeploySearchDto
     *            type {@link JpmProcessDeploySearchDto}
     * @return {@link int}
     * @author KhuongTH
     */
    int countBySearchCondition(@Param("searchDto") JpmProcessDeploySearchDto processDeploySearchDto);

    /**
     * <p>
     * Gets the process dtos by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link JpmProcessDeploySearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the process dtos by condition
     * @author KhuongTH
     */
    Page<JpmProcessDeployDto> getProcessDeployDtosByCondition(@Param("searchDto") JpmProcessDeploySearchDto searchDto,
            Pageable pageable);
    
    /**
     * Get JpmProcessDeploy lasted.
     *
     * @param companyId
     *            type {@link Long}: id of Company
     * @param businessCode
     *            type {@link String}: code of JpmBusiness
     * @param sysDate
     *            type {@link Date}: current time
     * @return {@link JpmProcessDeployDto}
     * @author KhuongTH
     */
    JpmProcessDeployDto getJpmProcessDeployLastedByBusinessCode(@Param("companyId") Long companyId, @Param("businessCode") String businessCode,
            @Param("sysDate") Date sysDate);
    
    /**
     * <p>
     * Get jpm process deploy by form id.
     * </p>
     *
     * @param formId
     *            type {@link Long}
     * @return {@link JpmProcessDeployDto}
     * @author taitt
     */
    List<JpmProcessDeployDto>  getJpmProcessDeployDtoByFormId(@Param("formId") Long formId);

	/**
	 * @author vunt
	 * @param processCode
	 * @return
	 */
	JpmProcessDeploy getJpmProcessDeployByProcessCode(@Param("processCode")String processCode);
}