/*******************************************************************************
 * Class        ：OZDocHistoryTrackDto
 * Created date ：2019/10/24
 * Lasted date  ：2019/10/24
 * Author       ：taitt
 * Change log   ：2019/10/24：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;


/**
 * OZDocHistoryTrackDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class OZDocHistoryTrackDto {

    private Long id;
    
    private Long docId;
    
    private Long mainFileId;
    
    private Long docMajorVersion;
    
    private Long docMinorVersion;
    
    private Long mainFileMajorVersion;
    
    private Long mainFileMinorVersion;
    
    private Long jpmHiTaskId;

    
    /**
     * Get docId
     * @return Long
     * @author taitt
     */
    public Long getDocId() {
        return docId;
    }

    
    /**
     * Set docId
     * @param   docId
     *          type Long
     * @return
     * @author  taitt
     */
    public void setDocId(Long docId) {
        this.docId = docId;
    }

    
    /**
     * Get mainFileId
     * @return Long
     * @author taitt
     */
    public Long getMainFileId() {
        return mainFileId;
    }

    
    /**
     * Set mainFileId
     * @param   mainFileId
     *          type Long
     * @return
     * @author  taitt
     */
    public void setMainFileId(Long mainFileId) {
        this.mainFileId = mainFileId;
    }

    
    /**
     * Get docMajorVersion
     * @return Long
     * @author taitt
     */
    public Long getDocMajorVersion() {
        return docMajorVersion;
    }

    
    /**
     * Set docMajorVersion
     * @param   docMajorVersion
     *          type Long
     * @return
     * @author  taitt
     */
    public void setDocMajorVersion(Long docMajorVersion) {
        this.docMajorVersion = docMajorVersion;
    }

    
    /**
     * Get docMinorVersion
     * @return Long
     * @author taitt
     */
    public Long getDocMinorVersion() {
        return docMinorVersion;
    }

    
    /**
     * Set docMinorVersion
     * @param   docMinorVersion
     *          type Long
     * @return
     * @author  taitt
     */
    public void setDocMinorVersion(Long docMinorVersion) {
        this.docMinorVersion = docMinorVersion;
    }

    
    /**
     * Get mainFileMajorVersion
     * @return Long
     * @author taitt
     */
    public Long getMainFileMajorVersion() {
        return mainFileMajorVersion;
    }

    
    /**
     * Set mainFileMajorVersion
     * @param   mainFileMajorVersion
     *          type Long
     * @return
     * @author  taitt
     */
    public void setMainFileMajorVersion(Long mainFileMajorVersion) {
        this.mainFileMajorVersion = mainFileMajorVersion;
    }

    
    /**
     * Get mainFileMinorVersion
     * @return Long
     * @author taitt
     */
    public Long getMainFileMinorVersion() {
        return mainFileMinorVersion;
    }

    
    /**
     * Set mainFileMinorVersion
     * @param   mainFileMinorVersion
     *          type Long
     * @return
     * @author  taitt
     */
    public void setMainFileMinorVersion(Long mainFileMinorVersion) {
        this.mainFileMinorVersion = mainFileMinorVersion;
    }
    
    /**
     * Get id
     * @return Long
     * @author taitt
     */
    public Long getId() {
        return id;
    }


    
    /**
     * Set id
     * @param   id
     *          type Long
     * @return
     * @author  taitt
     */
    public void setId(Long id) {
        this.id = id;
    }


    
    /**
     * Get jpmHiTaskId
     * @return Long
     * @author taitt
     */
    public Long getJpmHiTaskId() {
        return jpmHiTaskId;
    }


    
    /**
     * Set jpmHiTaskId
     * @param   jpmHiTaskId
     *          type Long
     * @return
     * @author  taitt
     */
    public void setJpmHiTaskId(Long jpmHiTaskId) {
        this.jpmHiTaskId = jpmHiTaskId;
    }
}
