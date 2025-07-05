/*******************************************************************************
 * Class        ：SlaAlertCreateResult
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * SlaAlertCreateResult
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class SlaAlertCreateResult {

    /** The sla email alert id list. */
    private List<Long> slaEmailAlertIdList;
    
    /** The sla noti alert id list. */
    private List<Long> slaNotiAlertIdList;
}
