/*******************************************************************************
 * Class        :ReportOzDto
 * Created date :2019/04/16
 * Lasted date  :2019/04/16
 * Author       :KhoaNA
 * Change log   :2019/04/16:01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * ReportOzDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class ReportOzDto  {

	private Long id;
	private Long formId;
	private String formName;
	private Boolean ozdOpen;
	private String fileName;
	private String docTitle;
	private Long companyId;

	/**
	 * Get id
	 * 
	 * @return Long
	 * @author KhoaNA
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set id
	 * 
	 * @param id
	 *            type Long
	 * @return
	 * @author KhoaNA
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Get formName
	 * @return String
	 * @author KhoaNA
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * Set formName
	 * @param   formName
	 *          type String
	 * @return
	 * @author  KhoaNA
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * Get formId
	 * @return Long
	 * @author KhoaNA
	 */
	public Long getFormId() {
		return formId;
	}

	/**
	 * Set formId
	 * @param   formId
	 *          type Long
	 * @return
	 * @author  KhoaNA
	 */
	public void setFormId(Long formId) {
		this.formId = formId;
	}

	/**
	 * Get ozdOpen
	 * 
	 * @return Boolean
	 * @author KhoaNA
	 */
	public Boolean getOzdOpen() {
		return ozdOpen;
	}

	/**
	 * Set ozdOpen
	 * 
	 * @param ozdOpen
	 *            type Boolean
	 * @return
	 * @author KhoaNA
	 */
	public void setOzdOpen(Boolean ozdOpen) {
		this.ozdOpen = ozdOpen;
	}

	/**
	 * Get fileName
	 * @return String
	 * @author KhoaNA
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Set fileName
	 * @param   fileName
	 *          type String
	 * @return
	 * @author  KhoaNA
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Get docTitle
	 * @return String
	 * @author KhoaNA
	 */
	public String getDocTitle() {
		return docTitle;
	}

	/**
	 * Set docTitle
	 * @param   docTitle
	 *          type String
	 * @return
	 * @author  KhoaNA
	 */
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

    /**
     * Get companyId
     * @return Long
     * @author KhoaNA
     */
    public Long getCompanyId() {
        return companyId;
    }
    
    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}