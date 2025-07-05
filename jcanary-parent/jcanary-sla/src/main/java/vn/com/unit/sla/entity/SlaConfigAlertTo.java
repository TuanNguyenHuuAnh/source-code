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
import vn.com.unit.db.entity.AbstractAuditTracking;
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
@Table(name = SlaConstant.TABLE_SLA_CONFIG_ALERT_TO)
public class SlaConfigAlertTo extends AbstractAuditTracking {

    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = SlaConstant.SEQ + SlaConstant.TABLE_SLA_CONFIG_ALERT_TO)
    private Long id;

    @Column(name = "SLA_CONFIG_DETAIL_ID")
    private Long slaConfigDetailId;
    
    @Column(name = "INVOLED_TYPE")
    private Long involedType;
    
    @Column(name = "RECEIVER_ID")
    private Long receiverId;
    
    @Column(name = "RECEIVER_TYPE")
    private String receiverType;

}
