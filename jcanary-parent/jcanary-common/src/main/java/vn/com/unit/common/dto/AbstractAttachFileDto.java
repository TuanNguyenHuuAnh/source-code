/*******************************************************************************
 * Class        ：AbstractAttachFileDto
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AbstractAttachFileDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Getter
@Setter
public abstract class AbstractAttachFileDto {
    private String fileName;
    private Long fileSize;
    private String fileType;
    private byte[] fileByte;
    private String contentType;
}
