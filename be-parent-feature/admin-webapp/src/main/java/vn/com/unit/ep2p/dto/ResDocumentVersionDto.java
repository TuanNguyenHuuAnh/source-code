package vn.com.unit.ep2p.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResDocumentVersionDto {

	private Long mainFileId;
	
	private String mainfileName;
	
	private String mainfileType;
	
	private String mainOzdFilePath;
	
	private Long mainOzdFileRepoId;
	
	private Long majorFileVersion;
	
	private Long minorFileVersion;
	
	private String mainFilePath;
	
	private Long mainFileRepoId;
	
	private String version;
	
	private Long majorFilePdfVersion;
	
	private Long minorFilePdfVersion;
	
	private String createdBy;
	
	@JsonInclude(Include.NON_NULL)
	private Date createdDate;
	
	private String created;
	

    
    /**
     * Get mainfileName
     * @return String
     * @author taitt
     */
    public String getMainfileName() {
        return mainfileName;
    }

    
    /**
     * Set mainfileName
     * @param   mainfileName
     *          type String
     * @return
     * @author  taitt
     */
    public void setMainfileName(String mainfileName) {
        this.mainfileName = mainfileName;
    }

    
    /**
     * Get mainfileType
     * @return String
     * @author taitt
     */
    public String getMainfileType() {
        return mainfileType;
    }

    
    /**
     * Set mainfileType
     * @param   mainfileType
     *          type String
     * @return
     * @author  taitt
     */
    public void setMainfileType(String mainfileType) {
        this.mainfileType = mainfileType;
    }

    
    /**
     * Get mainOzdFilePath
     * @return String
     * @author taitt
     */
    public String getMainOzdFilePath() {
        return mainOzdFilePath;
    }

    
    /**
     * Set mainOzdFilePath
     * @param   mainOzdFilePath
     *          type String
     * @return
     * @author  taitt
     */
    public void setMainOzdFilePath(String mainOzdFilePath) {
        this.mainOzdFilePath = mainOzdFilePath;
    }

    
    /**
     * Get mainOzdFileRepoId
     * @return Long
     * @author taitt
     */
    public Long getMainOzdFileRepoId() {
        return mainOzdFileRepoId;
    }

    
    /**
     * Set mainOzdFileRepoId
     * @param   mainOzdFileRepoId
     *          type Long
     * @return
     * @author  taitt
     */
    public void setMainOzdFileRepoId(Long mainOzdFileRepoId) {
        this.mainOzdFileRepoId = mainOzdFileRepoId;
    }

    
    /**
     * Get mainFilePath
     * @return String
     * @author taitt
     */
    public String getMainFilePath() {
        return mainFilePath;
    }

    
    /**
     * Set mainFilePath
     * @param   mainFilePath
     *          type String
     * @return
     * @author  taitt
     */
    public void setMainFilePath(String mainFilePath) {
        this.mainFilePath = mainFilePath;
    }

    
    /**
     * Get mainFileRepoId
     * @return Long
     * @author taitt
     */
    public Long getMainFileRepoId() {
        return mainFileRepoId;
    }

    
    /**
     * Set mainFileRepoId
     * @param   mainFileRepoId
     *          type Long
     * @return
     * @author  taitt
     */
    public void setMainFileRepoId(Long mainFileRepoId) {
        this.mainFileRepoId = mainFileRepoId;
    }

    
    /**
     * Get version
     * @return String
     * @author taitt
     */
    public String getVersion() {
        return version;
    }

    
    /**
     * Set version
     * @param   version
     *          type String
     * @return
     * @author  taitt
     */
    public void setVersion(String version) {
        this.version = version;
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
	 * Get majorFileVersion
	 * @return String
	 * @author taitt
	 */
	public Long getMajorFileVersion() {
		return majorFileVersion;
	}


	/**
	 * Set majorFileVersion
	 * @param   majorFileVersion
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setMajorFileVersion(Long majorFileVersion) {
		this.majorFileVersion = majorFileVersion;
	}


	/**
	 * Get minorFileVersion
	 * @return String
	 * @author taitt
	 */
	public Long getMinorFileVersion() {
		return minorFileVersion;
	}


	/**
	 * Set minorFileVersion
	 * @param   minorFileVersion
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setMinorFileVersion(Long minorFileVersion) {
		this.minorFileVersion = minorFileVersion;
	}


	public Long getMajorFilePdfVersion() {
		return majorFilePdfVersion;
	}


	public void setMajorFilePdfVersion(Long majorFilePdfVersion) {
		this.majorFilePdfVersion = majorFilePdfVersion;
	}


	public Long getMinorFilePdfVersion() {
		return minorFilePdfVersion;
	}


	public void setMinorFilePdfVersion(Long minorFilePdfVersion) {
		this.minorFilePdfVersion = minorFilePdfVersion;
	}


	public Long getMainFileId() {
		return mainFileId;
	}


	public void setMainFileId(Long mainFileId) {
		this.mainFileId = mainFileId;
	}

	
}
