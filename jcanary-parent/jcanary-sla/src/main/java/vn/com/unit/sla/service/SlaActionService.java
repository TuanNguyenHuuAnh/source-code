/*******************************************************************************
 * Class        ：SlaActionService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：NganNH
 * Change log   ：2020/11/11：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import vn.com.unit.common.sla.dto.CalculateDueDateParam;
import vn.com.unit.common.sla.dto.CalculateElapsedMinutesParam;
import vn.com.unit.common.sla.dto.CreateSlaAlertParam;
import vn.com.unit.common.sla.dto.SlaDateResult;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaAlertCreateResult;
import vn.com.unit.sla.dto.SlaConfigDetailDto;
import vn.com.unit.sla.dto.SlaDateResultDto;
import vn.com.unit.sla.dto.SlaReqParamDto;

/**
 * ActionSlaService
 * 
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface SlaActionService {

    /**
     * <p>
     * Creates the sla alert by config.
     * </p>
     *
     * @author TrieuVD
     * @param slaCreateAlertParam
     *            type {@link SlaCreateAlertParam}
     * @return {@link SlaAlertCreateResult}
     * @throws DetailException
     *             the detail exception
     */
    public SlaAlertCreateResult createSlaAlertByConfig(CreateSlaAlertParam createAlertParam) throws DetailException;

    /**
     * <p>
     * Scan sla noti alert job.
     * </p>
     *
     * @author TrieuVD
     * @param fromDate
     *            type {@link Date}
     * @param toDate
     *            type {@link Date}
     */
    public void scanSlaNotiAlertJob(Date fromDate, Date toDate);
    
    /**
     * <p>
     * Scan sla email alert job.
     * </p>
     *
     * @author TrieuVD
     * @param fromDate
     *            type {@link Date}
     * @param toDate
     *            type {@link Date}
     */
    public void scanSlaEmailAlertJob(Date fromDate, Date toDate);
    
    /**
     * <p>
     * Calculate due date.
     * </p>
     *
     * @author TrieuVD
     * @param calcDueDateParam
     *            type {@link CalculateDueDateParam}
     * @return {@link SlaDateResult}
     */
    public SlaDateResultDto calculateDueDate(CalculateDueDateParam calcDueDateParam);
    
    /**
     * <p>
     * Calculate elapsed minutes.
     * </p>
     *
     * @param slaReqParamDto
     *            type {@link SlaReqParamDto}
     * @return {@link int}
     * @author TrieuVD
     */
    public int calculateElapsedMinutes(CalculateElapsedMinutesParam calcElapsedMinutesParam);

    /**
     * <p>
     * Creates the sla noti alert.
     * </p>
     *
     * @author TrieuVD
     * @param submitDate
     *            type {@link Date}
     * @param dueDate
     *            type {@link Date}
     * @param slaConfigDetailDto
     *            type {@link SlaConfigDetailDto}
     * @param mapData
     *            type {@link Map<String,Object>}
     * @return {@link List<Long>}
     * @throws DetailException
     *             the detail exception
     */
    public List<Long> createSlaNotiAlert(Date submitDate, Date dueDate, SlaConfigDetailDto slaConfigDetailDto, Map<String, Object> mapData)
            throws DetailException;

    /**
     * <p>
     * Creates the sla email alert.
     * </p>
     *
     * @author TrieuVD
     * @param submitDate
     *            type {@link Date}
     * @param dueDate
     *            type {@link Date}
     * @param slaConfigDetailDto
     *            type {@link SlaConfigDetailDto}
     * @param mapData
     *            type {@link Map<String,Object>}
     * @return {@link List<Long>}
     * @throws DetailException
     *             the detail exception
     */
    public List<Long> createSlaEmailAlert(Date submitDate, Date dueDate, SlaConfigDetailDto slaConfigDetailDto, Map<String, Object> mapData)
            throws DetailException;
    
}
