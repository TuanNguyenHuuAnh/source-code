/*******************************************************************************
 * Class        ：SlaWorkingDay
 * Created date ：2021/03/10
 * Lasted date  ：2021/03/10
 * Author       ：ngannh
 * Change log   ：2021/03/10：01-00 ngannh create a new
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
import vn.com.unit.db.entity.AbstractAuditTracking;
import vn.com.unit.sla.constant.SlaConstant;

/**
 * SlaWorkingDay
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
@Table(name = SlaConstant.TABLE_SLA_WORKING_DAY)
public class SlaWorkingDay extends AbstractAuditTracking{
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = SlaConstant.SEQ + SlaConstant.TABLE_SLA_WORKING_DAY)
    private Long id;

    @Column(name = "WORKING_DAY")
    private Date workingDay;

    @Column(name = "CALENDAR_TYPE_ID")
    private Long calendarTypeId;
    
    @Column(name = "START_TIME")
    private String startTime;

    @Column(name = "END_TIME")
    private String endTime;

    @Column(name = "DESCRIPTION")
    private String description;
}
