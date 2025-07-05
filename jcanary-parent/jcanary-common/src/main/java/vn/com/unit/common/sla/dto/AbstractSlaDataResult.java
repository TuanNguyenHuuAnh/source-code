/*******************************************************************************
 * Class        ：AbstractSlaDataResult
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.sla.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AbstractSlaDataResult
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public abstract class AbstractSlaDataResult {

    private Long resultId;
    private boolean status;
    private String statusCode;
    private String message;
}
