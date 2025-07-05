/*******************************************************************************
 * Class        FileUploadResponseDto
 * Created date 2018/08/16
 * Lasted date  2018/08/16
 * Author       phatvt
 * Change log   2018/08/1601-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

/**
 * FileUploadResponseDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public class FileUploadResponseDto {
    private String status;
    private Long idFile;
    private String message;
    
    private String content;
    private String filePath;
    
    
    public String getFilePath() {
        return filePath;
    }

    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdFile() {
        return idFile;
    }

    public void setIdFile(Long idFile) {
        this.idFile = idFile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
