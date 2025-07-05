/*******************************************************************************
 * Class        ：SlaAlertService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaEmailAlertDto;
import vn.com.unit.sla.entity.SlaEmailAlert;

/**
 * <p>
 * SlaEmailAlertService
 * </p>
 * 
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface SlaEmailAlertService extends DbRepositoryService<SlaEmailAlert, Long> {
    
    /**
     * <p>
     * Get sla email alert dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link SlaEmailAlertDto}
     */
    public SlaEmailAlertDto getSlaEmailAlertDtoById(Long id);

    /**
     * <p>
     * Save sla email alert.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlert
     *            type {@link SlaEmailAlert}
     * @return {@link SlaEmailAlert}
     * @throws DetailException
     *             the detail exception
     */
    public SlaEmailAlert saveSlaEmailAlert (SlaEmailAlert slaAlert) throws DetailException;
    
    /**
     * <p>
     * Save sla email alert dto.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlertDto
     *            type {@link SlaEmailAlertDto}
     * @return {@link SlaEmailAlertDto}
     * @throws DetailException
     *             the detail exception
     */
    public SlaEmailAlertDto saveSlaEmailAlertDto (SlaEmailAlertDto slaAlertDto) throws DetailException;

    /**
     * <p>
     * Get sla email alert dto list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param fromDate
     *            type {@link Date}
     * @param toDate
     *            type {@link Date}
     * @param status
     *            type {@link String}
     * @return {@link List<SlaEmailAlertDto>}
     */
    public List<SlaEmailAlertDto> getSlaEmailAlertDtoListByCondition(Date fromDate, Date toDate, Integer status);

    /**
     * <p>
     * Update status by id list.
     * </p>
     *
     * @author TrieuVD
     * @param status
     *            type {@link String}
     * @param idList
     *            type {@link List<Long>}
     */
    public void updateStatusByIdList(Integer status, List<Long> idList);

    /**
     * <p>
     * Save sla email alert dto list.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlertDtoList
     *            type {@link List<SlaEmailAlertDto>}
     */
    public void saveSlaEmailAlertDtoList(List<SlaEmailAlertDto> slaAlertDtoList);
    
    
    /**
     * <p>
     * Move sla alert to sla alert history.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlertDto
     *            type {@link SlaEmailAlertDto}
     * @param status
     *            type {@link String}
     * @param responseJson
     *            type {@link String}
     */
    public void moveSlaAlertToSlaAlertHistory(SlaEmailAlertDto slaAlertDto, Integer status, String responseJson);
    
    /**
     * <p>
     * Move sla email alert to sla email alert history by id.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlertId
     *            type {@link Long}
     * @param status
     *            type {@link String}
     * @param responseJson
     *            type {@link String}
     */
    public void moveSlaEmailAlertToSlaEmailAlertHistoryById(Long slaAlertId, Integer status, String responseJson);
    
}
