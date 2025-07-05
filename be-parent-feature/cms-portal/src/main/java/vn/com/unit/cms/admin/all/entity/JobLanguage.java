/*******************************************************************************
 * Class        ：JobLanguage
 * Created date ：2017/03/07
 * Lasted date  ：2017/03/07
 * Author       ：TranLTH
 * Change log   ：2017/03/07：01-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.entity;

import javax.persistence.Lob;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.cms.core.entity.AbstractTracking;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;

/**
 * JobLanguage
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Table(name = "m_job_language")
public class JobLanguage extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_JOB_LANGUAGE")
    private Long id;
    
    /** Column: m_job_id type BIGINT(20) */
    @Column(name = "m_job_id")
    private Long mJobId;
    
    /** Column: m_language_code type VARCHAR(30) */
    @Column(name = "m_language_code")
    private String mLanguageCode;
   
    /** Column: job_title type VARCHAR(255) */
    @Column(name = "job_title")
    private String jobTitle;
    
    /** Column: job_detail type LONGBLOB(0) */       
    @Lob
    private byte[] jobDetail;
    
    @Lob
    private byte[] jobDescription;
    
    public JobLanguage() {
    }
    
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
     * @return byte[]
     * @author TranLTH
     */
    public byte[] getJobDetail() {
        return jobDetail;
    }

    /**
     * Set jobDetail
     * @param   jobDetail
     *          type byte[]
     * @return
     * @author  TranLTH
     */
    public void setJobDetail(byte[] jobDetail) {
        this.jobDetail = jobDetail;
    }

    /**
     * Get jobDescription
     * @return byte[]
     * @author TranLTH
     */
    public byte[] getJobDescription() {
        return jobDescription;
    }

    /**
     * Set jobDescription
     * @param   jobDescription
     *          type byte[]
     * @return
     * @author  TranLTH
     */
    public void setJobDescription(byte[] jobDescription) {
        this.jobDescription = jobDescription;
    }
}
