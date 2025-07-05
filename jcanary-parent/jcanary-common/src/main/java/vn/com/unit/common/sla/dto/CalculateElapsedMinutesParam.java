/*******************************************************************************
 * Class        ：AbstractSlaReqParamDto
 * Created date ：2020/11/12
 * Lasted date  ：2020/11/12
 * Author       ：TrieuVD
 * Change log   ：2020/11/12：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.sla.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * AbstractSlaReqParamDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class CalculateElapsedMinutesParam {

    private Date submitDate;
    private Date completeDate;
    private int timeTotal;
    private Long slaConfigId;
}
