/*******************************************************************************
 * Class        ：FileResultDto
 * Created date ：2019/06/28
 * Lasted date  ：2019/06/28
 * Author       ：HungHT
 * Change log   ：2019/06/28：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.dto;


/**
 * FileResultDto
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class FileResultDto {

    private boolean status;
    private String filePath;
    private String fileName;
    private Long repositoryId;
    private String message;
    private String messageCode;
    private Object[] args;
    
    /**
     * Get status
     * @return boolean
     * @author HungHT
     */
    public boolean isStatus() {
        return status;
    }
    
    /**
     * Set status
     * @param   status
     *          type boolean
     * @return
     * @author  HungHT
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    /**
     * Get filePath
     * @return String
     * @author HungHT
     */
    public String getFilePath() {
        return filePath;
    }
    
    /**
     * Set filePath
     * @param   filePath
     *          type String
     * @return
     * @author  HungHT
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    /**
     * Get fileName
     * @return String
     * @author HungHT
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * Set fileName
     * @param   fileName
     *          type String
     * @return
     * @author  HungHT
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * Get repositoryId
     * @return Long
     * @author HungHT
     */
    public Long getRepositoryId() {
        return repositoryId;
    }
    
    /**
     * Set repositoryId
     * @param   repositoryId
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
    }
    
    /**
     * Get message
     * @return String
     * @author HungHT
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Set message
     * @param   message
     *          type String
     * @return
     * @author  HungHT
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Get messageCode
     * @return String
     * @author HungHT
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * Set messageCode
     * @param   messageCode
     *          type String
     * @return
     * @author  HungHT
     */
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * Get args
     * @return Object[]
     * @author HungHT
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * Set args
     * @param   args
     *          type Object[]
     * @return
     * @author  HungHT
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }
}
