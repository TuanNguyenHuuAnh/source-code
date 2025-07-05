/*******************************************************************************
 * Class        ：CommonReq
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：KhoaNA
 * Change log   ：2020/11/30：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * CommonReq
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@Setter
public abstract class CommonReq {

    /** System request Code */
    private String systemReqCode;
    
    /** App request Code */
    private String appReqCode;
}
