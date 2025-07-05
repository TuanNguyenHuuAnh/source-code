/*******************************************************************************
 * Class        ：CompanyConditionSearchReq
 * Created date ：2020/12/10
 * Lasted date  ：2020/12/10
 * Author       ：ngannh
 * Change log   ：2020/12/10：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * CompanyConditionSearchReq
 * 
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
@Getter
@Setter
public class CompanyConditionSearchReq {
    
    private String name;
    
    private String description;
    
    private String systemCode;
    
    private String systemName;
}
