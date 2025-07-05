/*******************************************************************************
 * Class        ：JobLanguageDto
 * Created date ：2017/03/07
 * Lasted date  ：2017/03/07
 * Author       ：TranLTH
 * Change log   ：2017/03/07：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import javax.validation.constraints.Size;

/**
 * JobLanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class JobLanguageDto {
    private Long id;
       
    private Long mJobId;
        
    private String mLanguageCode;
    
    @Size(max=255)
    private String jobTitle;
        
    private String jobDetail;

    @Size(max=65535)
    private String jobDescription;
    
    /**
     * Get id
     * @return Long
     * @author TranLTH
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get mJobId
     * @return Long
     * @author TranLTH
     */
    public Long getmJobId() {
        return mJobId;
    }

    /**
     * Set mJobId
     * @param   mJobId
     *          type Long
     * @return
     * @author  TranLTH
     */
    public void setmJobId(Long mJobId) {
        this.mJobId = mJobId;
    }
    
    /**
     * Get mLanguageCode
     * @return String
     * @author TranLTH
     */
    public String getmLanguageCode() {
        return mLanguageCode;
    }

    /**
     * Set mLanguageCode
     * @param   mLanguageCode
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setmLanguageCode(String mLanguageCode) {
        this.mLanguageCode = mLanguageCode;
    }

    /**
     * Get jobTitle
     * @return String
     * @author TranLTH
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Set jobTitle
     * @param   jobTitle
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Get jobDetail
     * @return String
     * @author TranLTH
     */
    public String getJobDetail() {
        return jobDetail;
    }

    /**
     * Set jobDetail
     * @param   jobDetail
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    /**
     * Get jobDescription
     * @return String
     * @author TranLTH
     */
    public String getJobDescription() {
        return jobDescription;
    }

    /**
     * Set jobDescription
     * @param   jobDescription
     *          type String
     * @return
     * @author  TranLTH
     */
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }    
}
