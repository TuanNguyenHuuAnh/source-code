/*******************************************************************************
 * Class        ：SlaCalculateService
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.Date;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.dto.SlaDateResultDto;

/**
 * SlaCalculateService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaCalculateService {

    /**
     * <p>
     * Calc due date by working time.
     * </p>
     *
     * @author TrieuVD
     * @param submitDate
     *            type {@link Date}
     * @param workTime
     *            type {@link Long}
     * @param timeType
     *            type {@link Integer}
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link SlaDateResultDto}
     */
    public SlaDateResultDto calcDueDateByWorkingTime(Date submitDate, Long workTime, Integer timeType, Long calendarTypeId);

    /**
     * calcDueDate
     * 
     * @param startDate
     *            type Date
     * @param slaMinutes
     *            type int
     * @param calendarTypeId
     *            type Long
     * @return Date
     * @author TrieuVD
     * @throws DetailException 
     */
    public SlaDateResultDto calcDueDate(Date startDate, int slaMinutes, Long calendarTypeId) throws DetailException;

    /**
     * <p>
     * Calc elapsed minutes.
     * </p>
     *
     * @author TrieuVD
     * @param startDate
     *            type {@link Date}
     * @param endDate
     *            type {@link Date}
     * @param slaMinutes
     *            type {@link int}
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link SlaDateResultDto}
     */
    public SlaDateResultDto calcElapsedMinutes(Date startDate, Date endDate, int slaMinutes, Long calendarTypeId);

    /**
     * <p>
     * Get sla minutes by time type.
     * </p>
     *
     * @author TrieuVD
     * @param submitDate
     *            type {@link Date}
     * @param value
     *            type {@link Long}
     * @param timeType
     *            type {@link Integer}
     * @param calendarTypeId
     *            type {@link Long}
     * @return {@link long}
     */
    public long getSlaMinutesByTimeType(Date submitDate, Long value, Integer timeType, Long calendarTypeId);
}
