/*******************************************************************************
 * Class        ：SlaDayOff
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;
import vn.com.unit.sla.constant.SlaConstant;

/**
 * SlaDayOff
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@Table(name = SlaConstant.TABLE_SLA_CALENDAR)
public class SlaCalendar extends AbstractAuditTracking{

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = SlaConstant.SEQ + SlaConstant.TABLE_SLA_CALENDAR)
    private Long id;

    @Column(name = "CALENDAR_DATE")
    private Date calendarDate;

    @Column(name = "CALENDAR_TYPE_ID")
    private Long calendarTypeId;
    
    @Column(name = "START_TIME")
    private String startTime;

    @Column(name = "END_TIME")
    private String endTime;

    @Column(name = "DESCRIPTION")
    private String description;
    
    //TODO TrieuVD
//    CREATED_ID
//    CREATED_DATE
//    UPDATED_ID
//    UPDATED_DATE

}
