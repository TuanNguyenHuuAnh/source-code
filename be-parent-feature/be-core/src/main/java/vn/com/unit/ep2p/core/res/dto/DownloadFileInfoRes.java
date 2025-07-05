/*******************************************************************************
 * Class        ：DownloadFileInfoRes
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：SonND
 * Change log   ：2021/01/28：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.res.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * DownloadFileInfoRes
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class DownloadFileInfoRes {
    private long length;
    private String mimeType;
    private String filePath;
    private String fileName;
    private String fileType;
    private BigDecimal fileSize;
    private String base64;
}
