/*******************************************************************************
 * Class        ：ProcessParamInfoReq
 * Created date ：2021/01/12
 * Lasted date  ：2021/01/12
 * Author       ：SonND
 * Change log   ：2020/12/07：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> ProcessParamInfoReq </p>
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class ProcessParamInfoReq {

    @ApiModelProperty(notes = "Id of process", example = "1", required = true, position = 0)
    private Long processId;
    
    @ApiModelProperty(notes = "Field name of parameter", example = "amount", required = true, position = 0)
    private String fieldName;
    
    @ApiModelProperty(notes = "Data type of field", example = "String", required = true, position = 0)
    private String dataType;
    
    @ApiModelProperty(notes = "Form of field name", example = "total_amount", required = true, position = 0)
    private String formFieldName;

    @ApiModelProperty(notes = "List step config",  required = true, position = 0)
    private List<ProcessParamConfigInfoReq> listParamConfig;
}
