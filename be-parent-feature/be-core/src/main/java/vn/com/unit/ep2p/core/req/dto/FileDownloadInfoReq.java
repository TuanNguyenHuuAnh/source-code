/*******************************************************************************
 * Class        ：FileDownloadInfoReq
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：SonND
 * Change log   ：2021/01/28：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FileDownloadInfoReq
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadInfoReq {
	@ApiModelProperty(notes = "Id of repository", example = "1", required = true, position = 0)
    private Long repositoryId;
	@ApiModelProperty(notes = "File path", example = "/HDBank/CAR_RENTAL_CLONE.ozr", required = true, position = 1)
	private String filePath;
}
