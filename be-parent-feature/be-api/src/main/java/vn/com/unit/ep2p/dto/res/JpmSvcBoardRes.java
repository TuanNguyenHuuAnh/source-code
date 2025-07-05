/*******************************************************************************
 * Class        ：JpmSvcBoardRes
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：taitt
 * Change log   ：2021/01/28：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.efo.dto.EfoFormDto;

/**
 * JpmSvcBoardRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class JpmSvcBoardRes extends EfoFormDto{

    @ApiModelProperty(notes = "Encode file to Base64", position = 0)
    private String imgBase64;
    private String imageName;
}
