/*******************************************************************************
 * Class        ：SlaConfig
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
 * SlaConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@Table(name = SlaConstant.TABLE_SLA_CONFIG)
public class SlaConfig extends AbstractTracking{

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = SlaConstant.SEQ + SlaConstant.TABLE_SLA_CONFIG)
    private Long id;
    
    @Column(name = "SLA_NAME")
    private String slaName;
    
    @Column(name = "CALENDAR_TYPE_ID")
    private Long calendarTypeId;
    
    @Column(name = "SLA_DUE_TIME")
    private Long slaDueTime;

    @Column(name = "SLA_TIME_TYPE")
    private String slaTimeType;
    
    @Column(name = "ACTIVED")
    private boolean actived;
    
    @Column(name = "DISPLAY_ORDER")
    private Long displayOrder;

}
