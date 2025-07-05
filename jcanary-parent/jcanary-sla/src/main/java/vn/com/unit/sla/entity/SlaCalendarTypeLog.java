/*******************************************************************************
 * Class        ：SlaCalendarTypeLog
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;
import vn.com.unit.sla.constant.SlaConstant;

/**
 * <p>
 * SlaCalendarTypeLog
 * </p>
 * 
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = SlaConstant.TABLE_SLA_CALENDAR_TYPE_LOG)
public class SlaCalendarTypeLog extends AbstractCreatedTracking{

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = SlaConstant.SEQ + SlaConstant.TABLE_SLA_CALENDAR_TYPE_LOG)
    private Long id;
    
    @Column(name = "CALENDAR_TYPE_ID")
    private Long calendarTypeId;
    
    @Column(name = "MONDAY")
    private boolean monday;
    
    @Column(name = "TUESDAY")
    private boolean tuesday;
    
    @Column(name = "WEDNESDAY")
    private boolean wednesday;

    @Column(name = "THURSDAY")
    private boolean thursday;

    @Column(name = "FRIDAY")
    private boolean friday;

    @Column(name = "SATURDAY")
    private boolean saturday;

    @Column(name = "SUNDAY")
    private boolean sunday;

    @Column(name = "START_MORNING_TIME")
    private String startMorningTime;

    @Column(name = "END_MORNING_TIME")
    private String endMorningTime;

    @Column(name = "START_AFTERNOON_TIME")
    private String startAfternoonTime;

    @Column(name = "END_AFTERNOON_TIME")
    private String endAfternoonTime;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "COMPANY_ID")
    private Long companyId;

    @Column(name = "DESCRIPTION")
    private String description;

}
