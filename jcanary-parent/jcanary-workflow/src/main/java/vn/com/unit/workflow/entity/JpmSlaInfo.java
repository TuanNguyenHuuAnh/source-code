/*******************************************************************************
 * Class        ：JpmSlaInfo
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
import vn.com.unit.db.entity.AbstractTracking;
import vn.com.unit.workflow.constant.WorkflowConstant;

/**
 * <p>
 * JpmSlaInfo
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */

@Getter
@Setter
@Table(name = WorkflowConstant.TABLE_JPM_SLA_INFO)
public class JpmSlaInfo extends AbstractTracking {

    /** The id. */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = WorkflowConstant.SEQ + WorkflowConstant.TABLE_JPM_SLA_INFO)
    private Long id;

    /** The business id. */
    @Column(name = "BUSINESS_ID")
    private Long businessId;
    
    /** The process deploy id. */
    @Column(name = "PROCESS_DEPLOY_ID")
    private Long processDeployId;

    /** The sla calendar type id. */
    @Column(name = "SLA_CALENDAR_TYPE_ID")
    private Long slaCalendarTypeId;

    /** The name. */
    @Column(name = "NAME")
    private String name;
    
    /** The company id. */
    @Column(name = "COMPANY_ID")
    private Long companyId;

}