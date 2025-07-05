/*******************************************************************************
 * Class        ：FileDownloadOZRInfoReq
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：SonND
 * Change log   ：2021/01/28：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * FileDownloadOZRInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class FileDownloadOZRInfoReq {
	@ApiModelProperty(notes = "Id of form", example = "1", required = true, position = 0)
    private Long formId;

}
