/*******************************************************************************
 * Class        ：ProcessStatusInfoReq
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：SonND
 * Change log   ：2020/13/07：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> ProcessStatusInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class ProcessStatusInfoReq {

    @ApiModelProperty(notes = "Id of process", example = "1", required = true, position = 0)
    private Long processId;
    
    @ApiModelProperty(notes = "Name of status", example = "Approved", required = true, position = 0)
    private String statusName;
    
    @ApiModelProperty(notes = "Name of status", required = true, position = 0)
    private List<ProcessStatusLangInfoReq> statusLangs;

}
