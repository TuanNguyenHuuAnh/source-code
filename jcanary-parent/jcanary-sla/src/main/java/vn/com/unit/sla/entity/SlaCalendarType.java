/*******************************************************************************
 * Class        ：SlaCalendarType
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;
import vn.com.unit.sla.constant.SlaConstant;

/**
 * SlaCalendarType
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@Table(name = SlaConstant.TABLE_SLA_CALENDAR_TYPE)
public class SlaCalendarType extends AbstractTracking{

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = SlaConstant.SEQ + SlaConstant.TABLE_SLA_CALENDAR_TYPE)
    private Long id;

    @Column(name = "CODE")
    private String code;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "COMPANY_ID")
    private Long companyId;
    
    @Column(name = "ACTIVED")
    private boolean actived;
    
    @Column(name = "WORKING_HOURS")
    private String workingHours;
    
    @Column(name = "DESCRIPTION")
    private String description;
}
