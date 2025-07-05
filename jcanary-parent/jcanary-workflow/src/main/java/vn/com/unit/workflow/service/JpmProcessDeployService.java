/*******************************************************************************
 * Class        ：JpmProcessDeployService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDeploySearchDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.entity.JpmProcessDeploy;

/**
 * JpmProcessDeployService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmProcessDeployService {

    /** The Constant TABLE_ALIAS_JPM_PROCESS_DEPLOY. */
    static final String TABLE_ALIAS_JPM_PROCESS_DEPLOY = "proDeploy";

    /**
     * get JpmProcessDeployDto by id.
     *
     * @param id
     *            type {@link Long}: id of JpmProcessDeploy
     * @return {@link JpmProcessDeployDto}
     * @author KhuongTH
     */
    JpmProcessDeployDto getJpmProcessDeployDtoById(Long id);

    /**
     * check flag DELETED_ID by id.
     *
     * @param id
     *            type {@link Long}: id of JpmProcessDeploy
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmProcessDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmProcessDeploy
     *            type {@link JpmProcessDeploy}
     * @return {@link JpmProcessDeploy}
     * @author KhuongTH
     */
    JpmProcessDeploy saveJpmProcessDeploy(JpmProcessDeploy jpmProcessDeploy);

    /**
     * save JpmProcessDeployDto.
     *
     * @param jpmProcessDeployDto
     *            type {@link JpmProcessDeployDto}
     * @return {@link JpmProcessDeploy}
     * @author KhuongTH
     */
    JpmProcessDeploy saveJpmProcessDeployDto(JpmProcessDeployDto jpmProcessDeployDto);

    /**
     * Get JpmProcessDeploy lasted.
     *
     * @param companyId
     *            type {@link Long}: id of Company
     * @param businessId
     *            type {@link Long}: id of JpmBusiness
     * @return {@link JpmProcessDeployDto}
     * @author KhuongTH
     */
    JpmProcessDeployDto getJpmProcessDeployLasted(Long companyId, Long businessId);

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
    int countBySearchCondition(JpmProcessDeploySearchDto processDeploySearchDto);

    /**
     * <p>
     * Gets the process deploy dtos by condition.
     * </p>
     *
     * @param searchDto
     *            type {@link JpmProcessDeploySearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return the process deploy dtos by condition
     * @author KhuongTH
     */
    List<JpmProcessDeployDto> getProcessDeployDtosByCondition(JpmProcessDeploySearchDto searchDto, Pageable pageable);

    /**
     * <p>
     * Export process.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link JpmProcessImportExportDto}
     * @author KhuongTH
     */
    JpmProcessImportExportDto exportProcess(Long processDeployId);

    /**
     * <p>
     * Gets the jpm process deploy lasted by business code.
     * </p>
     *
     * @param companyId
     *            type {@link Long}
     * @param businessCode
     *            type {@link String}
     * @return the jpm process deploy lasted by business code
     * @author KhuongTH
     */
    JpmProcessDeployDto getJpmProcessDeployLastedByBusinessCode(Long companyId, String businessCode);
    

    /**
     * <p>
     * Gets the jpm process deploy dto by process deploy id. <b> Don't get sub-module</b>
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the jpm process deploy dto by process deploy id
     * @author KhuongTH
     */
    JpmProcessDeployDto getJpmProcessDeployDtoByProcessDeployId(Long processDeployId);

    /**
     * getJpmProcessDeployByFormId.
     *
     * @param formId
     *            type {@link Long}
     * @return {@link JpmProcessDeployDto}
     * @author taitt
     */
    List<JpmProcessDeployDto>  getJpmProcessDeployByFormId(Long formId);

	/**
	 * @author vunt
	 * @param processCode
	 * @return
	 */
	JpmProcessDeploy getJpmProcessDeployByProcessCode(String processCode);
}