/*******************************************************************************
 * Class        ：FormTemplateReq
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.PagingReq;

/**
 * FormTemplateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class FormTemplateReq extends PagingReq{

    private String keySearch;
    
    private Long companyId;
    
    private Long categoryId;
}
