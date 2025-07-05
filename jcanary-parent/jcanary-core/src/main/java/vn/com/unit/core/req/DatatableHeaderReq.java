/*******************************************************************************
 * Class        ：DatatableHeaderReq
 * Created date ：2021/01/27
 * Lasted date  ：2021/01/27
 * Author       ：vinhlt
 * Change log   ：2021/01/27：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.req;

import lombok.Getter;
import lombok.Setter;

/**
 * DatatableHeaderReq
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
@Getter
@Setter
public class DatatableHeaderReq {

    private String functionCode;

    private Long userId;
    
    private String jsonConfig;
    
}
