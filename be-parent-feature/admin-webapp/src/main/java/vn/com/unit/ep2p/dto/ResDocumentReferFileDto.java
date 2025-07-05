package vn.com.unit.ep2p.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResDocumentReferFileDto {

	private Long referFileId;
	
	private String referFileName;
	
	private String referFileType;
	
	private boolean isReferFile;
	
	@JsonInclude(Include.NON_NULL)
	private String referOzdFilePath;
	
	@JsonInclude(Include.NON_NULL)
	private Long referOzdFileRepoId;
	
	private String referFilePath;
	
	private Long referFileRepoId;
	
	private String createdBy;
	
	@JsonInclude(Include.NON_NULL)
	private Date createdDate;
	
	private String created;
	
	private String modifiedBy;
	
	private String modified;
	
	@JsonInclude(Include.NON_NULL)
	private Date dated;

    @JsonInclude(Include.NON_NULL)
    private String allowEdit;
    
    /**
     * Get referFileName
     * @return String
     * @author taitt
     */
    public String getReferFileName() {
        return referFileName;
    }

    
    /**
     * Set referFileName
     * @param   referFileName
     *          type String
     * @return
     * @author  taitt
     */
    public void setReferFileName(String referFileName) {
        this.referFileName = referFileName;
    }

    
    /**
     * Get referFileType
     * @return String
     * @author taitt
     */
    public String getReferFileType() {
        return referFileType;
    }

    
    /**
     * Set referFileType
     * @param   referFileType
     *          type String
     * @return
     * @author  taitt
     */
    public void setReferFileType(String referFileType) {
        this.referFileType = referFileType;
    }

    
    /**
     * Get referOzdFilePath
     * @return String
     * @author taitt
     */
    public String getReferOzdFilePath() {
        return referOzdFilePath;
    }

    
    /**
     * Set referOzdFilePath
     * @param   referOzdFilePath
     *          type String
     * @return
     * @author  taitt
     */
    public void setReferOzdFilePath(String referOzdFilePath) {
        this.referOzdFilePath = referOzdFilePath;
    }

    
    /**
     * Get referOzdFileRepoId
     * @return Long
     * @author taitt
     */
    public Long getReferOzdFileRepoId() {
        return referOzdFileRepoId;
    }

    
    /**
     * Set referOzdFileRepoId
     * @param   referOzdFileRepoId
     *          type Long
     * @return
     * @author  taitt
     */
    public void setReferOzdFileRepoId(Long referOzdFileRepoId) {
        this.referOzdFileRepoId = referOzdFileRepoId;
    }

    
    /**
     * Get referFilePath
     * @return String
     * @author taitt
     */
    public String getReferFilePath() {
        return referFilePath;
    }

    
    /**
     * Set referFilePath
     * @param   referFilePath
     *          type String
     * @return
     * @author  taitt
     */
    public void setReferFilePath(String referFilePath) {
        this.referFilePath = referFilePath;
    }

    
    /**
     * Get referFileRepoId
     * @return Long
     * @author taitt
     */
    public Long getReferFileRepoId() {
        return referFileRepoId;
    }

    
    /**
     * Set referFileRepoId
     * @param   referFileRepoId
     *          type Long
     * @return
     * @author  taitt
     */
    public void setReferFileRepoId(Long referFileRepoId) {
        this.referFileRepoId = referFileRepoId;
    }

    
    /**
     * Get createdBy
     * @return String
     * @author taitt
     */
    public String getCreatedBy() {
        return createdBy;
    }

    
    /**
     * Set createdBy
     * @param   createdBy
     *          type String
     * @return
     * @author  taitt
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    
    /**
     * Get createdDate
     * @return Date
     * @author taitt
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    
    /**
     * Set createdDate
     * @param   createdDate
     *          type Date
     * @return
     * @author  taitt
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    
    /**
     * Get created
     * @return String
     * @author taitt
     */
    public String getCreated() {
        return created;
    }

    
    /**
     * Set created
     * @param   created
     *          type String
     * @return
     * @author  taitt
     */
    public void setCreated(String created) {
        this.created = created;
    }

    
    /**
     * Get modifiedBy
     * @return String
     * @author taitt
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    
    /**
     * Set modifiedBy
     * @param   modifiedBy
     *          type String
     * @return
     * @author  taitt
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    
    /**
     * Get modified
     * @return String
     * @author taitt
     */
    public String getModified() {
        return modified;
    }

    
    /**
     * Set modified
     * @param   modified
     *          type String
     * @return
     * @author  taitt
     */
    public void setModified(String modified) {
        this.modified = modified;
    }

    
    /**
     * Get dated
     * @return Date
     * @author taitt
     */
    public Date getDated() {
        return dated;
    }

    
    /**
     * Set dated
     * @param   dated
     *          type Date
     * @return
     * @author  taitt
     */
    public void setDated(Date dated) {
        this.dated = dated;
    }


	/**
	 * Get referFileId
	 * @return Long
	 * @author taitt
	 */
	public Long getReferFileId() {
		return referFileId;
	}


	/**
	 * Set referFileId
	 * @param   referFileId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setReferFileId(Long referFileId) {
		this.referFileId = referFileId;
	}


	/**
	 * Get isReferFile
	 * @return boolean
	 * @author taitt
	 */
	public boolean getIsReferFile() {
		return isReferFile;
	}


	/**
	 * Set isReferFile
	 * @param   isReferFile
	 *          type boolean
	 * @return
	 * @author  taitt
	 */
	public void setIsReferFile(boolean isReferFile) {
		this.isReferFile = isReferFile;
	}


	public String getAllowEdit() {
		return allowEdit;
	}


	public void setAllowEdit(String allowEdit) {
		this.allowEdit = allowEdit;
	}


	
}
