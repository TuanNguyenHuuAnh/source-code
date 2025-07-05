/*******************************************************************************
 * Class        ：FormInfoRes
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：taitt
 * Change log   ：2020/12/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;

/**
 * FormInfoRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class FormInfoRes extends EfoFormDto{

    private String formTypeName;
    private String owner;
    private String createdDateStr;
    private String businessName;
    private String categoryName;
    
}
