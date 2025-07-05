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
import vn.com.unit.sla.dto.SlaNotiAlertDto;
import vn.com.unit.sla.entity.SlaNotiAlert;

/**
 * <p>
 * SlaNotiAlertService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface SlaNotiAlertService extends DbRepositoryService<SlaNotiAlert, Long>{

    /**
     * <p>
     * Get sla noti alert dto by id.
     * </p>
     *
     * @author TrieuVD
     * @param id
     *            type {@link Long}
     * @return {@link SlaNotiAlertDto}
     */
    public SlaNotiAlertDto getSlaNotiAlertDtoById(Long id);

    /**
     * <p>
     * Save sla noti alert.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlert
     *            type {@link SlaNotiAlert}
     * @return {@link SlaNotiAlert}
     * @throws DetailException
     *             the detail exception
     */
    public SlaNotiAlert saveSlaNotiAlert(SlaNotiAlert slaAlert) throws DetailException;

    /**
     * <p>
     * Save sla noti alert dto.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlertDto
     *            type {@link SlaNotiAlertDto}
     * @return {@link SlaNotiAlertDto}
     * @throws DetailException
     *             the detail exception
     */
    public SlaNotiAlertDto saveSlaNotiAlertDto(SlaNotiAlertDto slaAlertDto) throws DetailException;

    /**
     * <p>
     * Get sla noti alert dto list by condition.
     * </p>
     *
     * @author TrieuVD
     * @param fromDate
     *            type {@link Date}
     * @param toDate
     *            type {@link Date}
     * @param status
     *            type {@link Integer}
     * @return {@link List<SlaNotiAlertDto>}
     */
    public List<SlaNotiAlertDto> getSlaNotiAlertDtoListByCondition(Date fromDate, Date toDate, Integer status);

    /**
     * <p>
     * Update status by id list.
     * </p>
     *
     * @author TrieuVD
     * @param status
     *            type {@link Integer}
     * @param idList
     *            type {@link List<Long>}
     */
    public void updateStatusByIdList(Integer status, List<Long> idList);

    /**
     * <p>
     * Save sla noti alert dto list.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlertDtoList
     *            type {@link List<SlaNotiAlertDto>}
     */
    public void saveSlaNotiAlertDtoList(List<SlaNotiAlertDto> slaAlertDtoList);

    /**
     * <p>
     * Move sla noti alert to sla noti alert history.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlertDto
     *            type {@link SlaNotiAlertDto}
     * @param status
     *            type {@link Integer}
     * @param responseJson
     *            type {@link String}
     */
    public void moveSlaNotiAlertToSlaNotiAlertHistory(SlaNotiAlertDto slaAlertDto, Integer status, String responseJson);
    
    /**
     * <p>
     * Move sla noti alert to sla noti alert history by id.
     * </p>
     *
     * @author TrieuVD
     * @param slaAlertId
     *            type {@link Long}
     * @param status
     *            type {@link Integer}
     * @param responseJson
     *            type {@link String}
     */
    public void moveSlaNotiAlertToSlaNotiAlertHistoryById(Long slaAlertId, Integer status, String responseJson);

}
