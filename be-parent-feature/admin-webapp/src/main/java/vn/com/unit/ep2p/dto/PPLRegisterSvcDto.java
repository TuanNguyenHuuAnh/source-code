/*******************************************************************************
 * Class        :RegisterSvcDto
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :HungHT
 * Change log   :2019/04/16:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * RegisterSvcDto
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class PPLRegisterSvcDto {

    private String reportType;
    private String reportDesc;
    private String reportPath;
    private String reportName;
    private String serviceName;
    private String usedFlag;
    private String deviceType;
    private String formType;
    
    /**
     * Get reportType
     * @return String
     * @author HungHT
     */
    public String getReportType() {
        return reportType;
    }
    
    /**
     * Set reportType
     * @param   reportType
     *          type String
     * @return
     * @author  HungHT
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    /**
     * Get reportDesc
     * @return String
     * @author HungHT
     */
    public String getReportDesc() {
        return reportDesc;
    }
    
    /**
     * Set reportDesc
     * @param   reportDesc
     *          type String
     * @return
     * @author  HungHT
     */
    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }
    
    /**
     * Get reportPath
     * @return String
     * @author HungHT
     */
    public String getReportPath() {
        return reportPath;
    }
    
    /**
     * Set reportPath
     * @param   reportPath
     *          type String
     * @return
     * @author  HungHT
     */
    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }
    
    /**
     * Get reportName
     * @return String
     * @author HungHT
     */
    public String getReportName() {
        return reportName;
    }
    
    /**
     * Set reportName
     * @param   reportName
     *          type String
     * @return
     * @author  HungHT
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    
    /**
     * Get serviceName
     * @return String
     * @author HungHT
     */
    public String getServiceName() {
        return serviceName;
    }
    
    /**
     * Set serviceName
     * @param   serviceName
     *          type String
     * @return
     * @author  HungHT
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    /**
     * Get usedFlag
     * @return String
     * @author HungHT
     */
    public String getUsedFlag() {
        return usedFlag;
    }
    
    /**
     * Set usedFlag
     * @param   usedFlag
     *          type String
     * @return
     * @author  HungHT
     */
    public void setUsedFlag(String usedFlag) {
        this.usedFlag = usedFlag;
    }
    
    /**
     * Get deviceType
     * @return String
     * @author HungHT
     */
    public String getDeviceType() {
        return deviceType;
    }
    
    /**
     * Set deviceType
     * @param   deviceType
     *          type String
     * @return
     * @author  HungHT
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    
    /**
     * getFormType
     * @return
     * @author trieuvd
     */
    public String getFormType() {
        return formType;
    }

    /**
     * setFormType
     * @param formType
     * @author trieuvd
     */
    public void setFormType(String formType) {
        this.formType = formType;
    }
    
}