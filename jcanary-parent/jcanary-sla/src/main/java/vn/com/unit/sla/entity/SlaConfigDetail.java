/*******************************************************************************
 * Class        ：SlaConfigDetail
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
 * SlaConfigDetail
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
@Table(name = SlaConstant.TABLE_SLA_CONFIG_DETAIL)
public class SlaConfigDetail extends AbstractTracking{

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = SlaConstant.SEQ + SlaConstant.TABLE_SLA_CONFIG_DETAIL)
    private Long id;

    @Column(name = "SLA_CONFIG_ID")
    private Long slaConfigId;
    
    @Column(name = "ALERT_TYPE")
    private Integer alertType;
    
    @Column(name = "ALERT_TIME")
    private Long alertTime;
    
    @Column(name = "ALERT_UNIT_TIME")
    private String alertUnitTime;
    
    @Column(name = "EMAIL_TEMPLATE_ID")
    private Long emailTemplateId;
    
    @Column(name = "EMAIL_SEND_FLAG")
    private boolean emailSendFlag;
    
    @Column(name = "NOTI_TEMPLATE_ID")
    private Long notiTemplateId;
    
    @Column(name = "NOTI_SEND_FLAG")
    private boolean notiSendFlag;

    @Column(name = "ACTIVED")
    private boolean actived;
}
