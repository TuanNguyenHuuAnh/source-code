/*******************************************************************************
 * Class        ：AttachFileReq
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * AttachFileReq
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public class AttachFileReq {

    @ApiModelProperty(notes = "fileName (full name) of attach file", example = "abc.png", required = true, position = 0)
    private String fileName;
    @ApiModelProperty(notes = "fileSize (byte) of attach file", example = "1000", required = true, position = 1)
    private Long fileSize;
    @ApiModelProperty(notes = "fileType of attach file", example = "png", required = true, position = 2)
    private String fileType;
    @ApiModelProperty(notes = "Encode file to Base64", example = "/9j/4AAQSkZJRgABAQAAAQAB....", required = true, position = 3)
    private String fileBase64;
}
