/*******************************************************************************
 * Class        ：AttachFileUploadReq
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * AttachFileUploadReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class AttachFileUploadReq {

    @ApiModelProperty(notes = "companyId of attach file", example = "1", required = true, position = 0)
    private Long companyId;
    @ApiModelProperty(notes = "referenceId of attach file", example = "1", required = false, position = 1)
    private Long referenceId;
    @ApiModelProperty(notes = "referenceKey of attach file", example = "ATTACH_FILE_2021020214070000", required = false, position = 2)
    private String referenceKey;
    @ApiModelProperty(notes = "attachType of attach file", example = "ATTACH_FILE_DOC", required = false, position = 3)
    private String attachType;
    @ApiModelProperty(notes = "attachFileList of attach file", required = true, position = 4)
    private List<AttachFileReq> attachFileList;
}
