/*******************************************************************************
 * Class        ：FileDownloadResult
 * Created date ：2020/07/22
 * Lasted date  ：2020/07/22
 * Author       ：tantm
 * Change log   ：2020/07/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FileDownloadResult
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class FileDownloadResult {

    private boolean success;
    byte[] fileByteArray;
    
    private String fileName;
    private long length;
    private String mimeType;
    
    private String errorCode;
    private String errorMsg;
    
    private Long repositoryId;
    private String filePath;
    private Object[] args;
    
}
