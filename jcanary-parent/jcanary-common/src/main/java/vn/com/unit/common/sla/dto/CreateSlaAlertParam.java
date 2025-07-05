/*******************************************************************************
 * Class        ：AbstractSlaReqParamDto
 * Created date ：2020/11/12
 * Lasted date  ：2020/11/12
 * Author       ：TrieuVD
 * Change log   ：2020/11/12：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.sla.dto;

import java.util.Date;
import java.util.Map;

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
public class CreateSlaAlertParam {

    private Date submitDate;
    private Date dueDate;
    private Long slaConfigId;
    private Map<String, Object> mapData;
    
    //MAPDATA KEY
    public static final String BUSINESS_KEY = "BUSINESS_KEY";
    public static final String DOCUMENT_DATA = "docData";
    
    public static final String ASSGINEE_ID_LIST = "ASSGINEE_ID_LIST";
    public static final String SUBMITTED_ID_LIST = "SUBMITTED_ID_LIST";
    public static final String OWNER_ID_LIST = "OWNER_ID_LIST";
}
