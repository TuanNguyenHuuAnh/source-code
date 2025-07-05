/*******************************************************************************
 * Class        ：ProcessLangReq
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：KhuongTH
 * Change log   ：2020/12/08：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * ProcessLangReq
 * </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
public class ProcessLangReq {

    @ApiModelProperty(notes = "Code of language", example = "EN", required = true, position = 0)
    private String langCode;

    @ApiModelProperty(notes = "Name of process by language", example = "Process", required = true, position = 0)
    private String processName;
}
