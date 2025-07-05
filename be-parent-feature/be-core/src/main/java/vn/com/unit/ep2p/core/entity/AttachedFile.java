/*******************************************************************************
 * Class        ：AttachedFile
 * Created date ：2019/02/19
 * Lasted date  ：2019/02/19
 * Author       ：VinhLT
 * Change log   ：2019/02/19：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.ep2p.dto.AbstractEntity;

/**
 * AttachedFile
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
@Table(name = "JCA_ATTACHED_FILE")
public class AttachedFile extends AbstractEntity {

    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_ATTACHED_FILE")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_SIZE")
    private String fileSize;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "UNIQUE_FILE_NAME")
    private String uniqueFileName;

    @Column(name = "LOCATED_FOLDER")
    private String locatedFolder;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "BUSINESS_CODE")
    private String businessCode;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "REPOSITORY_ID")
    private Long repositoryId;

    @Column(name = "DELETED_BY")
    private String updatedBy;

    @Column(name = "DELETED_DATE")
    private Date updatedDate;

    /**
     * Get id
     * 
     * @return Long
     * @author VinhLT
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id
     * 
     * @param id type Long
     * @return
     * @author VinhLT
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get title
     * 
     * @return String
     * @author VinhLT
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * 
     * @param title type String
     * @return
     * @author VinhLT
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get fileName
     * 
     * @return String
     * @author VinhLT
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set fileName
     * 
     * @param fileName type String
     * @return
     * @author VinhLT
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get fileType
     * 
     * @return String
     * @author VinhLT
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Set fileType
     * 
     * @param fileType type String
     * @return
     * @author VinhLT
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * Get uniqueFileName
     * 
     * @return String
     * @author VinhLT
     */
    public String getUniqueFileName() {
        return uniqueFileName;
    }

    /**
     * Set uniqueFileName
     * 
     * @param uniqueFileName type String
     * @return
     * @author VinhLT
     */
    public void setUniqueFileName(String uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
    }

    /**
     * Get locatedFolder
     * 
     * @return String
     * @author VinhLT
     */
    public String getLocatedFolder() {
        return locatedFolder;
    }

    /**
     * Set locatedFolder
     * 
     * @param locatedFolder type String
     * @return
     * @author VinhLT
     */
    public void setLocatedFolder(String locatedFolder) {
        this.locatedFolder = locatedFolder;
    }

    /**
     * Get notes
     * 
     * @return String
     * @author VinhLT
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set notes
     * 
     * @param notes type String
     * @return
     * @author VinhLT
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Get businessCode
     * 
     * @return String
     * @author VinhLT
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * Set businessCode
     * 
     * @param businessCode type String
     * @return
     * @author VinhLT
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * Get reference
     * 
     * @return String
     * @author VinhLT
     */
    public String getReference() {
        return reference;
    }

    /**
     * Set reference
     * 
     * @param reference type String
     * @return
     * @author VinhLT
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Get repositoryId
     * 
     * @return Long
     * @author VinhLT
     */
    public Long getRepositoryId() {
        return repositoryId;
    }

    /**
     * Set repositoryId
     * 
     * @param repositoryId type Long
     * @return
     * @author VinhLT
     */
    public void setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * Get updatedBy
     * 
     * @return String
     * @author VinhLT
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set updatedBy
     * 
     * @param updatedBy type String
     * @return
     * @author VinhLT
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * Get updatedDate
     * 
     * @return Date
     * @author VinhLT
     */
    public Date getUpdatedDate() {
        return updatedDate != null ? (Date) updatedDate.clone() : null;
    }

    /**
     * Set updatedDate
     * 
     * @param updatedDate type Date
     * @return
     * @author VinhLT
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate != null ? (Date) updatedDate.clone() : null;
    }

    /**
     * Get fileSize
     * 
     * @return String
     * @author VinhLT
     */
    public String getFileSize() {
        return fileSize;
    }

    /**
     * Set fileSize
     * 
     * @param fileSize type String
     * @return
     * @author VinhLT
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

}
