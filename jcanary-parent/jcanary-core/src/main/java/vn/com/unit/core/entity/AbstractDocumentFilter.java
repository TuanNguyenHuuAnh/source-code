/*******************************************************************************
 * Class        ：AbstractDocumentFilter
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.entity;

import jp.sf.amateras.mirage.annotation.Column;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * AbstractDocumentFilter
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public abstract class AbstractDocumentFilter extends AbstractAuditTracking{
	
	
    /** Column: TASK_ID type DECIMAL(20) NOT NULL */
    @Column(name = "TASK_ID")
    private Long taskId;
    
    /** Column: DOC_ID type DECIMAL(20) NOT NULL */
    @Column(name = "DOC_ID")
    private Long docId;

}
