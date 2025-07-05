/*******************************************************************************
 * Class        ：DocumentMainFileInfoReq
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：tantm
 * Change log   ：2021/01/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DocumentMainFileInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class DocumentMainFileInfoReq {

    @ApiModelProperty(notes = "Id of form", example = "1", required = true, position = 0)
    private Long formId;
    
    @ApiModelProperty(notes = "Id of document", example = "1", required = false, position = 0)
    private Long docId;
}
