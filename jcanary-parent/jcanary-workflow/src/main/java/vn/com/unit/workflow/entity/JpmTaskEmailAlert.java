/*******************************************************************************
 * Class        ：JpmTaskNotiAlert
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/

package vn.com.unit.workflow.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;
import vn.com.unit.workflow.constant.WorkflowConstant;

/**
 * <p>
 * JpmTaskNotiAlert
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_TASK_EMAIL_ALERT)
public class JpmTaskEmailAlert extends AbstractCreatedTracking {

    /** The jpm task id. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "JPM_TASK_ID")
    private Long jpmTaskId;
    
    /** The sla noti alert id. */
    @Id
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    @Column(name = "SLA_EMAIL_ALERT_ID")
    private Long slaEmailAlertId;

}