/*******************************************************************************
 * Class        ：MenuLangInfoReq
 * Created date ：2021/02/24
 * Lasted date  ：2021/02/24
 * Author       ：taitt
 * Change log   ：2021/02/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * MenuLangInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class MenuLangInfoReq {

    private String languageCode;
    private String name;
    private String alias;
    
    private Long id;
}
