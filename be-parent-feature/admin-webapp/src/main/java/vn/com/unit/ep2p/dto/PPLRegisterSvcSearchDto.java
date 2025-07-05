/*******************************************************************************
 * Class        :PPLRegisterSvcSearchDto
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * PPLRegisterSvcSearchDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class PPLRegisterSvcSearchDto extends PPLRegisterSvcDto  {
    
    private Long companyId;
    private String companyName;
    private MultipartFile file;
    private int status;
    private String message;
    private Long categoryId;
    private String businessCode;
    private String functionCode;
    
    /** 
     * @author HungHT
     */
    public PPLRegisterSvcSearchDto() {
    }

    /** 
     * PPLRegisterSvcSearchDto
     * 
     * @param companyId
     * @param companyName
     * @author HungHT
     */
    public PPLRegisterSvcSearchDto(Long companyId, String companyName) {
        super();
        this.companyId = companyId;
        this.companyName = companyName;
    }

    /**
     * Get companyId
     * @return Long
     * @author HungHT
     */
    public Long getCompanyId() {
        return companyId;
    }
    
    /**
     * Set companyId
     * @param   companyId
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    
    /**
     * Get companyName
     * @return String
     * @author HungHT
     */
    public String getCompanyName() {
        return companyName;
    }
    
    /**
     * Set companyName
     * @param   companyName
     *          type String
     * @return
     * @author  HungHT
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get file
     * @return MultipartFile
     * @author HungHT
     */
    public MultipartFile getFile() {
        return file;
    }
    
    /**
     * Set file
     * @param   file
     *          type MultipartFile
     * @return
     * @author  HungHT
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * Get status
     * @return int
     * @author HungHT
     */
    public int getStatus() {
        return status;
    }

    /**
     * Set status
     * @param   status
     *          type int
     * @return
     * @author  HungHT
     */
    public void setStatus(int status) {
        this.status = status;
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
     * Get categoryId
     * @return Long
     * @author HungHT
     */
    public Long getCategoryId() {
        return categoryId == null ? 1L : categoryId;
    }
    
    /**
     * Set categoryId
     * @param   categoryId
     *          type Long
     * @return
     * @author  HungHT
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get businessCode
     * @return String
     * @author HungHT
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * Set businessCode
     * @param   businessCode
     *          type String
     * @return
     * @author  HungHT
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * Get functionCode
     * @return String
     * @author HungHT
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * Set functionCode
     * @param   functionCode
     *          type String
     * @return
     * @author  HungHT
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }
}